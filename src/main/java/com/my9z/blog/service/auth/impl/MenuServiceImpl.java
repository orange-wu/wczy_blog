package com.my9z.blog.service.auth.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my9z.blog.common.enums.ErrorCodeEnum;
import com.my9z.blog.common.pojo.entity.auth.MenuEntity;
import com.my9z.blog.common.pojo.entity.auth.RoleEntity;
import com.my9z.blog.common.pojo.entity.auth.UserAuthEntity;
import com.my9z.blog.common.pojo.req.SaveOrUpdateMenuReq;
import com.my9z.blog.common.pojo.resp.MenuResp;
import com.my9z.blog.common.pojo.resp.UserMenuResp;
import com.my9z.blog.common.util.UserUtil;
import com.my9z.blog.mapper.MenuMapper;
import com.my9z.blog.mapper.RoleMapper;
import com.my9z.blog.mapper.UserAuthMapper;
import com.my9z.blog.service.auth.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 菜单service实现类
 * @author: wczy9
 * @createTime: 2023-01-21  17:37
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<UserMenuResp> listUserMenus() {
        //1、拿到登陆用户的角色信息 没有登陆的话sa-token直接报错
        Long userId = UserUtil.getLoginId();
        UserAuthEntity userAuth = userAuthMapper.selectById(userId);
        //没有找到登录用户的对应信息 || 该用户没有被分配任何角色
        if (userAuth == null || CollUtil.isEmpty(userAuth.getRoleIds()))
            throw ErrorCodeEnum.USER_INFO_IS_ABNORMAL.buildException();
        List<Long> roleIds = userAuth.getRoleIds();
        //2、拿到对应角色拥有的目录权限id
        List<RoleEntity> roles = roleMapper.selectBatchIds(roleIds);
        Set<Long> menuIds = roles.stream()
                .flatMap(role -> role.getMenuIds().stream())
                .collect(Collectors.toSet());
        //用户对应的角色没有任何权限
        if (CollUtil.isEmpty(menuIds)) return null;
        //3、根据目录id封装返回结果
        return getUserMenuResp(menuIds);
    }

    @Override
    public List<MenuResp> listMenus(String menuName) {
        //查询菜单数据
        List<MenuEntity> menuEntityList = baseMapper.selectList(new LambdaQueryWrapper<MenuEntity>()
                .like(StrUtil.isNotEmpty(menuName), MenuEntity::getName, menuName));
        if (CollUtil.isEmpty(menuEntityList)) return null;
        //父级菜单集合
        List<MenuEntity> parentMenuList = menuEntityList.stream()
                .filter(menu -> menu.getParentId() == null)
                .collect(Collectors.toList());
        //父级菜单id为key,菜单子集为value
        Map<Long, List<MenuEntity>> childrenMap = menuEntityList.stream()
                .filter(menu -> menu.getParentId() != null)
                .collect(Collectors.groupingBy(MenuEntity::getParentId));
        //组装目录菜单数据
        return parentMenuList.stream().map(item -> {
                    //一级菜单
                    MenuResp menuResp = BeanUtil.copyProperties(item, MenuResp.class);
                    //二级菜单
                    List<MenuEntity> childrenMenuList = childrenMap.get(item.getId());
                    if (CollUtil.isNotEmpty(childrenMenuList)) {
                        List<MenuResp> childrenMenuRespList = BeanUtil.copyToList(childrenMenuList, MenuResp.class);
                        //二级菜单排序
                        childrenMenuRespList = childrenMenuRespList.stream()
                                .sorted(Comparator.comparing(MenuResp::getOrderNum))
                                .collect(Collectors.toList());
                        menuResp.setChildren(childrenMenuRespList);
                    }
                    return menuResp;
                }).sorted(Comparator.comparing(MenuResp::getOrderNum)) //一级菜单排序
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMenuById(Long menuId) {
        //查询该菜单是否有角色关联
        List<RoleEntity> roleEntityList = roleMapper.selectCountMenuId(menuId);
        if (CollUtil.isNotEmpty(roleEntityList)) {
            //当前菜单存在角色关联，不允许删除
            String roleName = roleEntityList.stream()
                    .map(RoleEntity::getRoleName)
                    .collect(Collectors.joining(StrPool.COMMA, StrPool.BRACKET_START, StrPool.BRACKET_END));
            throw ErrorCodeEnum.MENU_IS_USED_BY_ROLE.buildException(roleName);
        }
        //查询子菜单
        List<Long> deleteMenuIdList = baseMapper.selectChildId(menuId);
        deleteMenuIdList.add(menuId);
        //删除菜单
        baseMapper.deleteByIds(deleteMenuIdList);
    }

    @Override
    public void saveOrUpdateMenu(SaveOrUpdateMenuReq saveOrUpdateMenuReq) {
        MenuEntity menuEntity = BeanUtil.copyProperties(saveOrUpdateMenuReq, MenuEntity.class);
        this.saveOrUpdate(menuEntity);
    }

    /**
     * 组装菜单父子集关系
     *
     * @param menuIds 菜单id集合
     * @return 父子关系的菜单集合
     */
    private List<UserMenuResp> getUserMenuResp(Set<Long> menuIds) {
        List<MenuEntity> menus = baseMapper.selectBatchIds(menuIds);
        //父级菜单id为key,菜单子集为value
        Map<Long, List<MenuEntity>> childrenMap = menus.stream()
                .filter(menu -> menu.getParentId() != null)
                .collect(Collectors.groupingBy(MenuEntity::getParentId));
        //父级菜单集合
        List<MenuEntity> parentMenuList = menus.stream()
                .filter(menu -> menu.getParentId() == null)
                .sorted(Comparator.comparing(MenuEntity::getOrderNum))
                .collect(Collectors.toList());
        //菜单处理
        List<UserMenuResp> resp = new ArrayList<>();
        parentMenuList.forEach(parent -> {
            UserMenuResp userMenuResp;
            if (childrenMap.containsKey(parent.getId())) {
                userMenuResp = BeanUtil.copyProperties(parent, UserMenuResp.class);
                //设置子菜单
                List<MenuEntity> children = childrenMap.get(parent.getId());
                List<UserMenuResp> collect = children.stream()
                        .sorted(Comparator.comparing(MenuEntity::getOrderNum))
                        .map(menu -> BeanUtil.copyProperties(menu, UserMenuResp.class))
                        .collect(Collectors.toList());
                userMenuResp.setChildren(collect);
            } else {
                //只有一级菜单时，生成一个子菜单，目的是为了让前端vue更方便的渲染路由
                userMenuResp = new UserMenuResp();
                userMenuResp.setPath(parent.getPath());
                userMenuResp.setComponent("Layout");
                userMenuResp.setHidden(parent.getHidden());
                List<UserMenuResp> children = CollUtil.list(false, UserMenuResp.builder()
                        .path("").name(parent.getName()).icon(parent.getIcon()).component(parent.getComponent())
                        .build());
                userMenuResp.setChildren(children);
            }
            resp.add(userMenuResp);
        });
        return resp;
    }
}

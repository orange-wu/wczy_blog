package com.my9z.blog.common.pojo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 修改用户信息请求对象
 * @author: wczy9
 * @createTime: 2023-06-11  13:45
 */
@Data
public class UpdateUserRoleReq {

    /**
     * id
     */
    @NotNull(message = "异常数据：id为空")
    private Long id;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 用户角色
     */
    @NotEmpty(message = "用户角色不能为空")
    private List<Long> roleIds;


}
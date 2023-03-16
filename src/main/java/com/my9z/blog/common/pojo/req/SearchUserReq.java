package com.my9z.blog.common.pojo.req;

import com.my9z.blog.common.pojo.WPage;
import com.my9z.blog.common.pojo.resp.UserPageInfoResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 用户查询请求对象
 * @author: wczy9
 * @createTime: 2023-03-16  14:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchUserReq extends WPage<UserPageInfoResp> {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 登陆方式
     */
    private Integer loginType;

}
package com.my9z.blog.common.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 接口模块资源返回对象
 * @author: wczy9
 * @createTime: 2023-02-13  16:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModularResourceResp {

    /**
     * 模块id
     */
    private Long id;

    /**
     * 选项名
     */
    private String label;

    /**
     * 子选项
     */
    private List<ModularResourceResp> children;

}
package com.my9z.blog.config.mptypehandler;

import com.alibaba.fastjson.TypeReference;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

/**
 * @description: List<Long> --> varchar json
 * @author: kim
 * @createTime: 2023-02-02  11:40
 */
@MappedJdbcTypes(JdbcType.VARBINARY)
@MappedTypes({List.class})
public class LongListToJsonTypeHandler extends ListToJsonTypeHandler<Long> {

    @Override
    protected TypeReference<List<Long>> specificType() {
        return new TypeReference<List<Long>>() {
        };
    }

}

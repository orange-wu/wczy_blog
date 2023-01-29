package com.my9z.blog.config.mptypehandler;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: list转varchar json格式
 * @author: wczy9
 * @createTime: 2023-01-24  23:08
 */
@MappedJdbcTypes(JdbcType.VARBINARY)
@MappedTypes({List.class})
public class ListToJsonTypeHandler extends BaseTypeHandler<List<?>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<?> ts, JdbcType jdbcType) throws SQLException {
        String param = CollUtil.isEmpty(ts) ? null : JSON.toJSONString(ts);
        preparedStatement.setString(i, param);
    }

    @Override
    public List<?> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return getListByJson(resultSet.getString(columnName));
    }

    @Override
    public List<?> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return getListByJson(resultSet.getString(columnIndex));
    }

    @Override
    public List<?> getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return getListByJson(callableStatement.getString(columnIndex));
    }

    private List<?> getListByJson(String param) {
        return param == null ? null : (List<?>) JSON.parseObject(param, List.class);
    }
}
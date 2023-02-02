package com.my9z.blog.config.mptypehandler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
 * @description: list<?> --> 转varchar json格式
 * @author: wczy9
 * @createTime: 2023-01-24  23:08
 */
public abstract class ListToJsonTypeHandler<T> extends BaseTypeHandler<List<T>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<T> ts, JdbcType jdbcType) throws SQLException {
        String param = CollUtil.isEmpty(ts) ? null : JSON.toJSONString(ts);
        preparedStatement.setString(i, param);
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return getListByJson(resultSet.getString(columnName));
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return getListByJson(resultSet.getString(columnIndex));
    }

    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return getListByJson(callableStatement.getString(columnIndex));
    }

    private List<T> getListByJson(String param) {
        return StrUtil.isBlank(param) ? null : JSON.parseObject(param, this.specificType());
    }

    /**
     * List具体的类型，由子类提供
     *
     * @return 具体类型
     */
    protected abstract TypeReference<List<T>> specificType();
}

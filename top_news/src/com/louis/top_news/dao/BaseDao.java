package com.louis.top_news.dao;

import com.louis.top_news.util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {

    public <T> T baseQueryObject (Class<T> clazz , String sql , Object... args) {
        T t = null;
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int rows = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1 , args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                t = (T)resultSet.getObject(1);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            JDBCUtil.closeConnection();
        }
        return t;
    }

    public <T> List<T> baseQuery (Class clazz , String sql , Object... args) {
        List<T> list = new ArrayList<>();
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int rows = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1 , args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Object obj = clazz.getDeclaredConstructor()
                                  .newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    Object value = resultSet.getObject(columnName);

                    // 處理 datetime 欄位、java.util.Date 轉換問題
                    if (value.getClass()
                             .equals(LocalDateTime.class)) {
                        value = Timestamp.valueOf((LocalDateTime)value);
                    }
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(obj , value);
                }
                list.add((T)obj);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            JDBCUtil.closeConnection();
        }
        return list;
    }

    public int baseUpdate (String sql , Object... args) {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1 , args[i]);
            }
            rows = preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                }
                catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            JDBCUtil.closeConnection();
        }
        return rows;
    }
}
package com.lagou.sqlSession;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {

    public <E> List<E> query(Configuration configuration,MappedStatement mappedStatement,Object... params) throws Exception;
    int save(Configuration configuration,MappedStatement mappedStatement,Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException;
    int update(Configuration configuration,MappedStatement mappedStatement,Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException;
    int delete(Configuration configuration,MappedStatement mappedStatement,Object... params) throws ClassNotFoundException, SQLException, IllegalAccessException, NoSuchFieldException;

}

package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;


    //根据条件进行用户查询
    public User findByCondition(User user) throws Exception;
    //添加用户
    void saveUser(User user) throws Exception;
    //更改用户
    void updateUser(User user) throws Exception;
    //通过id删除指定用户
    void deleteUser(User user) throws Exception;


}

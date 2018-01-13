package com.yy.service;

import com.yy.dao.UserDao;
import com.yy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yy on 2018/1/13.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
//        return new User(1,"qw");
    }
}

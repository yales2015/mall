package com.yy.service.impl;

import com.yy.common.ServerResponse;
import com.yy.pojo.User;
import com.yy.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by yy on 2018/1/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    IUserService iUserService;

    @Test
    public void testLogin(){
        ServerResponse<User> response=iUserService.login("test","test");
        Assert.assertNotNull(response);
        System.out.println(response.getData().toString());
    }
}

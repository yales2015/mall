package com.yy.controller.backend;

import com.yy.common.Const;
import com.yy.common.ServerResponse;
import com.yy.pojo.User;
import com.yy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by yy on 2018/1/15.
 */
@Controller
@RequestMapping("/manager/user")
public class UserManagerController {
    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "/login",method= RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(HttpSession session,String username,String password){
        ServerResponse<User> response=iUserService.login(username, password);
        if(!response.isSuccess()) {
            return response;
        }
        User user=response.getData();
        if(user.getRole()!=Const.Role.ROLE_ADMIN){
            return ServerResponse.createByErrorMsg("用户非管理员");
        }
        session.setAttribute(Const.CURRENT_USER,response.getData());
        return response;
    }

}

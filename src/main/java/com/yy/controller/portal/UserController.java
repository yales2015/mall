package com.yy.controller.portal;
/**
 * Created by yy on 2018/1/11.
 */


import com.yy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
//        return "hello world";
        return userService.getById(1).toString();
    }
}

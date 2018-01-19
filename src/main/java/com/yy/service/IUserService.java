package com.yy.service;

import com.yy.common.ServerResponse;
import com.yy.pojo.User;

/**
 * Created by yy on 2018/1/13.
 */
public interface IUserService {
    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str,String type);
    ServerResponse<String> selectQuestion(String username);
    ServerResponse<String> checkAnswer(String username, String question, String answer);
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);
    ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew);
    ServerResponse<User> updateInformation(User user);
    ServerResponse<User> getInformation(int userId);
    ServerResponse<String> checkAdminRole(User currentUser);
}

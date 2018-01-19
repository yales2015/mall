package com.yy.service.impl;

import com.yy.common.Const;
import com.yy.common.ResponseCode;
import com.yy.common.ServerResponse;
import com.yy.common.TokenCache;
import com.yy.dao.UserMapper;
import com.yy.pojo.User;
import com.yy.service.IUserService;
import com.yy.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by yy on 2018/1/13.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount=userMapper.checkUsername(username);
        if(resultCount==0){
            return ServerResponse.createByErrorMsg("用户名不存在");
        }
        //密码登录MD5
        String md5Password=MD5Util.MD5EncodeUtf8(password);

        User user=userMapper.selectLogin(username,md5Password);
        if(user==null){
            return ServerResponse.createByErrorMsg("密码错误");
        }
        user.setPassword("");
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse<String> usernameResponse=this.checkValid(user.getUsername(),Const.USERNAME);
        if(usernameResponse.isSuccess()){
            return ServerResponse.createByErrorMsg("用户名已存在");
        }
        ServerResponse<String> emailResponse=this.checkValid(user.getEmail(),Const.EMAIL);
        if(emailResponse.isSuccess()){
            return ServerResponse.createByErrorMsg("email已存在");
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount=userMapper.insert(user);
        if(resultCount==0){
            return ServerResponse.createByErrorMsg("注册失败");
        }
        return ServerResponse.createBySuccessMsg("注册成功");
    }

    /**
     *检查用户名和邮箱是否存在，如果不存在则返回success
     * @param str
     * @param type
     * @return
     */
    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if(org.apache.commons.lang3.StringUtils.isBlank(type)){
            return  ServerResponse.createByErrorMsg("参数错误");
        }
        if(Const.USERNAME.equalsIgnoreCase(type)){
            int resultCount=userMapper.checkUsername(str);
            if(resultCount>0){
                return ServerResponse.createBySuccessMsg("用户名存在");
            }
        }
        if(Const.EMAIL.equalsIgnoreCase(type)) {
            int emailCount=userMapper.checkEmail(str);
            if(emailCount>0){
                return ServerResponse.createBySuccessMsg("email存在");
            }
        }
        return ServerResponse.createByErrorMsg("校验失败，不存在");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse<String> usernameResponse=checkValid(username,Const.USERNAME);
        if(!usernameResponse.isSuccess()){
            return ServerResponse.createByErrorMsg("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(org.apache.commons.lang3.StringUtils.isBlank(question)){
            ServerResponse.createByErrorMsg("找回密码的问题是空");
        }
        return ServerResponse.createBySuccess(question);
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount=userMapper.checkAnswer(username,question,answer);
        if(resultCount==0){
            return ServerResponse.createByErrorMsg("问题答案错误");
        }
        String forgetToken= UUID.randomUUID().toString();
        TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
        return ServerResponse.createBySuccess(forgetToken);
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {

       if(StringUtils.isBlank(forgetToken)){
           return ServerResponse.createByErrorMsg("参数错误，Token需要传递");
       }
       ServerResponse<String> usernameResponse=checkValid(username,Const.USERNAME);
        if (!usernameResponse.isSuccess()){
            return ServerResponse.createByErrorMsg("用户不存在");
        }
       String token= TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
       if(!StringUtils.equals(token,forgetToken)){
           return ServerResponse.createByErrorMsg("Token错误，请重新获取重置密码的Token");
       }
       String MD5Password= MD5Util.MD5EncodeUtf8(passwordNew);
       int rowCount=userMapper.updataPasswordByUsername(username,MD5Password);
        if(rowCount==0){
            return ServerResponse.createByErrorMsg("修改密码失败");
        }
        return ServerResponse.createBySuccess("修改密码成功");
    }

    @Override
    public ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew) {
        //防止横向越权，要校验一下用户的旧密码
        int resultCount=userMapper.checkPassword(user.getId(),MD5Util.MD5EncodeUtf8(passwordOld));
        if(resultCount==0){
            return ServerResponse.createByErrorMsg("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updataCount=userMapper.updatePasswordById(user.getId(),user.getPassword());
        if(updataCount==0){
            return ServerResponse.createByErrorMsg("密码更新失败");
        }
        return ServerResponse.createBySuccessMsg("密码更新成功");
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        //username是不能更新
        //email需要进行校验，校验新的email是否已经存在
        // 检查email是不是在非当前用户时存在
        int resultCount=userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount>0){
            return ServerResponse.createByErrorMsg("请更换email，再尝试更新");
        }
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount=userMapper.updateByPrimaryKey(updateUser);
        if(updateCount==0){
            return ServerResponse.createByErrorMsg("更新个人信息失败");
        }
        return ServerResponse.createBySuccess("更新个人信息成功",user);
    }

    @Override
    public ServerResponse<User> getInformation(int userId) {
        User user=userMapper.selectByPrimaryKey(userId);
        user.setPassword("");
        if(user==null){
            return ServerResponse.createByErrorMsg("用户不存在");
        }
        return ServerResponse.createBySuccess("查找用户成功",user);
    }

    @Override
    public ServerResponse<String> checkAdminRole(User user) {
        if(user!=null && user.getRole()== Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}

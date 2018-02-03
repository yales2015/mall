package com.yy.dao;

import com.yy.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //手动自增
    int checkUsername(@Param("username") String username);

    User selectLogin(@Param("username") String username, @Param("password") String md5Password);

    int checkEmail(@Param("email") String email);

    String selectQuestionByUsername(@Param("username") String username);

    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int updataPasswordByUsername(@Param("username") String username, @Param("password") String md5Password);

    int checkPassword(@Param("id") Integer id, @Param("password") String password);

    int checkEmailByUserId(@Param("email") String email, @Param("id") Integer id);
}
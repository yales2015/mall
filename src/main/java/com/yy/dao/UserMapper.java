package com.yy.dao;

import com.yy.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Delete({
        "delete from mmall_user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into mmall_user (id, username, ",
        "password, email, ",
        "phone, question, ",
        "answer, role, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{phone,jdbcType=VARCHAR}, #{question,jdbcType=VARCHAR}, ",
        "#{answer,jdbcType=VARCHAR}, #{role,jdbcType=INTEGER}, now(), ",
        "now())"
    })
    int insert(User record);

    int insertSelective(User record);

    @Select({
        "select",
        "id, username, password, email, phone, question, answer, role, create_time, update_time",
        "from mmall_user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    User selectByPrimaryKey(Integer id);

    @Update({
            "update mmall_user",
            "set password = #{password}",
            "where id = #{id}"
    })
    int updatePasswordById(@Param("id") int id,@Param("password") String password);


    int updateByPrimaryKeySelective(User record);

    @Update({
        "update mmall_user",
        "set email = #{email,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "question = #{question,jdbcType=VARCHAR},",
          "answer = #{answer,jdbcType=VARCHAR},",
          "update_time = now()",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);

    /**
     * 手动增加
     * @param username
     * @return
     */

    @Select({
            "select",
            "count(1)",
            "from mmall_user",
            "where username = #{username}"
    })
    int checkUsername(@Param("username") String username);

    @Select({
            "select",
            "id, username, password, email, phone, question, answer, role, create_time, update_time",
            "from mmall_user",
            "where username = #{username} and password = #{password}"
    })
    User selectLogin(@Param("username") String username, @Param("password") String md5Password);

    @Select({
            "select",
            "count(1)",
            "from mmall_user",
            "where email = #{email}"
    })
    int checkEmail(@Param("email") String email);

        @Select({
                "select",
                "question",
                "from mmall_user",
                "where username = #{username}"
        })
    String selectQuestionByUsername(@Param("username") String username);

    @Select({
            "select",
            "count(1)",
            "from mmall_user",
            "where username = #{username} and question = #{question} and answer = #{answer}"
    })
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    @Update({
            "update mmall_user",
            "set password = #{password,jdbcType=VARCHAR},",
            "update_time = now()",
            "where username = #{username}"
    })
    int updataPasswordByUsername(@Param("username") String username, @Param("password") String md5Password);

    @Select({
            "select",
            "count(1)",
            "from mmall_user",
            "where id = #{id} and password = #{password}"
    })
    int checkPassword(@Param("id") Integer id, @Param("password") String password);

    @Select({
            "select",
            "count(1)",
            "from mmall_user",
            "where id != #{id} and email = #{email}"
    })
    int checkEmailByUserId(@Param("email") String email, @Param("id") Integer id);
}
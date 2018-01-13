package com.yy.dao;

import com.yy.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by yy on 2018/1/13.
 */
@Mapper
public interface UserDao{
    @Select("select * from user where id = #{id}")
    User getById(@Param("id")int id);
}

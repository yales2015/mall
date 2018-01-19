package com.yy.dao;

import com.yy.pojo.PayInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PayInfoMapper {
    @Delete({
        "delete from mmall_pay_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into mmall_pay_info (id, user_id, ",
        "order_no, pay_platform, ",
        "platform_number, platform_status, ",
        "create_time, update_time)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{orderNo,jdbcType=BIGINT}, #{payPlatform,jdbcType=INTEGER}, ",
        "#{platformNumber,jdbcType=VARCHAR}, #{platformStatus,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    @Select({
        "select",
        "id, user_id, order_no, pay_platform, platform_number, platform_status, create_time, ",
        "update_time",
        "from mmall_pay_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    @Update({
        "update mmall_pay_info",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "order_no = #{orderNo,jdbcType=BIGINT},",
          "pay_platform = #{payPlatform,jdbcType=INTEGER},",
          "platform_number = #{platformNumber,jdbcType=VARCHAR},",
          "platform_status = #{platformStatus,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PayInfo record);
}
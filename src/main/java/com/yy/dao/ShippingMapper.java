package com.yy.dao;

import com.yy.pojo.Shipping;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ShippingMapper {
    @Delete({
        "delete from mmall_shipping",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into mmall_shipping (id, user_id, ",
        "receiver_name, receiver_phone, ",
        "receiver_mobile, receiver_province, ",
        "receiver_city, receiver_district, ",
        "receiver_address, receiver_zip, ",
        "create_time, update_time)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{receiverName,jdbcType=VARCHAR}, #{receiverPhone,jdbcType=VARCHAR}, ",
        "#{receiverMobile,jdbcType=VARCHAR}, #{receiverProvince,jdbcType=VARCHAR}, ",
        "#{receiverCity,jdbcType=VARCHAR}, #{receiverDistrict,jdbcType=VARCHAR}, ",
        "#{receiverAddress,jdbcType=VARCHAR}, #{receiverZip,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Shipping record);

    int insertSelective(Shipping record);

    @Select({
        "select",
        "id, user_id, receiver_name, receiver_phone, receiver_mobile, receiver_province, ",
        "receiver_city, receiver_district, receiver_address, receiver_zip, create_time, ",
        "update_time",
        "from mmall_shipping",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    @Update({
        "update mmall_shipping",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "receiver_name = #{receiverName,jdbcType=VARCHAR},",
          "receiver_phone = #{receiverPhone,jdbcType=VARCHAR},",
          "receiver_mobile = #{receiverMobile,jdbcType=VARCHAR},",
          "receiver_province = #{receiverProvince,jdbcType=VARCHAR},",
          "receiver_city = #{receiverCity,jdbcType=VARCHAR},",
          "receiver_district = #{receiverDistrict,jdbcType=VARCHAR},",
          "receiver_address = #{receiverAddress,jdbcType=VARCHAR},",
          "receiver_zip = #{receiverZip,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Shipping record);
}
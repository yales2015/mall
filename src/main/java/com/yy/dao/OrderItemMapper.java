package com.yy.dao;

import com.yy.pojo.OrderItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrderItemMapper {
    @Delete({
        "delete from mmall_order_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into mmall_order_item (id, user_id, ",
        "order_no, product_id, ",
        "product_name, product_image, ",
        "current_unit_price, quantity, ",
        "total_price, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{orderNo,jdbcType=BIGINT}, #{productId,jdbcType=INTEGER}, ",
        "#{productName,jdbcType=VARCHAR}, #{productImage,jdbcType=VARCHAR}, ",
        "#{currentUnitPrice,jdbcType=DECIMAL}, #{quantity,jdbcType=INTEGER}, ",
        "#{totalPrice,jdbcType=DECIMAL}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    @Select({
        "select",
        "id, user_id, order_no, product_id, product_name, product_image, current_unit_price, ",
        "quantity, total_price, create_time, update_time",
        "from mmall_order_item",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    @Update({
        "update mmall_order_item",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "order_no = #{orderNo,jdbcType=BIGINT},",
          "product_id = #{productId,jdbcType=INTEGER},",
          "product_name = #{productName,jdbcType=VARCHAR},",
          "product_image = #{productImage,jdbcType=VARCHAR},",
          "current_unit_price = #{currentUnitPrice,jdbcType=DECIMAL},",
          "quantity = #{quantity,jdbcType=INTEGER},",
          "total_price = #{totalPrice,jdbcType=DECIMAL},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OrderItem record);
}
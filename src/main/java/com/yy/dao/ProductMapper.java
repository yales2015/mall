package com.yy.dao;

import com.yy.pojo.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProductMapper {
    @Delete({
        "delete from mmall_product",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into mmall_product (id, category_id, ",
        "name, subtitle, ",
        "main_image, sub_images, ",
        "detail, price, stock, ",
        "status, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=INTEGER}, #{categoryId,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{subtitle,jdbcType=VARCHAR}, ",
        "#{mainImage,jdbcType=VARCHAR}, #{subImages,jdbcType=VARCHAR}, ",
        "#{detail,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, #{stock,jdbcType=INTEGER}, ",
        "#{status,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Product record);

    int insertSelective(Product record);

    @Select({
        "select",
        "id, category_id, name, subtitle, main_image, sub_images, detail, price, stock, ",
        "status, create_time, update_time",
        "from mmall_product",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    @Update({
        "update mmall_product",
        "set category_id = #{categoryId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "subtitle = #{subtitle,jdbcType=VARCHAR},",
          "main_image = #{mainImage,jdbcType=VARCHAR},",
          "sub_images = #{subImages,jdbcType=VARCHAR},",
          "detail = #{detail,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DECIMAL},",
          "stock = #{stock,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Product record);
}
package com.yy.dao;

import com.yy.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CategoryMapper {
    @Delete({
        "delete from mmall_category",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into mmall_category (id, parent_id, ",
        "name, status, sort_order, ",
        "create_time, update_time)",
        "values (#{id,jdbcType=INTEGER}, #{parentId,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{status,jdbcType=BIT}, #{sortOrder,jdbcType=INTEGER}, ",
        "now(), now())"
    })
    int insert(Category record);

    int insertSelective(Category record);

    @Select({
        "select",
        "id, parent_id, name, status, sort_order, create_time, update_time",
        "from mmall_category",
        "where id = #{id,jdbcType=INTEGER}"
    })
    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    @Update({
        "update mmall_category",
        "set name = #{name,jdbcType=VARCHAR},",
          "update_time = now()",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateNameByPrimaryKey(Category record);

    @Select({
            "select *" ,
            "from mmall_category",
            "where parent_id=#{parent_id}"
    })
    List<Category> selectCategoryChildrenById(@Param("parent_id") Integer parentId);
}
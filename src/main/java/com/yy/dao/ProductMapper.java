package com.yy.dao;

import com.yy.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectList();

    List<Product> selectByNameId(@Param("name")String name,@Param("id") Integer id);

    List<Product> selectByNameAndCategoryIds(@Param("name") String name,@Param("categoryIdList") List<Integer> categoryIdList);
}
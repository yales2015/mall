package com.yy.dao;

import com.yy.pojo.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserIdProductId(@Param("userId") int userId,@Param("productId")int productId);

    List<Cart> selectByUserId(@Param("userId") int userId);

    /**
     * 根据用户id查找是否存在未勾选的商品
     * @param userId
     * @return
     */
    int selectProductUncheckedStatusByUserId(@Param("userId") Integer userId);

    void deleteByUserIdProductIdList(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    void updateCheckedByProductIdUserId(@Param("userId") Integer userId,@Param("productId")Integer productId,@Param("checked") Integer checked);

    Integer selectTotalCountByUserId(@Param("userId") Integer userId);
}
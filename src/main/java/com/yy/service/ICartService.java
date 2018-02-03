package com.yy.service;

import com.yy.common.ServerResponse;
import com.yy.vo.CartVo;

/**
 * Created by yy on 2018/2/2.
 */
public interface ICartService {
    ServerResponse<CartVo> add(int userId, Integer productId, Integer count);
    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> deleteProducts(Integer userId, String productIds);
    ServerResponse<CartVo> selectorUnselect(Integer userId,Integer productId,Integer checked);
    ServerResponse<CartVo> list(Integer userId);
    ServerResponse<Integer> getTotalCount(Integer userId);
}

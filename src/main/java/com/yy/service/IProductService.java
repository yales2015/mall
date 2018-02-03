package com.yy.service;

import com.github.pagehelper.PageInfo;
import com.yy.common.ServerResponse;
import com.yy.pojo.Product;
import com.yy.vo.ProductDetailVo;
import com.yy.vo.ProductListVo;

import java.util.List;

/**
 * Created by yy on 2018/1/19.
 */
public interface IProductService {
    ServerResponse<String> saveOrUpdateProduct(Product product);
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> getDetail(Integer productId);
    ServerResponse<PageInfo> list(Integer pageNum, Integer pageSize);
    ServerResponse<PageInfo> search(String productName, Integer productId, Integer pageNum, Integer pageSize);
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy);
}

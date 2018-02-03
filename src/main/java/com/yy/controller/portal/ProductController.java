package com.yy.controller.portal;

import com.github.pagehelper.PageInfo;
import com.yy.common.ServerResponse;
import com.yy.pojo.Product;
import com.yy.service.IProductService;
import com.yy.vo.ProductDetailVo;
import com.yy.vo.ProductListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yy on 2018/2/1.
 */
@Controller
@RequestMapping("/product/")
public class ProductController {
    @Autowired
    IProductService iProductService;

    @RequestMapping("/detail")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value="keyword",required = false) String keyword,
                                         @RequestParam(value="categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "")String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }
}

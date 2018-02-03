package com.yy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yy.common.Const;
import com.yy.common.ResponseCode;
import com.yy.common.ServerResponse;
import com.yy.dao.CategoryMapper;
import com.yy.dao.ProductMapper;
import com.yy.pojo.Category;
import com.yy.pojo.Product;
import com.yy.service.ICategoryService;
import com.yy.service.IProductService;
import com.yy.util.DateTimeUtil;
import com.yy.util.PropertiesUtil;
import com.yy.vo.ProductDetailVo;
import com.yy.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yy on 2018/1/19.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ICategoryService iCategoryService;

    @Override
    public ServerResponse<String> saveOrUpdateProduct(Product product) {
        if (product == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "新增或者更新产品参数错误");
        }
        String[] subImageArray = product.getSubImages().split(",");
        if (subImageArray.length > 0) {
            product.setMainImage(subImageArray[0]);
        }
        //id不为空，则为更新操作
        if (product.getId() != null) {
            int updateCount = productMapper.updateByPrimaryKeySelective(product);
            if (updateCount > 0) {
                return ServerResponse.createBySuccess("更新产品成功");
            }
            return ServerResponse.createByErrorMsg("更新产品失败");
        }
        int insertCount = productMapper.insert(product);
        if (insertCount > 0) {
            return ServerResponse.createBySuccess("新增产品成功");
        }
        return ServerResponse.createByErrorMsg("新增产品失败");
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "者更新销售状态参数错误");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新产品销售状态成功");
        }
        return ServerResponse.createByErrorMsg("更新产品销售状态失败");
    }

    @Override
    public ServerResponse<ProductDetailVo> getDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "获取产品详细信息参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMsg("获取产品详细信息失败");
        }
        return ServerResponse.createBySuccess("获取产品详细信息成功", assembleProductDetailVo(product));
    }

    @Override
    public ServerResponse<PageInfo> list(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList) {
            productListVoList.add(assembleProductListVo(productItem));
        }
        PageInfo pageInfo = new PageInfo(productListVoList);
//        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> search(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (!StringUtils.isBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameId(productName, productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : productList) {
            productListVoList.add(assembleProductListVo(productItem));
        }
        PageInfo pageInfo = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    //=======================================================
    //portal

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(), "获取产品详细信息参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMsg("获取产品详细信息失败");
        }
        if(product.getStatus()!= Const.ProductStautsEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMsg("产品已下架或删除");
        }
        return ServerResponse.createBySuccess("获取产品详细信息成功", assembleProductDetailVo(product));
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy) {
        if(StringUtils.isBlank(keyword) && categoryId==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList=Lists.newArrayList();
        if(categoryId!=null){
            Category category=categoryMapper.selectByPrimaryKey(categoryId);
            if(category==null&&StringUtils.isBlank(keyword)){
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList=Lists.newArrayList();
                PageInfo pageInfo=new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdList=iCategoryService.getCategoryAndChildrenById(category.getId()).getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword=new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray=orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> productList=productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,
                categoryIdList.size()==0?null:categoryIdList);
        List<ProductListVo> productListVoList=Lists.newArrayList();
        for (Product product:productList){
            productListVoList.add(assembleProductListVo(product));
        }
        PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    //=============================================
    //组装
    private ProductDetailVo assembleProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setName(product.getName());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        //配置文件中读取ImageHost
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.imitationtmall.xin/"));
        //获取父类别名称
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            productDetailVo.setParentCategoryId(0);
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }
        return productDetailVo;
    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setName(product.getName());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setStatus(product.getStatus());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.imitationtmall.xin/"));
        return productListVo;
    }
}

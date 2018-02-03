package com.yy.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yy.common.Const;
import com.yy.common.ResponseCode;
import com.yy.common.ServerResponse;
import com.yy.dao.CartMapper;
import com.yy.dao.ProductMapper;
import com.yy.pojo.Cart;
import com.yy.pojo.Product;
import com.yy.service.ICartService;
import com.yy.util.BigDecimalUtil;
import com.yy.util.PropertiesUtil;
import com.yy.vo.CartProductVo;
import com.yy.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yy on 2018/2/2.
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> add(int userId,Integer productId, Integer count) {
        if(productId==null || count==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectByUserIdProductId(userId,productId);
        if(cart==null){
            Cart cartItem =new Cart();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(count);
            cartItem.setUserId(userId);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        }else{
            //如果购物车已存在，则产品相加
            cart.setQuantity(cart.getQuantity()+count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
        if(productId==null || count==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectByUserIdProductId(userId,productId);
        if(cart!=null){
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    @Override
    public ServerResponse<CartVo> deleteProducts(Integer userId, String productIds) {
        if(productIds==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<String> productIdList= Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productIdList)){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIdList(userId,productIdList);
        return list(userId);
    }

    @Override
    public ServerResponse<CartVo> list(Integer userId) {
        return ServerResponse.createBySuccess(this.getCartVoLimit(userId));
    }

    @Override
    public ServerResponse<Integer> getTotalCount(Integer userId) {
        if(userId==null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectTotalCountByUserId(userId));
    }


    @Override
    public ServerResponse<CartVo> selectorUnselect(Integer userId,Integer productId,Integer checked) {
        cartMapper.updateCheckedByProductIdUserId(userId,productId,checked);
        return list(userId);
    }

    //返回购物车信息栏
    private CartVo getCartVoLimit(int userId) {
        //获取某个用户购置的所有商品
        List<Cart> cartList=cartMapper.selectByUserId(userId);
        List<CartProductVo> cartProductVoList= Lists.newArrayList();
        BigDecimal totalPrice=new BigDecimal("0");
        //cartList-->cartProductVoList
        for ( Cart cartItem :cartList){
            CartProductVo cartProductVo=new CartProductVo();
            cartProductVo.setId(cartItem.getId());
            cartProductVo.setUserId(cartItem.getUserId());
            cartProductVo.setProductId(cartItem.getProductId());
            Product product=productMapper.selectByPrimaryKey(cartItem.getProductId());
            cartProductVo.setProductName(product.getName());
            cartProductVo.setProductMainImage(product.getMainImage());
            cartProductVo.setProductPrice(product.getPrice());
            cartProductVo.setProductSubtitle(product.getSubtitle());
            cartProductVo.setProductStatus(product.getStatus());
            cartProductVo.setProductStock(product.getStock());
            cartProductVo.setProductChecked(cartItem.getChecked());
            int quantity=cartItem.getQuantity();//买家要买的商品数量
            int stock=product.getStock();
            if(quantity>stock){
                //库存不足
                cartItem.setQuantity(stock);
                cartMapper.updateByPrimaryKeySelective(cartItem);
                cartProductVo.setQuantity(stock);
                cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
            }else{
                //库存充足
                cartProductVo.setQuantity(quantity);
                cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
            }
            cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity().doubleValue()));
            if(cartItem.getChecked()==Const.Cart.CHECKED) {
                totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
            }
            cartProductVoList.add(cartProductVo);
        }
        CartVo cartVo=new CartVo();
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setAllChecked(this.getAllChecked(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","ftp://120.79.86.75/"));
        return cartVo;
    }

    private Boolean getAllChecked(Integer userId) {
        if(userId==null){
            return false;
        }
        //不存在未勾选的商品，返回True
        return cartMapper.selectProductUncheckedStatusByUserId(userId)==0;
    }
}

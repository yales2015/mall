package com.yy.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yy on 2018/2/2.
 */
@Data
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;//是否已经都勾选
    private String imageHost;
}

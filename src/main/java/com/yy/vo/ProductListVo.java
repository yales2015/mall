package com.yy.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by yy on 2018/1/19.
 */
@Data
public class ProductListVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private BigDecimal price;
    private Integer status;


    public String imageHost;
}

package com.yy.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by yy on 2018/1/13.
 */
public class Const {
    public static final String CURRENT_USER="currentUser";

    public static final String EMAIL="email";
    public static final String USERNAME="username";

    public interface Role{
        int ROLE_CUSTOMER=0;//普通用户
        int ROLE_ADMIN=1;//管理员

    }

    public interface Cart{
        int CHECKED=1;
        int UN_CHECKED=0;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";//购物车中商品数量大于库存
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";//购物车中商品数量小于库存

    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC= Sets.newHashSet("price_asc","price_desc");
    }

    public enum ProductStautsEnum{
        ON_SALE(1,"在线");

        private int code;
        private String value;

        ProductStautsEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }
}

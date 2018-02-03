package com.yy.util;

import java.math.BigDecimal;

/**
 * Created by yy on 2018/2/2.
 */
public class BigDecimalUtil {
    private BigDecimalUtil(){}
    public static BigDecimal add(Double d1,Double d2){
        BigDecimal b1=new BigDecimal(d1.toString());
        BigDecimal b2=new BigDecimal(d2.toString());
        return b1.add(b2);
    }

    public static BigDecimal sub(Double d1,Double d2){
        BigDecimal b1=new BigDecimal(d1.toString());
        BigDecimal b2=new BigDecimal(d2.toString());
        return b1.subtract(b2);
    }

    public static BigDecimal mul(Double d1,Double d2){
        BigDecimal b1=new BigDecimal(d1.toString());
        BigDecimal b2=new BigDecimal(d2.toString());
        return b1.multiply(b2);
    }

    public static BigDecimal div(Double d1,Double d2){
        BigDecimal b1=new BigDecimal(d1.toString());
        BigDecimal b2=new BigDecimal(d2.toString());
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
    }
}

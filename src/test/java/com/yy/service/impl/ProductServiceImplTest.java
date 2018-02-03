package com.yy.service.impl;

import com.github.pagehelper.PageInfo;
import com.yy.common.ServerResponse;
import com.yy.service.IProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by yy on 2018/2/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    IProductService iProductService;

    @Test
    public void list() throws Exception {
        ServerResponse<PageInfo> response=iProductService.list(1,10);
        System.out.println(response.getData());
    }

    @Test
    public void search() throws Exception {
        ServerResponse<PageInfo> response=iProductService.search("Apple",null,1,10);
        System.out.println(response.getData());
    }

}
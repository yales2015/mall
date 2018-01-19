package com.yy.service;

import com.yy.common.ServerResponse;
import com.yy.pojo.Category;

import java.util.List;

/**
 * Created by yy on 2018/1/17.
 */
public interface ICategoryService {
    ServerResponse<String> addCategory(String categoryName, Integer parentId);
    ServerResponse<String> setCategoryName(Integer categoryId, String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>> getCategoryAndChildrenById(Integer categoryId);
}

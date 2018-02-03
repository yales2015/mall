package com.yy.service.impl;

import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.yy.common.ServerResponse;
import com.yy.dao.CategoryMapper;
import com.yy.pojo.Category;
import com.yy.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.mockito.internal.util.collections.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yy on 2018/1/17.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse<String> addCategory(String categoryName, Integer parentId) {
        if(parentId==null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMsg("增加品类参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//品类可用
        int rowCount = categoryMapper.insert(category);
        if(rowCount<=0){
            return ServerResponse.createByErrorMsg("增加品类失败");
        }
        return ServerResponse.createBySuccessMsg("增加品类成功");
    }

    @Override
    public ServerResponse<String> setCategoryName(Integer categoryId, String categoryName) {
        if(categoryId==null|| StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMsg("更新品类参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount<=0){
            return ServerResponse.createByErrorMsg("更新品类名称失败");
        }
        return ServerResponse.createBySuccess("更新品类名称成功");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        if(categoryId==null){
            return ServerResponse.createByErrorMsg("获取品类平行子项参数错误");
        }
        List<Category> categoryList=categoryMapper.selectCategoryChildrenById(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return  ServerResponse.createBySuccess("获取品类平行子项成功",categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> getCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet= Sets.newSet();
        categorySet=findChildrenCategory(categorySet,categoryId);
        List<Integer> categoryIdList= Lists.newArrayList();
        if(categoryId!=null){
            for(Category categoryItem :categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess("获取品类并递归子品类成功",categoryIdList);
    }

    /**
     * 递归算出子节点
     * @return
     */
     private Set<Category> findChildrenCategory(Set<Category> categorySet,Integer categoryId){
         Category category=categoryMapper.selectByPrimaryKey(categoryId);
         if(category!=null){
            categorySet.add(category);
         }
         List<Category> categoryList=categoryMapper.selectCategoryChildrenById(categoryId);
         for (Category categoryItem :categoryList) {
             findChildrenCategory(categorySet,categoryItem.getId());
         }
         return categorySet;
     }

}

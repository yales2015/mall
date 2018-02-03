package com.yy.controller.backend;

import com.yy.common.Const;
import com.yy.common.ResponseCode;
import com.yy.common.ServerResponse;
import com.yy.pojo.Category;
import com.yy.pojo.User;
import com.yy.service.ICategoryService;
import com.yy.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by yy on 2018/1/17.
 */
@Controller
@RequestMapping("/manager/category")
public class CategoryManageController {
    @Autowired
    IUserService iUserService;

    @Autowired
    ICategoryService iCategoryService;

    @RequestMapping(value="/add",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addCategory(HttpSession session,String categoryName,@RequestParam(value="parentId",defaultValue = "0")int parentId){
        User currentUser=(User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        ServerResponse<String> response=iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()){
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iCategoryService.addCategory(categoryName,parentId);
    }

    @RequestMapping(value="/set_name",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setCategoryName(HttpSession session,int categoryId,String categoryName){
        User currentUser=(User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        ServerResponse<String> response=iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()){
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iCategoryService.setCategoryName(categoryId,categoryName);
    }

    @RequestMapping(value = "/get_children_parallel",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Category>> getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId",defaultValue="0") int categoryId){
        User currentUser=(User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        ServerResponse<String> response=iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()){
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 查询当前节点的id和递归子节点的id
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/get_deep_children",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Integer>> getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue="0") int categoryId) {
        User currentUser=(User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        ServerResponse<String> response=iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()){
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iCategoryService.getCategoryAndChildrenById(categoryId);
    }
}

package com.yy.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yy.common.Const;
import com.yy.common.ResponseCode;
import com.yy.common.ServerResponse;
import com.yy.pojo.Product;
import com.yy.pojo.User;
import com.yy.service.IFileService;
import com.yy.service.IProductService;
import com.yy.service.IUserService;
import com.yy.util.PropertiesUtil;
import com.yy.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by yy on 2018/1/19.
 */
@Controller
@RequestMapping("/manager/product")
public class ProductManageController {
    @Autowired
    IUserService iUserService;

    @Autowired
    IProductService iProductService;

    @Autowired
    IFileService iFileService;

    /**
     * 物品存储
     *
     * @param session
     * @param product
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> productSave(HttpSession session, Product product) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        ServerResponse<String> response = iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()) {
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iProductService.saveOrUpdateProduct(product);
    }

    /**
     * 设置物品状态，上下架
     *
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "/set_sale_status", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setSaleStatus(HttpSession session, int productId, int status) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        ServerResponse<String> response = iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()) {
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iProductService.setSaleStatus(productId, status);
    }

    /**
     * 获取商品详情
     *
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ProductDetailVo> getDetail(HttpSession session, int productId) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        ServerResponse<String> response = iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()) {
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iProductService.getDetail(productId);
    }

    /**
     * 按页获取商品
     *
     * @param session
     * @param pageNum  第几页
     * @param pageSize 页面容量
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> List(HttpSession session,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        ServerResponse<String> response = iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()) {
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iProductService.list(pageNum, pageSize);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> productSearch(HttpSession session, String productName, Integer productId,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        ServerResponse<String> response = iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()) {
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        return iProductService.search(productName, productId, pageNum, pageSize);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map> upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        //权限判断
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        ServerResponse<String> response = iUserService.checkAdminRole(currentUser);
        if (!response.isSuccess()) {
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
        //文件上传
        String path = request.getSession().getServletContext().getRealPath("path");
        String targetFileName = iFileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            return ServerResponse.createByErrorMsg("上传失败");
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping(value = "/richtext_img_upload", method = RequestMethod.POST)
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        //权限判断
        Map resultMap = Maps.newHashMap();

        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请使用管理员用户登录");
            return resultMap;
        }
        ServerResponse<String> checkResponse = iUserService.checkAdminRole(currentUser);
        if (!checkResponse.isSuccess()) {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
        //文件上传
        String path = request.getSession().getServletContext().getRealPath("path");
        String targetFileName = iFileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }

    /**
     * 跳转到uploading.html页面
     *
     * @return
     */
    @RequestMapping(value = "/uploading", method = RequestMethod.GET)
    public String goUpload() {
        return "uploading";
    }
}
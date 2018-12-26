package com.yxbkj.yxb.controller;

import com.alibaba.fastjson.JSON;
import com.yxbkj.yxb.entity.module.Code;
import com.yxbkj.yxb.entity.module.Result;
import com.yxbkj.yxb.entity.vo.CalculatePremiumParam;
import com.yxbkj.yxb.entity.vo.OrderParam;
import com.yxbkj.yxb.entity.vo.ZnProductParam;
import com.yxbkj.yxb.entity.vo.ZrProductParam;
import com.yxbkj.yxb.feign.ServerOrderFeignClient;
import com.yxbkj.yxb.util.AccessToken;
import com.yxbkj.yxb.util.StringUtil;
import com.yxbkj.yxb.util.WebUtils;
import com.yxbkj.yxb.util.pic.ImgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * <p>
 * 通用工具接口 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-08-08
 */
@Api(value = "CommController",description = "通用工具接口")
@Controller
@RequestMapping("/comm")
public class CommController {




}

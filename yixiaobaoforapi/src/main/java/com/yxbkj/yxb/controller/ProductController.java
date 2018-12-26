package com.yxbkj.yxb.controller;
import com.yxbkj.yxb.common.utils.Code;
import com.yxbkj.yxb.common.utils.Result;
import com.yxbkj.yxb.service.ProductInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
/**
 * <p>
 * 产品信息表 前端控制器
 * </p>
 *
 * @author 李明
 * @since 2018-07-23
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductInfoService productInfoService;
    /**
     * 作者: 李明
     * 描述: 查看所有产品信息
     * 备注:
     * @param productName
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "查看所有产品信息",notes = "密方式 yxbkj的MD5值+所有参数拼接成字符(limit=1&page=1)的值 加密 成 签名参数传入")
    @GetMapping("/getProductByPage")
    public Result<Map<String, Object>> ProductInfoList(@ApiParam(value = "产品名称",required = false)@RequestParam(value="productName",required = false)String productName,
                                                       @ApiParam(value = "当前页码",required = true)@RequestParam(value="page")Integer page,
                                                       @ApiParam(value = "每页数量",required = true)@RequestParam(value="limit")Integer limit) {
        logger.info("查询产品列表信息------------------------------------------");
        Map<String, Object> result = productInfoService.selectProductInfoList(productName, page, limit);
        Result<Map<String, Object>> tResult = new Result<>(Code.SUCCESS, "数据查询成功!", result, Code.IS_ALERT_NO);
        return tResult;
    }
}

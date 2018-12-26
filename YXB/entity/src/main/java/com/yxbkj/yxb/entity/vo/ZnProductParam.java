package com.yxbkj.yxb.entity.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * <p>
 * 众安产品跳转vo
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
public class ZnProductParam extends BaseTokenVo implements Serializable {
    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String product_id;
    /**
     * 客户端来源
     */
    @ApiModelProperty(value = "客户端来源")
    private String source;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

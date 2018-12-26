package com.yxbkj.yxb.common.utils;

import com.baomidou.mybatisplus.plugins.Page;
import com.yxbkj.yxb.common.entity.ResultPage;
import com.yxbkj.yxb.common.entity.YxbConstants;

/**
 * 分页处理类
 *
 * @author lideyang
 * @date 2018/7/12
 */
public class PageHelper {
    private PageHelper() {
    }

    /**
     * 构造数据返回对象
     *
     * @param page mybatis分页对象
     * @return 返回参数对象
     */
    public static <T> ResultPage<T> getResultPage(Page<T> page) {
        ResultPage<T> resultPage = new ResultPage<>();
        resultPage.setList(page.getRecords());
        resultPage.setPageNo(page.getCurrent());
        resultPage.setPages(page.getPages());
        resultPage.setPageSize(page.getSize());
        resultPage.setTotalSize(page.getTotal());
        resultPage.setSuccess(true);
        resultPage.setMsg("查询成功");
        return resultPage;
    }

    /**
     * 获取MyBatisPlus 分页page对象
     *
     * @param page  页码
     * @param limit 页面大小
     * @param <T>   泛型
     * @return 分页page对象
     */
    public static <T> Page<T> getPage(Integer page, Integer limit) {
        Page<T> pageHelper = new Page<>();
        if (page != null) {
            if (limit > YxbConstants.MAX_PAGE_CURRENT) {
                limit = YxbConstants.MAX_PAGE_CURRENT;
            }
            pageHelper.setCurrent(page);
        }
        if (limit != null) {
            if (limit > YxbConstants.MAX_PAGE_LIMIT) {
                limit = YxbConstants.MAX_PAGE_LIMIT;
            }
            pageHelper.setSize(limit);
        }
        return pageHelper;
    }
}

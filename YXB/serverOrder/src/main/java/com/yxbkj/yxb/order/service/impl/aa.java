package com.yxbkj.yxb.order.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.yxbkj.yxb.order.mapper.FuelPayOrderMapper;
import com.yxbkj.yxb.util.aliyun.SmsUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zy
 * @desc
 * @since
 */
@MapperScan("com.yxbkj.yxb.order.mapper")
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.yxbkj.yxb.*")
public class aa {

    public static void main(String[] args) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.rechargeNotifyForWxH5Recharge("OPAY20181225181746011");
    }
}

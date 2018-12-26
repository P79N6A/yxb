//package com.yxbkj.yxb.system.webservice;
//
//import org.apache.cxf.Bus;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.xml.ws.Endpoint;
//
///**
// * cxf配置
// *
// *
// * @author：Liming
// * @date：2018年09月19日 下午9:37:20
// */
//@Configuration
//public class CxfConfig {
//
//    @Autowired
//    private Bus bus;
//
//    @Autowired
//    private ProposalSaveService proposalSaveService;
//
//    @Bean
//    public Endpoint endpoint(){
//        EndpointImpl endpoint = new EndpointImpl(bus, proposalSaveService);
//        endpoint.publish("/proposalSaveService");
//        return endpoint;
//    }
//}

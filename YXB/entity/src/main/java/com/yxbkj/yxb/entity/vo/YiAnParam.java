package com.yxbkj.yxb.entity.vo;

import java.io.Serializable;
import java.util.List;

public class YiAnParam implements Serializable  {

    public String requestTime  ;
    public String outBusinessCode  ;
    public String productId  ;
    public String agrtCode  ;
    public String source  ;
    public String interfaceCode  ;
    public String dataSource  ;
    public String token  ;
    public  Data data;
    public class Data implements Serializable {

        public  CreateOrderReq createOrderReq;
        public class CreateOrderReq implements Serializable  {
            public List<OrderList> orderList;
            public class OrderList implements Serializable  {

                public String premium  ;
                public int uwCount  ;
                public String endDate  ;
                public String startDate  ;public List<ItemAcciList> itemAcciList;
                public class ItemAcciList implements Serializable  {

                    public String nominativeInd  ;
                    public String quantity  ;
                    public String occupationCode  ;
                    public String rationType  ;public List<AcciInsuredList> acciInsuredList;
                    public class AcciInsuredList implements Serializable  {

                        public String docType  ;
                        public String sex  ;
                        public String customerFlag  ;
                        public String docNo  ;
                        public String birthDate  ;
                        public String appliRelation  ;
                        public String customerName  ;
                        public String phoneNo  ;public List<AcciBenefitList> acciBenefitList;
                        public class AcciBenefitList implements Serializable  {

                            public String insuredRelation  ;
                            public String docType  ;
                            public String sex  ;
                            public String customerFlag  ;
                            public String benifitPercent  ;
                            public String docNo  ;
                            public String birthDate  ;
                            public String customerName  ;
                            public String phoneNo  ;

                        }


                    }


                }
                public List<CustomerList> customerList;
                public class CustomerList implements Serializable  {

                    public String customerType  ;
                    public String docType  ;
                    public String sex  ;
                    public int customerFlag  ;
                    public String customerSameInd  ;
                    public String docNo  ;
                    public String birthDate  ;
                    public String email  ;
                    public String customerName  ;
                    public String phoneNo  ;

                }
                public List<RiskDynamicList> riskDynamicList;
                public class RiskDynamicList implements Serializable  {

                    public String fieldAO  ;
                    public String fieldAP  ;
                    public String fieldAM  ;
                    public String fieldAN  ;

                }


            }


        }

    }

}
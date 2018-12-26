package com.yxbkj.yxb.util.zhongan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ZhongAnUtils {
    //String afterencrypt =RSAUtils.encrypt(PUCLIC_KEY,content);加密
    // String decryptStr =RSAUtils.decrypt(PRIVATE_KEY,jiami);解密
    //String sign = RSA.sign(content, PRIVATE_KEY, "utf-8");加签
    // boolean verify = RSA.verify(content, sign, PUCLIC_KEY, "utf-8");验签

    //private $baseUrl = 'https://ztg-uat.zhongan.com/promote/entrance/promoteEntrance.do?redirectType=h5&promotionCode=INST180369260001&productCode=PRD171119404003&promoteCategory=single_product&token=';//测试
    //private $baseUrl = 'https://ztg.zhongan.com/promote/entrance/promoteEntrance.do?redirectType=h5&promotionCode=INST180310527121&productCode=PRD171093420016&promoteCategory=single_product&token=';//线上
    public static String baseUrl  = "https://chexian.zhongan.com/aladin/entrance.do?redirectType=H5&aldLinkId=3186&token=8d321860a7753141b7e2a461c82e8ba0";//线上




    /**
     * 描述: 纵安获取跳转链接
     * 作者: 李明
     * 备注:
     * @param memberId
     * @return
     */
    public static String getRedirectUrl(String memberId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",memberId);
        String encrypt = com.yxbkj.yxb.util.zhongan.encrypt.RSAUtils.encrypt(RSAUtils.publicKey, jsonObject.toJSONString());
        String url = baseUrl+"&bizContent="+encrypt;
        return url;
    }

    /**
     * 描述: 纵安获取跳转链接
     * 作者: 李明
     * 备注:
     * @param memberId
     * @return
     */
    public static String getRedirectUrl(String memberId,String productId,String source,String url){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberId",memberId);
        jsonObject.put("productId",productId);
        jsonObject.put("source",source);
        //String encrypt = com.yxbkj.yxb.util.zhongan.encrypt.RSAUtils.encrypt(RSAUtils.publicKey, jsonObject.toJSONString());
        String encrypt = RC4Util.encryRC4String(jsonObject.toJSONString(), RC4Util.key);
        if(url.contains("?")){
            url+="&memberId="+memberId;
        }else{
            url+="?memberId="+memberId;
        }
        url+="&productId="+productId;
        url+="&source="+source;
        url = url+"&bizContent="+encrypt;
        return url;
    }


    /**
     * 描述: 获取纵安回调信息解密出来的JSON对象
     * 作者: 李明
     * 备注:
     * @param str
     * @return
     */
    public static JSONObject decrypt(String str){
        JSONObject jsonObject = (JSONObject) JSON.parse(str);
        String bizContent = jsonObject.getString("bizContent");
        String decrypt =com.yxbkj.yxb.util.zhongan.encrypt.RSAUtils.decrypt(RSAUtils.privateKey,bizContent);
        //{"bizContent":"a5f4b2b813285f595f227aca103020d7c4166d7c5468b190099eca2ca2f5300ae03aa187da9163d89f121a120a99fc978d5d17e3229d35f1009d132962e710c5bbbaffcad7f4e98e50e7b1d6ecf3cee03fe3013ba6f7558d7292f8f4513b7bfca6a808258f55e7782905fd0665fe0df6539de3d8c63e394dba5a7bb48387ade18be5e21d874ed2df8dab2050b5e4d1b2b12469911edd2d69f2f81a2fb4fc0953c922d0405d2eba2892f5586a17d326fc523651d04878322738b4135bc7c2037e6a14fba8d9405c484f647023c83d15c303d6d05feb9708659dda7c1f8dac3184aaff008ba16b7307339d94448f99d52fec57648ac6a1e69c6c96cf3ea126168fc9370644afc289faa6bf40ea3bbf9563514c85916a988d9d0b648e9e986f5a29572c0d68a0e10a80a9425d0ddb71fa1bc8e4e54609f184a12a4b746cabe85df8a950589e40d4800e47e94cc448ba237f58a7a076452131cf7fb96731a54d2b5ba1a5f00c7ad416fc587153b076f0739fb1494d831cc306b8305ead24c217824ca131f933c024b4e14b973316eb112b294776b490890274b07bc195d0161ef23c96e9da8875e1be54926548e7dd686bb119d900b8b97e957e74d7874979c9321a6ae2341ef362d3c1d1dd4ea89d0b2983aca03889690660d6626f8df467406741ea1be66d435f6ca13a6e6e99b2649f3dd2b37c3870ac1278d42621f22cdfd8928ea1652dd6cbc33a97cfea00b3e649f8ab6119af9d4850c154301101cbcf8381b4a837f92bf4fc54286e9ad26c723f69f6e822c06ea4d579a840ba9a29f8d7b99732a4f0571d09c867ca1b778081e40e4af263cd778e978634239612a3a56e6a8853be8459761935200977cbb7ef75d695381bedd7d3993b","effectiveDate":"2018-05-26","expiryDate":"2019-05-25","notifyType":"1","policyNo":"castle6da6064ec25a4f02937a965ae8791add","premium":"1100.00","productName":"1110保骉车险","promoteCode":"INST180369260001","promoteFee":"0.00","promoteName":"阿斯顿","vehicleType":"2"}
        return (JSONObject) JSON.parse(decrypt);
    }

}

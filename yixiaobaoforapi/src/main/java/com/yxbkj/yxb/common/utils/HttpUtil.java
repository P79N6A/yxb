package com.yxbkj.yxb.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 
 * @Author Lin_J
 * @ClassName: HttpUtil
 * @Description: (接口请求工具类)
 * @date 2017年10月23日 上午11:52:25
 *
 */
public class HttpUtil {

	private static Log logger = LogFactory.getLog(HttpUtil.class);

	/**
	 * 请求配置
	 */
	private static RequestConfig requestConfig = null;
	
	/**
	 * 连接池
	 */
	private static PoolingHttpClientConnectionManager cm = null;

	/**
	 * cookie管理器
	 */
	// private static CookieStore cookieStore =null;
	/**
	 * @author Lin_J
	 * <p>Title:超时时间初始化和连接池 </p> 
	 * <p>Description: 单例模式</p>
	 */
	public HttpUtil(){
		if(requestConfig == null){
			requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectionRequestTimeout(60000).setConnectTimeout(60000).build();
			// 创建cookie store的本地实例
			// cookieStore = new BasicCookieStore();
			// 创建HttpClient上下文
			//HttpClientContext context = HttpClientContext.create();
			// context.setCookieStore(cookieStore);
		}
		LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
	                .register("https", sslsf)
	                .register("http", new PlainConnectionSocketFactory())
	                .build();
	        cm =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
	        cm.setMaxTotal(200);
	        cm.setDefaultMaxPerRoute(20);
	}

	/**
	 * 
	 * @Auther Lin_J @Title: doPostUTF_8 @Description:
	 * (已做编码处理) @param @param url @param @param param @param @return
	 * 设定文件 @return String 返回类型 @throws
	 */
	public static String doPost(String url, Map<String, Object> param) {
		Assert.hasText(url,"url信息不能为空！");
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
		String resultString = "";
		try {
            // 设置参数到请求对象中
			HttpPost httpPost = new HttpPost(url);
			// 设置头部信息
			httpPost.setHeaders(createHeaders(null));
			//httpPost.setConfig(requestConfig);
			if (param != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(convertParams(param), Consts.UTF_8));
			}
			// 执行http请求
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				if (response.getStatusLine().getStatusCode() == 200 && response.getEntity() != null) {
					resultString = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
					EntityUtils.consume(response.getEntity());
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return resultString;
	}
	
	 /** 
	  * @auther Lin_J
	  * @className: (参数为json字符串的请求) 
	  * @param url 
	  * @param headVal 可为空
	  * @param jsonStr 可为空
	  * @return String
	  * @date 2018/05/30 15:53:45
	  */
    public static String doPostJson(String url,Map<String, String> headVal,String jsonStr) {
    	Assert.hasText(url,"url信息不能为空！");
        // 创建Httpclient对象  
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
        String resultString = "";  
        try {
//			if(isCreateToken){
//				// 生成token并拼接在url上，用于海康api
//				String[] strings=createToken(jsonStr, url);
//				if(null!=strings){
//                    url=strings[0];
//                    jsonStr=strings[1];
//                }
//			}
            // 创建Http Post请求  
            HttpPost httpPost = new HttpPost(url);
            if(!CollectionUtils.isEmpty(headVal))
            	httpPost.setHeaders(createHeadersByMap(headVal));
            if(StringUtils.isNotEmpty(jsonStr)){
				StringEntity entity = new StringEntity(jsonStr,"utf-8");
				httpPost.setEntity(entity);
				entity.setContentType("application/json");
			}
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            // 执行http请求  
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
            	if(response.getEntity()!=null && response.getStatusLine().getStatusCode() == 200 ){
            		resultString = EntityUtils.toString(response.getEntity(),Consts.UTF_8);
            		EntityUtils.consume(response.getEntity());
            	} else {
            	    logger.error("请求错误：" +  response.getStatusLine().getStatusCode());
                }
			} finally {
				response.close();
			}
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return resultString;  
    }

	/** 
	 * @auther Lin_J
	 * @className: (拼接验签参数) 
	 * @param  params
	 * @return  String
	 * @date 2018/05/30 15:55:26
	 */
	public static String getSign(TreeMap<String, Object> params, String appid, Long timestamp) {
		StringBuilder sb = new StringBuilder();
		if (params != null) {
			params.forEach((key, value) ->{
				sb.append(key);
				sb.append("=");
				sb.append(value);
				sb.append("&");
			});
		}
		sb.append(appid);
		sb.append(timestamp);
		logger.info(">>>>>>>>>>>>>>>>>>>>>拼接好的sign："+sb.toString());
		return DigestUtils.md5Hex(sb.toString());
	}

	/** 
	 * @auther Lin_J
	 * @className: (这里用一句话描述这个类的作用或者名称) 
	 * @param   map
	 * @return  files
	 * @date 2018/05/30 15:56:12
	 */
	public static String sendUploadCommon(String url, Map<String, Object> map, Map<String, File> files)
			throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
		String body = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			// httpPost.setConfig(requestConfig);
			MultipartEntityBuilder multipartEntityBuilder=MultipartEntityBuilder.create(); 
			/**
			 * 添加文件
			 */
			if (files != null) {
				for (Entry<String, File> entry : files.entrySet()) {
					multipartEntityBuilder.addPart(entry.getKey(), new FileBody(entry.getValue()));
				}
			}
			//httpPost.setHeader("partner-identify", StringUtils.isEmpty(getXydHeather()) ? null : getXydHeather());
			/**
			 * 装填参数
			 */
			if (map != null) {
				for (Entry<String, Object> entry : map.entrySet()) {
					multipartEntityBuilder.addPart(entry.getKey(), new StringBody((String)entry.getValue(), ContentType.TEXT_PLAIN));
				}
			}
			httpPost.setEntity(multipartEntityBuilder.build());
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				// 获取结果实体
				HttpEntity entity = response.getEntity();
				if (entity != null && response.getStatusLine().getStatusCode() == 200) {
					// 按指定编码转换结果实体为String类型
					body = EntityUtils.toString(entity, Consts.UTF_8);
				}
				EntityUtils.consume(entity);
			}finally{
				// 释放链接
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			httpclient.close();
		}
		return body;

	}
	/**
	 * @auther Lin_J
	 * @className: (发起get请求)
	 * @param	url
	 * @param	parameterMap
	 * @return  String
	 * @date 2018/05/30 15:57:36
	 */
	public static String doGet(String url, Map<String, Object> parameterMap, boolean isCreateToken) {
        Assert.hasText(url,"url不能为空！");
        String result = null;
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
        try {
			url+=(StringUtils.contains(url, "?") ? "&" : "?")
					+ EntityUtils.toString(new UrlEncodedFormEntity(convertParams(parameterMap), Consts.UTF_8));
            // 创建token
            if(isCreateToken){
				url=getCreateToken(url);
			}
			System.out.println(url);
            HttpGet httpGet = new HttpGet(url);
			// httpGet.setConfig(requestConfig);
            // 设置头部信息
			// httpGet.setHeaders(createHeaders(null));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
				if (response.getStatusLine().getStatusCode() == 200 && response.getEntity() != null) {
					result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
					EntityUtils.consume(response.getEntity());
				}
			} finally {
				response.close();
			}
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return result;
    }
	/**
	 * 
	 * @Auther Lin_J
	 * @Title: getRegion 
	 * @Description:  (根据ip地址获取所在地区)
	 * @param ip ip地址
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getRegion(String ip){
		if(!StringUtils.isEmpty(ip) && (ip.indexOf("192.168")>=0 || ip.indexOf("127.0.0.1")>=0 || ip.indexOf("0:0:0:0:0:0:0:1")>=0)){
			return "地区未知,可能是本地测试";
		}
		Map<String,Object> params=new HashMap<>();
		params.put("ip", ip);
		// 淘宝根据ip获取归属地
		String url=PEnvUtil.getInstance().get("query.region.taobao.url");
		if(StringUtils.isEmpty(url))
			return "";
		String region=doGet(url, params, false);
	 	JSONObject jsonObject = JSONObject.parseObject(region);
        Integer code = jsonObject.getInteger("code");
        if (code == null) {
        	logger.info("淘宝获取根据ip获取归属地失败！");
            return sina(params);
        }
        if (code != 0) {
        	logger.info("淘宝获取根据ip获取归属地失败！");
            return sina(params);
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (data == null) {
        	logger.info("淘宝获取根据ip获取归属地失败！");
            return sina(params);
        }
	    String regionInfo = data.getString("region");
		if (!StringUtils.isEmpty(regionInfo)){
			try {
				return new String(regionInfo.getBytes(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return regionInfo;
	}

	/**
	 * @auther Lin_J
	 * @className: (创建好自定义头部信息)
	 * @param  headersMap
	 * @return Header[]
	 * @date 2018/06/05 20:11:19
	 */
	public static Header[] createHeadersByMap(Map<String, String> headersMap){
		List<Header> headers = new ArrayList<>();
		headersMap.forEach((k,v)-> headers.add(new BasicHeader(k, v)));
		if(!CollectionUtils.isEmpty(headers)) {
			Header[] _headers = new Header[headers.size()];
			return headers.toArray(_headers);
		}
		return null;
	}

	/**
	 * 创建Header(模拟浏览器的header值)
	 */
	public static Header[] createHeaders(Header session) {

		List<Header> headers = new ArrayList<>();

		if (session != null) {
			headers.add(session);
		}

		Header header = null;
		header = new BasicHeader(HttpHeaders.CONTENT_TYPE,
				"application/x-www-form-urlencoded");
		headers.add(header);
		header = new BasicHeader("DNT", "1");
		headers.add(header);

		header = new BasicHeader("Pragma", "no-cache");
		headers.add(header);

		header = new BasicHeader(HttpHeaders.USER_AGENT,
				"Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
		headers.add(header);

		header = new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN");
		headers.add(header);

		header = new BasicHeader(HttpHeaders.ACCEPT,
				"text/html, application/xhtml+xml, */*");
		headers.add(header);

		header = new BasicHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate");
		headers.add(header);

		header = new BasicHeader(HttpHeaders.CONNECTION, "Keep-Alive");
		headers.add(header);

		Header[] _headers = new Header[headers.size()];
		headers.<Header> toArray(_headers);

		return _headers;
	}

	/**
	 * @auther Lin_J
	 * @className: (根据新浪api获取)
	 * @param  params
	 * @return     返回类型
	 * @date 2018/05/30 16:00:54
	 */
	public static String sina(Map<String,Object> params){
		String url=PEnvUtil.getInstance().get("query.region.url");
		if(StringUtils.isEmpty(url))
			return "";
		String region=doGet(url, params, false);
		if (!StringUtils.isEmpty(region))
			region=region.trim();//返回的数据带有大量空格
		return region;
	}

	/**
	 * @auther Lin_J
	 * @className: (海康统一生成token方法)
	 * @param jsonStr json参数字符串
	 * @param url 请求的url
	 * @date 2018/06/05 19:09:09
	 */
	public static String[] createToken(String jsonStr,String url){
		return null;
	}

	/**
	 * 针对Get方法的token创建
	 * @param url
	 * @return
	 */
	public static String getCreateToken(String url){
		return  null;
	}

	/**
	 * 请求工具类map参数公共转换
	 * @param params
	 * @return
	 */
	public static List<NameValuePair> convertParams(Map<String, Object> params){
		List<NameValuePair> nameValuePairs= Lists.newArrayList();
		if (params != null) {
			params.forEach((k, v)->{
				String value = ConvertUtils.convert(v);
				if (StringUtils.isNotBlank(k)) {
					nameValuePairs.add(new BasicNameValuePair(k, value));
				}
			});
		}
		return nameValuePairs;
	}
}
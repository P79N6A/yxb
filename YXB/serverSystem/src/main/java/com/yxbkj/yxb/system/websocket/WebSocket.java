package com.yxbkj.yxb.system.websocket;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 2018年5月18日16:11:48
 */
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocket {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private  static Logger loggers = LoggerFactory.getLogger(WebSocket.class);
    /**
     * 在线人数
     */
    public static int onlineNumber = 0;
    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    public static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    /**
     * 会话
     */
    public Session session;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session)
    {
        onlineNumber++;
        if(onlineNumber>=10000){
            return;
        }
        logger.info("现在来连接的客户id："+session.getId()+"用户名："+username);
        this.username = username;
        this.session = session;
        logger.info("有新连接加入！ 当前在线人数" + onlineNumber);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给所有人发送通知，说我上线了
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("messageType",1);
            map1.put("username",username);
            //sendMessageAll(JSON.toJSONString(map1),username);

            //把自己的信息加入到map当中去
            clients.put(username, this);

        }
        catch (Exception e){
            logger.info(username+"上线的时候通知所有人发生了错误");
        }



    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("服务端发生了错误"+error.getMessage());
        //error.printStackTrace();
    }
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose()
    {
        logger.info("连接关闭");
        onlineNumber--;
        //webSockets.remove(this);
        clients.remove(username);
        logger.info("有连接关闭！ 当前在线人数" + onlineNumber);
    }
    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        logger.info("收到客户端的消息");
    }

    public void sendMessageTo(String message, String ToUserName) throws IOException {
        logger.info("发送消息");
        for (WebSocket item : clients.values()) {
            if (item.username.equals(ToUserName) ) {
                item.session.getAsyncRemote().sendText(message);
                break;
            }
        }
    }
    public void sendMessageAll(String message,String FromUserName) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 作者: 李明
     * 描述: 发送Android推送消息
     * 备注:
     * @param memberId  会员ID
     * @param title 标题
     * @param content 内容
     * @param map 自定义的键值对
     */
    public  static  void sendHtmlMessage(String memberId,String title,String content,Map<String,String> map){
        loggers.info("开始发送H5消息");
        try{
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("title",title);
            map1.put("content",content);
            Set<String> keys = map.keySet();
            for(String key : keys){
                map1.put(key,map.get(key));
            }
            WebSocket socket = WebSocket.clients.get(memberId);
            loggers.info("开始发送H5消息"+JSON.toJSONString(map1));
            if(socket!=null){
                socket.session.getAsyncRemote().sendText(JSON.toJSONString(map1));
            }else{
                //对方不在线 不做处理
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineNumber;
    }

}

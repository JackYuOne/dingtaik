package com.jingyu.dingtaik.server;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.taobao.api.ApiException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author 20252
 */
@Service
public class DingTalkServer {

    //static String mobile = "15576777131";


    /**
     * 获取企业内部对应的access_token
     * @param appKey
     * @param appSecret
     * @return
     * @throws ApiException
     */
    public  String getAccessToken(String appKey,String appSecret) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        //应用的唯一标识key。
        request.setAppkey(appKey);
        //应用的密钥
        request.setAppsecret(appSecret);
        //请求方式
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }

    /**
     * 根据手机号号码拿到钉钉userid
     * @param mobile
     * @param appKey
     * @param appSecret
     * @return
     * @throws ApiException
     */
    public  String getUserid(String mobile,String appKey,String appSecret, String accessToken) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        //手机号码
        req.setMobile(mobile);
        OapiV2UserGetbymobileResponse rsp = client.execute(req,accessToken);
        return rsp.getResult().getUserid();
    }

    public  Boolean DingTalk(Long agentId,String useridList,String content,String accessToken ){

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        //发送消息时使用的微应用的AgentID
        request.setAgentId(agentId);
        //接收者的userid列表，最大用户列表长度100
        request.setUseridList(useridList);
        //是否发送给企业全部用户
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        //消息内容 支持文本 图片消息 语言消息 文件消息 连接消息 OA消息 Markdown消息 卡片消息
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        //setContent 为消息内容
        msg.getText().setContent(content);
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response rsp = null;
        try {
            rsp = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            return false;
        }
        String body = rsp.getBody();
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject().getAsJsonObject();
        int errCode = jsonObject.get("errcode").getAsInt();
        if(errCode==0){
            return true;
        }else{
            return false;
        }
    }

    public void DingTalkSend(String mobile,String content){




    }
}

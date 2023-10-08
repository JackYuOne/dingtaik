package com.jingyu.dingtaik.controller;

import com.jingyu.dingtaik.entity.DingTalkVo;
import com.jingyu.dingtaik.server.DingTalkServer;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 20252
 */
@RestController
@RequestMapping("/test/send")
public class DingTalkController {

    @Autowired
    private DingTalkServer dingTalkServer;

    @PostMapping
    public Boolean DingTalkSend(@RequestBody DingTalkVo dingTalkvo) {
        Boolean aBoolean;
        try {
            String accessToken = dingTalkServer.getAccessToken(dingTalkvo.getAppKey(), dingTalkvo.getAppSecret());

            String userid = dingTalkServer.getUserid(dingTalkvo.getMobile(), dingTalkvo.getAppKey(), dingTalkvo.getAppSecret(), accessToken);

            aBoolean = dingTalkServer.DingTalk(dingTalkvo.getAgentId(), userid, dingTalkvo.getContent(), accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
            return false;
        }
        return aBoolean;
    }
}

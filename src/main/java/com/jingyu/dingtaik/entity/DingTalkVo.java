package com.jingyu.dingtaik.entity;

import lombok.Data;

/**
 * @author 20252
 */
@Data
public class DingTalkVo {
    private String appKey;
    private String appSecret;
    private String mobile;
    private String content;
    private Long agentId;
}

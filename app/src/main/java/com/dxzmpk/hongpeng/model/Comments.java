package com.dxzmpk.hongpeng.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    private String orderId;

    private String checkStatus;
    /**
     * 评论
     */
    private String comments;

    private Integer ranks;

    private Date createTime;

    private String nickName = null;

    private String avatarUrl = null;

}

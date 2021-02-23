package com.dxzmpk.hongpeng.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String openId;

    private String nickName;

    /**
     * 手机号-必须
     */
    private String phone;

    /**
     * 性别
     */
    private String gender;

    /**
     * 地区
     */
    private String district;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 备注
     */
    private String review;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 验证码
     */
    private String code;

    public User(String nickName, String gender, String district, String birthday) {
        this.nickName = nickName;
        this.gender = gender;
        this.district = district;
        this.birthday = birthday;
    }
}


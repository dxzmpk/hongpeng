package com.dxzmpk.hongpeng.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity implements Serializable {
    private String actId;

    private String courseId;

    private String title;

    /**
     * 活动内容
     */
    private String content;

    /**
     * 活动是线上还是线下
     */
    private Integer isOnline;

    /**
     * 创建时间
     */
    private Date createTime;

    private Float price;

    /**
     * 销量
     */
    private Integer saleAmount = 0;


    /**
     * 库存
     */
    private Integer reserve;

    /**
     * 折扣
     */
    private Integer discount;

    /**
     * 备注
     */
    private BigDecimal promoteFee;

    private String pubState;

    /**
     * 阅读量
     */
    private Integer reading;

    /**
     * 阅读量
     */
    private String showPic;

    public Activity(String actId, String title, Integer isOnline, Float price, Integer reading, String showPic) {
        this.actId = actId;
        this.title = title;
        this.isOnline = isOnline;
        this.price = price;
        this.reading = reading;
        this.showPic = showPic;
    }
}


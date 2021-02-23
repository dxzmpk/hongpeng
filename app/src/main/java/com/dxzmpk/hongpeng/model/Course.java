package com.dxzmpk.hongpeng.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程表/门
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private String courseId;

    /**
     * 课程名称
     */
    private String title;

    /**
     * 课程简介
     */
    private String summary;

    /**
     * 课程富文本
     */
    private String detail;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 销量
     */
    private Integer saleAmount = 0;

    /**
     * 库存
     */
    private Integer reserve;

    /**
     * 发布状态
     */
    private String pubState;

    /**
     * 推广费
     */
    private BigDecimal promoteFee;

    /**
     * 展示图url
     */
    private String showPic;

    private List<Chapter> chapterList;
}
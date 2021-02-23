package com.dxzmpk.hongpeng.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private String videoId;

    private String chapterId;

    private String name;

    private String video;

    private String summary;

    private String index;
}
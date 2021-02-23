package com.dxzmpk.hongpeng.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    private String chapterId;

    private String courseId;

    private String name;

    private String index;

    private List<Video> children;
}
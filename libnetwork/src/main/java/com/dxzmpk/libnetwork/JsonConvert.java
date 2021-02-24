package com.dxzmpk.libnetwork;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

public class JsonConvert implements Convert {
    //默认的Json转 Java Bean的转换器
    @Override
    public Object convert(String response, Type type) {
        JSONObject jsonObject = JSON.parseObject(response);
        if (jsonObject != null) {
            Object data1 = jsonObject.get("data");
            return JSON.parseObject(data1.toString(), type);
        }
        return null;
    }
}

package com.venusz.admin.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class JsonData {
    private boolean flag;
    private String msg;
    private Object data;

    public JsonData(boolean flag){
        this.flag = flag;
    }

    public static JsonData success(String msg,Object data){
        JsonData jsonData = new JsonData(true);
        jsonData.msg = msg;
        jsonData.data = data;
        return jsonData;
    }

    public static JsonData success(Object data){
        JsonData jsonData = new JsonData(true);
        jsonData.data = data;
        return jsonData;
    }

    public static JsonData success(){
        JsonData jsonData = new JsonData(true);
        return jsonData;
    }

    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();

        result.put("flag",flag);
        result.put("data",data);
        result.put("msg",msg);
        return result;
    }


}

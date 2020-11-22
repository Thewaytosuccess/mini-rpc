package com.rpc.mini.codec.impl.json;

import com.alibaba.fastjson.JSONObject;
import com.rpc.mini.codec.Decoder;

/**
 * @author xhzy
 */
public class JsonDecoder implements Decoder {

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSONObject.parseObject(bytes,clazz);
    }
}

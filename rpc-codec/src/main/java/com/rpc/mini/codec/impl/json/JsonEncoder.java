package com.rpc.mini.codec.impl.json;

import com.alibaba.fastjson.JSON;
import com.rpc.mini.codec.Encoder;

/**
 * @author xhzy
 */
public class JsonEncoder implements Encoder {

    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}

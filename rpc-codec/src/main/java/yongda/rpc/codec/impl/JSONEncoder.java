package yongda.rpc.codec.impl;

import com.alibaba.fastjson.JSON;
import yongda.rpc.codec.Encoder;

public class JSONEncoder implements Encoder {

    @Override
    public byte[] encode(Object data) {
        return JSON.toJSONBytes(data);
    }
}

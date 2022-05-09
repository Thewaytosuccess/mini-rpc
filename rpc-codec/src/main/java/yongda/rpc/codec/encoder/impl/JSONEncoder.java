package yongda.rpc.codec.encoder.impl;

import com.alibaba.fastjson.JSON;
import yongda.rpc.codec.encoder.Encoder;

public class JSONEncoder implements Encoder {

    @Override
    public byte[] encode(Object data) {
        return JSON.toJSONBytes(data);
    }
}

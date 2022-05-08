package yongda.rpc.codec.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import yongda.rpc.codec.Decoder;

@Slf4j
public class JSONDecoder implements Decoder {

    @Override
    public <T> T decode(byte[] data, Class<T> clazz) {
        return JSON.parseObject(data, clazz);
    }
}

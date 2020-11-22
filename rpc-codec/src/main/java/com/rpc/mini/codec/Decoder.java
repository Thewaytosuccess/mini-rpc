package com.rpc.mini.codec;

/**
 * @author xhzy
 */
public interface Decoder {

    /**
     * 反序列化：将字节数组转化成object对象
     * @param bytes 字节数组
     * @param clazz
     * @return
     */
    <T> T decode(byte[] bytes, Class<T> clazz);
}

package com.rpc.mini.codec;

/**
 * @author xhzy
 */
public interface Encoder {

    /**
     *  序列化，将一个object对象转化成字节数组
     * @param obj 对象
     * @return 字节数组
     */
    byte[] encode(Object obj);
}

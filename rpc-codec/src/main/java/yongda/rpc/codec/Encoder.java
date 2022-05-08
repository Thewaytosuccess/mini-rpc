package yongda.rpc.codec;

public interface Encoder {

    /**
     * 序列化，将对象转化为二进制字节数组
     * @param data 对象
     * @return 字节数组
     */
    byte[] encode(Object data);
}

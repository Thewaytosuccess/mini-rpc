package yongda.rpc.codec;

public interface Decoder {

    /**
     * 反序列化，将字节数组转化为对象
     * @param data 字节数组
     * @param clazz 反序列化后对象的类型
     * @param <T> 反序列化后的对象类型
     * @return 反序列化后的对象
     */
    <T> T decode(byte[] data,Class<T> clazz);
}

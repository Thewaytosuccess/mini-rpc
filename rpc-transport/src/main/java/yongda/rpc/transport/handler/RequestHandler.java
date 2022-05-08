package yongda.rpc.transport.handler;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 负责传递输入输出流
 * @author cdl
 */
public interface RequestHandler {

    /**
     * 回调函数
     * @param is 请求参数序列化后的输入流
     * @param os 请求响应序列化后的输出流
     */
    void onRequest(InputStream is,OutputStream os);
}

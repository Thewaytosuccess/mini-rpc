package yongda.client;

import yongda.rpc.client.RpcClient;
import yongda.service.CalculateFacade;

/**
 * 如果要通过接口，调用接口的某个方法
 * 1.通过动态代理创建接口的实例，利用invoke方法实现rpc接口调用；
 * 在创建动态代理的过程中，可以通过invoke拦截到需要调用的方法，
 * 因而需要在invoke方法中完成客户端请求的发送和响应的获取；
 *
 * 客户端发送请求的过程：
 * 1.实例化rpc客户端，【因为以下的操作需要编解码器和客户端选择器selector，
 *   所有在客户端的实例化过程中，完成以上对象的实例化；】
 * 2.在invoke方法中完成request对象的装配；
 * 3.将request对象序列化 -- encoder
 * 4.选择一个服务端点建立连接 -- selector
 * 5.构建http请求，将序列化后的二进制数据流发送出去，得到响应的二进制流；
 * 6.将响应的二进制流，反序列化后，得到服务端的返回的对象 -- decoder；
 */
public class TestClient {

    public static void main(String[] args) {
        //todo 添加注解@Reference实现
        RpcClient client = new RpcClient();

        CalculateFacade proxy = client.getProxy(CalculateFacade.class);
        System.out.println("sum:"+proxy.sum(1,7));
        System.out.println("minus:"+proxy.minus(3,4));

    }
}

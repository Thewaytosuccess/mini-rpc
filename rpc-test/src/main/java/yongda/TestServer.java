package yongda;

import yongda.rpc.proto.registry.ServiceRegistry;
import yongda.rpc.server.RpcServer;

/**
 * @author  cdl
 *
 * 服务端启动过程
 * 1.服务端实例化，【1.1 因为处理客户端请求，需要解析客户端请求和响应调用结果给客户端，
 *   所以，需要在实例化的过程中，首先完成编解码器的实例化，为RequestHandler提供原料；
 *   1.2 其次调用服务端的接口，需要查找对应的服务，因此需要提前完成注册中心的实例化
 *   和invoker的实例化，进一步为RequestHandler提供原料；
 *   1.3 实例化TransportServer,TransportServer是Jetty Server的封装和抽象；
 *   TransportServer初始化，其实就是里面封装的Jetty Server的创建和初始化逻辑；
 *   加了一个回调接口RequestHandler作为初始化参数，用来侦听到客户端请求时，
 *   处理客户端请求，即从HttpServlet中获取到的输入输出流交给RequestHandler来处理；】
 * 2.服务注册；
 * 3.服务启动，就是里面封装的JettyServer的启动；
 *
 * JettyServer的创建过程：
 * Server实例化，并侦听指定的端口；
 * Servlet上下文ServletContextHandler注册到Server中；
 * HttpServlet的实例注册到上下文ServletContextHandler中；
 * HttpServlet能真正获取到客户端请求，doGet,doPost中的输入输出流，交给RequestHandler来处理；
 *
 * RequestHandler的处理逻辑：
 * 首先将从doGet,doPost中获取到输入流反序列化，转化成Request对象 --decode；
 * 根据Request对象从注册中心查找对应的服务 --lookup service；
 * 利用反射，调用查到的服务 -- invoke service；
 * 将结果序列化成二进制流，写入到response的输出流中 --encode；
 */
public class TestServer {

    public static void main(String[] args) {
        //todo 通过注解自动注册，配置管理
        ServiceRegistry.getInstance().register(CalculateFacade.class,new CalculateService());
        new RpcServer().start();

    }
}

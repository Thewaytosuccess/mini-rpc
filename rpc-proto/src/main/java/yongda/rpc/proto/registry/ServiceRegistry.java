package yongda.rpc.proto.registry;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import yongda.rpc.proto.request.Request;
import yongda.rpc.proto.service.ServiceDescriptor;
import yongda.rpc.common.ReflectionUtils;
import yongda.rpc.proto.service.ServiceInstance;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心
 * @author cdl
 */
@Slf4j
public class ServiceRegistry<T> {

    private static ServiceRegistry serviceRegistry = new ServiceRegistry();

    private final Map<ServiceDescriptor, ServiceInstance> registry;

    private Map<Class<T>,T> instanceRegistry = new ConcurrentHashMap<>();

    private ServiceRegistry(){
        this.registry = new ConcurrentHashMap<>();
    }

    public static ServiceRegistry getInstance(){
        return serviceRegistry;
    }

    public T getObject(Class<T> clazz){
        return instanceRegistry.get(clazz);
    }

    /**
     * 服务自动注册
     */
    @SneakyThrows
    public static void autoRegister(){
        String servicePackageConfig = "rpc.remote.service.package";
        registerService(servicePackageConfig,null);
    }

    @SneakyThrows
    public static void registerService(String servicePackageConfig,Callback callback){
        String servicePackage = getServicePackage(servicePackageConfig);
        String servicePath = ServiceRegistry.class.getClassLoader()
                .getResource("").getPath() +
                servicePackage.replaceAll("\\.", "/");

        log.info("service path = {}",servicePath);
        File file = new File(servicePath);
        if(file.exists() && file.isDirectory()){
            File[] list = file.listFiles();
            if(Objects.nonNull(list) && list.length > 0){
                for(File f:list){
                    Class<?> clazz = Class.forName(servicePackage + "." +
                            f.getName().substring(0,f.getName().lastIndexOf(".")));

                    if(clazz.isAnnotationPresent(Service.class)){
                        autoRegister(clazz);
                        if(Objects.nonNull(callback)){
                            callback.autowire(clazz);
                        }
                    }
                }
            }
        }
    }

    private static void autoRegister(Class<?> clazz){
        Class<?>[] interfaces = clazz.getInterfaces();
        if(Objects.isNull(interfaces) || interfaces.length == 0){
            getInstance().instanceRegistry.putIfAbsent(clazz,ReflectionUtils.newInstance(clazz));
        }else{
            Class<Object> interfaceClass= (Class<Object>)interfaces[0];
            getInstance().register(interfaceClass, ReflectionUtils.newInstance(clazz));
        }
    }

    @SneakyThrows
    private static String getServicePackage(String servicePackageConfig){
        String applicationProperties = "application.properties";
        Properties properties = new Properties();
        properties.load(ServiceRegistry.class.getClassLoader().getResourceAsStream(applicationProperties));
        return properties.getProperty(servicePackageConfig);
    }

    /**
     * 将接口的所有方法都注册到注册中心
     * @param interfaceClass 接口
     * @param instance 实例
     */
    private void register(Class<T> interfaceClass, T instance){
        //根据接口获取所有的公共方法
        Method[] publicMethods = ReflectionUtils.getPublicMethods(interfaceClass);

        //将所有的方法都注册的注册中心
        Arrays.stream(publicMethods).forEach(e -> registry.put(
                new ServiceDescriptor(interfaceClass,e),
                new ServiceInstance(instance,e)));
    }

    /**
     * 查找服务
     * @param request 请求
     * @return 服务实例
     */
    public ServiceInstance get(Request request){
        return registry.get(request.getServiceDescriptor());
    }
}

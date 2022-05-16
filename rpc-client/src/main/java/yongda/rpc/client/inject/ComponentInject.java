package yongda.rpc.client.inject;

import lombok.extern.slf4j.Slf4j;
import yongda.rpc.client.RpcClient;
import yongda.rpc.proto.registry.Callback;
import yongda.rpc.proto.registry.ServiceRegistry;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
public class ComponentInject {

    public static Object scan(Class<?> clazz){
        String servicePackageConfig = "rpc.component.scan.package";
        ServiceRegistry.registerService(servicePackageConfig, ComponentInject::autowire);
        return ServiceRegistry.getInstance().getObject(clazz);
    }

    private static void autowire(Class<?> clazz) {
        RpcClient client = new RpcClient();

        Arrays.stream(clazz.getDeclaredFields())
            .filter(e -> e.isAnnotationPresent(Resource.class))
            .forEach(e -> {
                //setter
                String field = e.getName();
                String setter = "set" + field.substring(0,1).toUpperCase()
                        + field.substring(1);
                try {
                    clazz.getDeclaredMethod(setter,e.getType()).invoke(
                            ServiceRegistry.getInstance().getObject(clazz),
                            client.getProxy(e.getType()));
                } catch (Exception ex) {
                    log.error(ex.getMessage(),ex);
                }
            });
    }
}

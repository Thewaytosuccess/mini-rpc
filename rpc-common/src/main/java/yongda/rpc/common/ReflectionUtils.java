package yongda.rpc.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 * @author cdl
 */
public class ReflectionUtils {

    /**
     * 创建对象
     */
    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取一个类的公共方法
     */
    public static Method[] getPublicMethods(Class clazz){
        Method[] declaredMethod = clazz.getDeclaredMethods();
        List<Method> list = new ArrayList<>();
        for(Method e:declaredMethod){
            if(Modifier.isPublic(e.getModifiers())){
                list.add(e);
            }
        }
        return list.toArray(new Method[0]);
    }

    /**
     * 调用某个类的方法
     */
    public static Object invoke(Object obj,Method method,Object... args){
        try {
            return method.invoke(obj,args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

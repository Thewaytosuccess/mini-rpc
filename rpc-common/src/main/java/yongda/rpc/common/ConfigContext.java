package yongda.rpc.common;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @author cdl
 */
@Slf4j
public class ConfigContext {

    private static Properties properties;

    private static final String APPLICATION = "application.properties";

    static {
        properties = new Properties();
        try {
            properties.load(ConfigContext.class.getClassLoader()
                    .getResourceAsStream(APPLICATION));
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    public static String get(String key){
        return properties.getProperty(key);
    }
}

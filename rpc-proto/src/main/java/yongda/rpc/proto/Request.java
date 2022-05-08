package yongda.rpc.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网络请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private ServiceDescriptor serviceDescriptor;

    private Object[] parameters;
}

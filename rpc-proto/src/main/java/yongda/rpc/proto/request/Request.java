package yongda.rpc.proto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yongda.rpc.proto.service.ServiceDescriptor;

/**
 * 网络请求
 * @author cdl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private String requestId;

    private ServiceDescriptor serviceDescriptor;

    private Object[] parameters;
}

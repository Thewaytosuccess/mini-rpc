package yongda.rpc.proto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务响应
 * @author cdl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String requestId;

    private int code = 200;

    private String message = "success";

    private Object data;
}

package yongda.rpc.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private int code = 1;

    private String message = "success";

    private Object data;
}

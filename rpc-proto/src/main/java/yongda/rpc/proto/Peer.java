package yongda.rpc.proto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 网络中的一个端点
 * @author cdl
 */
@Data
@AllArgsConstructor
public class Peer {

    private String host;

    private int port;
}

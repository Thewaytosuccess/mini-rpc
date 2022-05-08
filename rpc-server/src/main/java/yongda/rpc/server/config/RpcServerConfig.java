package yongda.rpc.server.config;

import lombok.Data;
import yongda.rpc.codec.Decoder;
import yongda.rpc.codec.Encoder;
import yongda.rpc.codec.impl.JSONDecoder;
import yongda.rpc.codec.impl.JSONEncoder;
import yongda.rpc.transport.server.TransportServer;
import yongda.rpc.transport.server.impl.HttpTransportServer;

/**
 * 服务端默认配置
 * @author cdl
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportServer = HttpTransportServer.class;

    private Class<? extends Encoder> encoder = JSONEncoder.class;

    private Class<? extends Decoder> decoder = JSONDecoder.class;

    private int port = 3000;
}

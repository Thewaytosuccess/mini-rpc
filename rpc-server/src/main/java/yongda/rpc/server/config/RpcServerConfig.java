package yongda.rpc.server.config;

import lombok.Data;
import yongda.rpc.codec.decoder.Decoder;
import yongda.rpc.codec.encoder.Encoder;
import yongda.rpc.codec.decoder.impl.JSONDecoder;
import yongda.rpc.codec.encoder.impl.JSONEncoder;
import yongda.rpc.transport.server.TransportServer;
import yongda.rpc.transport.server.impl.HttpTransportServer;
import yongda.rpc.transport.server.impl.NettyTransportServer;

/**
 * 服务端默认配置
 * @author cdl
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportServer;

    private Class<? extends Encoder> encoder = JSONEncoder.class;

    private Class<? extends Decoder> decoder = JSONDecoder.class;

    private int port = 3000;
}

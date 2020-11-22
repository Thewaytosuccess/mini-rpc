package com.rpc.mini.server.config;

import com.rpc.mini.codec.Decoder;
import com.rpc.mini.codec.Encoder;
import com.rpc.mini.codec.impl.json.JsonDecoder;
import com.rpc.mini.codec.impl.json.JsonEncoder;
import com.rpc.mini.transport.server.TransportServer;
import com.rpc.mini.transport.server.impl.HttpTransportServer;
import lombok.Data;

/**
 * @author xhzy
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportServer = HttpTransportServer.class;

    private Class<? extends Encoder> encoder = JsonEncoder.class;

    private Class<? extends Decoder> decoder = JsonDecoder.class;

    private int port = 3000;
}

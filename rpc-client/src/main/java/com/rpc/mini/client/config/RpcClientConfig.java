package com.rpc.mini.client.config;

import com.rpc.mini.Peer;
import com.rpc.mini.client.selector.TransportClientSelector;
import com.rpc.mini.client.selector.impl.RandomTransportSelector;
import com.rpc.mini.codec.Decoder;
import com.rpc.mini.codec.Encoder;
import com.rpc.mini.codec.impl.json.JsonDecoder;
import com.rpc.mini.codec.impl.json.JsonEncoder;
import com.rpc.mini.transport.client.TransportClient;
import com.rpc.mini.transport.client.impl.HttpTransportClient;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author xhzy
 */
@Data
public class RpcClientConfig {

    private Class<? extends TransportClient> client = HttpTransportClient.class;

    private Class<? extends Encoder> encoder = JsonEncoder.class;

    private Class<? extends Decoder> decoder = JsonDecoder.class;

    private Class<? extends TransportClientSelector> selector = RandomTransportSelector.class;

    private int connectedCount = 1;

    private List<Peer> servers = Collections.singletonList(new Peer("localhost",3000));
}

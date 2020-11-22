package com.rpc.mini.client.selector.impl;

import com.rpc.mini.Peer;
import com.rpc.mini.client.selector.TransportClientSelector;
import com.rpc.mini.common.util.ReflectionUtil;
import com.rpc.mini.transport.client.TransportClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author xhzy
 */
public class RandomTransportSelector implements TransportClientSelector {

    private final List<TransportClient> connectedClients;

    public RandomTransportSelector(){
        connectedClients = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void init(List<Peer> servers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count,1);
        for(Peer peer:servers){
            for(int i=0; i<count; ++i){
                TransportClient client = ReflectionUtil.newInstance(clazz);
                client.connect(peer);
                connectedClients.add(client);
            }
        }
    }

    @Override
    public TransportClient select() {
        return connectedClients.remove(new Random().nextInt(connectedClients.size()));
    }

    @Override
    public void release(TransportClient client) {
        connectedClients.add(client);
    }

    @Override
    public void close() {
        connectedClients.forEach(TransportClient::close);
        connectedClients.clear();
    }
}

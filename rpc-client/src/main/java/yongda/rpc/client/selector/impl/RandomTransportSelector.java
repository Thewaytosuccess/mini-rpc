package yongda.rpc.client.selector.impl;

import yongda.rpc.client.selector.TransportSelector;
import yongda.rpc.common.ReflectionUtils;
import yongda.rpc.proto.Peer;
import yongda.rpc.transport.client.TransportClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomTransportSelector implements TransportSelector {

    private List<TransportClient> clients;

    public RandomTransportSelector(){
        clients = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> transportClient) {
        //至少与服务端建立一个连接
        count = Math.max(count,1);

        //客户端初始化，并与服务端建立连接
        for(Peer peer:peers){
            for(int i=0; i < count; i++){
                TransportClient client = ReflectionUtils.newInstance(transportClient);
                client.connect(peer);
                clients.add(client);
            }
        }
    }

    /**
     * 随机选择一个服务端建立连接
     */
    @Override
    public TransportClient select() {
        return clients.size() == 1 ? clients.remove(0) :
                clients.remove(new Random().nextInt(clients.size()));
    }

    @Override
    public void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public void close() {
       for (TransportClient client:clients){
           client.close();
       }
       clients.clear();
    }
}

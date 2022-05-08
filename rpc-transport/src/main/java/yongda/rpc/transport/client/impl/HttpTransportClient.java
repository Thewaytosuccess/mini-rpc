package yongda.rpc.transport.client.impl;

import org.apache.commons.io.IOUtils;
import yongda.rpc.proto.Peer;
import yongda.rpc.transport.client.TransportClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author cdl
 */
public class HttpTransportClient implements TransportClient {

    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream sendRequest(InputStream is) {
        try {
            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json;charset=utf8");
            conn.connect();

            IOUtils.copy(is,conn.getOutputStream());

            return conn.getResponseCode() == HttpURLConnection.HTTP_OK ?
                   conn.getInputStream() : conn.getErrorStream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }

}

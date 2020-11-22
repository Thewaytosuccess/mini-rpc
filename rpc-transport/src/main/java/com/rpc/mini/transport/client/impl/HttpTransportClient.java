package com.rpc.mini.transport.client.impl;

import com.rpc.mini.Peer;
import com.rpc.mini.transport.client.TransportClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xhzy
 */
public class HttpTransportClient implements TransportClient {

    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://"+peer.getHost()+":"+peer.getPort();
    }

    @Override
    public InputStream write(InputStream inputStream) {
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();
            //将输入流拷贝到输出流中
            IOUtils.copy(inputStream,connection.getOutputStream());

            return connection.getResponseCode() == HttpURLConnection.HTTP_OK ? connection.getInputStream() :
                   connection.getErrorStream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}

package com.antler.mft.server.rpc;

import org.springframework.stereotype.Service;

@Service
public class Configuration {
    private String serverAddress = "127.0.0.1:18866";


    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
}

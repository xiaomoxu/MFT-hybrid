package com.antler.mft.server.rpc;

import com.antler.mft.logging.LogFactory;

public class ServerBootstrap {
    public static void main(String[] args) {
        LogFactory.useLog4JLogging();
        RpcServerFactory rpcServerFactory = new RpcServerFactoryBuilder().build(null);
        RpcServer rpcServer = rpcServerFactory.createRpcServer();
        try {
            rpcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

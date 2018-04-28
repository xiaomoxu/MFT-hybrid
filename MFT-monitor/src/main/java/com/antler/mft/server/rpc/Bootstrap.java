package com.antler.mft.server.rpc;

public class Bootstrap {
    public static void main(String[] args) {
        RpcServerFactory rpcServerFactory = new RpcServerFactoryBuilder().build(null);
        RpcServer rpcServer = rpcServerFactory.createRpcServer();
        try {
            rpcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

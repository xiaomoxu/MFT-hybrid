package com.antler.mft.server.rpc;

import org.springframework.stereotype.Service;

public class DefaultRpcServerFactory implements RpcServerFactory {

    private Configuration configuration;
    private RpcRequestHandler rpcRequestHandler;

    public DefaultRpcServerFactory(){

    }

    public DefaultRpcServerFactory(Configuration configuration , IServiceManage iServiceManage) {
        this.configuration = configuration;
        rpcRequestHandler = new DefaultRpcRequestHandler(iServiceManage);
    }

    @Override
    public RpcServer createRpcServer() {
        RpcServer rpcServer = new RpcServer(this.configuration.getServerAddress(), rpcRequestHandler);
        return rpcServer;
    }
}

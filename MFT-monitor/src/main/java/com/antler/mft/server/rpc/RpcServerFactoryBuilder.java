package com.antler.mft.server.rpc;

import com.antler.mft.server.stub.impl.HelloServiceImpl;
import com.antler.mft.stub.HelloService;

import java.io.InputStream;

public class RpcServerFactoryBuilder {

    public RpcServerFactory build(InputStream inputStream) {
        //inputstream -- configuration
        HelloService helloService = new HelloServiceImpl();
        IServiceManage iServiceManage = new ServiceManage();
        iServiceManage.registService(HelloService.class.getName(), helloService);
        Configuration configuration = new Configuration();
        return new DefaultRpcServerFactory(configuration, iServiceManage);
    }
}

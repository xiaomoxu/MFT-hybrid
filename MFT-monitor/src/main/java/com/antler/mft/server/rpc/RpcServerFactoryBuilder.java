package com.antler.mft.server.rpc;

import com.antler.mft.logging.Log;
import com.antler.mft.logging.LogFactory;
import com.antler.mft.server.rpc.stub.impl.HelloServiceImpl;
import com.antler.mft.stub.HelloService;

import java.io.InputStream;

public class RpcServerFactoryBuilder {
    private Log log = LogFactory.getLog(RpcServerFactoryBuilder.class);

    public RpcServerFactory build(InputStream inputStream) {
        //inputstream -- configuration
        HelloService helloService = new HelloServiceImpl();
        IServiceManage iServiceManage = new ServiceManage();
        iServiceManage.registService(HelloService.class.getName(), helloService);
        Configuration configuration = new Configuration();
        return new DefaultRpcServerFactory(configuration, iServiceManage);
    }
}

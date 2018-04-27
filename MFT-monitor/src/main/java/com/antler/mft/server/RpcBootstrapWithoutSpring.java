package com.antler.mft.server;

import com.antler.mft.server.rpc.RpcServer;
import com.antler.mft.server.stub.impl.HelloServiceImpl;
import com.antler.mft.stub.HelloService;


public class RpcBootstrapWithoutSpring {
//    private static final Logger logger = LoggerFactory.getLogger(RpcBootstrapWithoutSpring.class);

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1:18866";
//        ServiceRegistry serviceRegistry = new ServiceRegistry("127.0.0.1:2181");
        RpcServer rpcServer = new RpcServer(serverAddress);
        HelloService helloService = new HelloServiceImpl();
        rpcServer.addService(HelloService.class.getName(), helloService);
        try {
            rpcServer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
//            logger.error("Exception: {}", ex);
        }
    }
}

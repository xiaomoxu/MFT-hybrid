package mft.rpc;

import com.antler.mft.stub.HelloService;
import mft.server.rpc.ConnectManage;

import java.util.ArrayList;
import java.util.List;

public class ClientBootstrap {
    public static void main(String[] args) {
        List<String> address = new ArrayList<String>();
        address.add("127.0.0.1:18866");
        ConnectManage.getInstance().updateConnectedServer(address);
        DefaultRpcClientFactory defaultRpcClientFactory = new DefaultRpcClientFactory();
        RpcClient rpcClient = defaultRpcClientFactory.createDefaultRpcClient();
        HelloService helloService = rpcClient.createRpcStub(HelloService.class);
        String name = helloService.whatYourName("1");
        System.out.println(name);
    }
}

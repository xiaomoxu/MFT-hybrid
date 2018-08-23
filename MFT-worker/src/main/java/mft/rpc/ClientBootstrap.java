package mft.rpc;

import com.antler.mft.stub.HelloService;
import mft.rpc.client.DefaultRpcClientFactory;
import mft.rpc.client.RpcClient;
import mft.rpc.proxy.IRpcProxy;
import mft.rpc.proxy.RpcAsyncCallback;

import java.util.ArrayList;
import java.util.List;

public class ClientBootstrap {
    public static void main(String[] args) {
        List<String> address = new ArrayList<String>();
        address.add("127.0.0.1:18866");
        ConnectManage.getInstance().updateConnectedServer(address);

        //创建同步客户端
        DefaultRpcClientFactory defaultRpcClientFactory = new DefaultRpcClientFactory();
        RpcClient syncClient = defaultRpcClientFactory.createSyncClient();
        HelloService helloService = (HelloService)syncClient.createStub(HelloService.class);
        String name = helloService.hello("okokok!");
        System.out.println(name);

        //创建异步客户端
        RpcClient asyncClient = defaultRpcClientFactory.createAsyncClient();
        IRpcProxy iRpcProxy = (IRpcProxy)asyncClient.createStub(HelloService.class);
        RpcFuture rpcFuture = iRpcProxy.remoteCall("whatYourName", new RpcAsyncCallback() {
            @Override
            public void success(Object result) {
                System.out.println(result);
            }

            @Override
            public void fail(Exception e) {

            }
        }, "1");

    }
}

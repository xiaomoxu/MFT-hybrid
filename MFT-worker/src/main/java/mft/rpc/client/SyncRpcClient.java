package mft.rpc.client;

import mft.rpc.proxy.RpcProxy;
import mft.rpc.proxy.StubProxy;

import java.lang.reflect.Proxy;

public class SyncRpcClient implements RpcClient {

    @Override
    public Object createStub(Class interfaceClass) {
        return Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new StubProxy(interfaceClass)
        );
    }
}

package mft.rpc;

import mft.rpc.proxy.IRpcProxy;
import mft.rpc.proxy.RpcProxy;
import mft.rpc.proxy.StubProxy;


import java.lang.reflect.Proxy;

public class DefaultRpcClient implements RpcClient {

    @Override
    public <T> T createRpcStub(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new StubProxy<T>(interfaceClass)
        );
    }

    @Override
    public <T> IRpcProxy createAsyncRpcProxy(Class<T> interfaceClass) {
        return new RpcProxy<>(interfaceClass);
    }
}

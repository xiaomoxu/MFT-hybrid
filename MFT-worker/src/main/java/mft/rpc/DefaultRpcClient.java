package mft.rpc;

import com.antler.mft.stub.IStub;


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
}

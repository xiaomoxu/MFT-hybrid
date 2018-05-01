package mft.rpc.proxy;

import mft.rpc.RpcFuture;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StubProxy<T> implements InvocationHandler {
    private Class<T> clazz;

    public StubProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
        IRpcProxy rpcProxy = new RpcProxy(clazz);
        RpcFuture rpcFuture = rpcProxy.remoteCall(method.getName(), null, args);
        return rpcFuture.get();
    }


}

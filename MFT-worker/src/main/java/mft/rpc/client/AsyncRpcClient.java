package mft.rpc.client;

import mft.rpc.proxy.RpcProxy;

public class AsyncRpcClient implements RpcClient {

    @Override
    public Object createStub(Class interfaceClass) {
        return new RpcProxy<>(interfaceClass);
    }
}

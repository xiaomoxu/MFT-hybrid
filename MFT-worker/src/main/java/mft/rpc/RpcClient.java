package mft.rpc;

import com.antler.mft.stub.IStub;

public interface RpcClient {
    public <T> T createRpcStub(Class<T> interfaceClass);
}
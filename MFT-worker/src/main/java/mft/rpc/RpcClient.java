package mft.rpc;

import com.antler.mft.stub.IStub;
import mft.rpc.proxy.IRpcProxy;

public interface RpcClient {
    public <T> T createRpcStub(Class<T> interfaceClass);

    public <T> IRpcProxy createAsyncRpcProxy(Class<T> interfaceClass);
}

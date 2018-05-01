package mft.rpc.proxy;

import mft.rpc.RpcFuture;

public interface IRpcProxy {
    public RpcFuture remoteCall(String methodName, RpcAsyncCallback callback, Object... args);
}

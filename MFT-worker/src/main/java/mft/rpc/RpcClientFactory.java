package mft.rpc;

public interface RpcClientFactory {
    public RpcClient createDefaultRpcClient();

    public RpcClient createAsyncRpcClient();
}

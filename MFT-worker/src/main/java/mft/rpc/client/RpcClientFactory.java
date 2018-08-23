package mft.rpc.client;

public interface RpcClientFactory {
    public RpcClient createAsyncClient();

    public RpcClient createSyncClient();
}

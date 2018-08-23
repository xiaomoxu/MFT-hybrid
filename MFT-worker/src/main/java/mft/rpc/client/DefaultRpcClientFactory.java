package mft.rpc.client;


public class DefaultRpcClientFactory implements RpcClientFactory {

    @Override
    public RpcClient createAsyncClient() {
        return new AsyncRpcClient();
    }

    @Override
    public RpcClient createSyncClient() {
        return new SyncRpcClient();
    }
}

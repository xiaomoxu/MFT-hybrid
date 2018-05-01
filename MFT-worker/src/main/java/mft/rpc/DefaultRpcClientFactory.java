package mft.rpc;

public class DefaultRpcClientFactory implements RpcClientFactory{
    @Override
    public RpcClient createDefaultRpcClient() {
        return new DefaultRpcClient();
    }

}

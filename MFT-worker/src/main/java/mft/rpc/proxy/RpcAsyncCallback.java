package mft.rpc.proxy;

/**
 * Created by luxiaoxun on 2016-03-17.
 */
public interface RpcAsyncCallback {

    void success(Object result);

    void fail(Exception e);

}

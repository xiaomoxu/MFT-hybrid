package mft.server.rpc;

import com.antler.mft.stub.HelloService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ServerTest {
    private RpcClient rpcClient = new RpcClient("");

    static{
        List<String> address = new ArrayList<String>();
        address.add("127.0.0.1:18866");
        ConnectManage.getInstance().updateConnectedServer(address);
    }
    public void helloTest1() throws InterruptedException, ExecutionException, TimeoutException {
        IAsyncObjectProxy client = rpcClient.createAsync(HelloService.class);
        RPCFuture helloFuture = client.call("hello", "xuxiaomo");
        String result = (String) helloFuture.get();
        System.out.println(result);
    }

    public void helloTest2() throws InterruptedException, ExecutionException, TimeoutException {
        HelloService helloService = rpcClient.create(HelloService.class);
        String result = helloService.hello("World");
        System.out.println(result);
////        Assert.assertEquals("Hello! World", result);

    }

    public static void main(String[] args) {
        try {
            new ServerTest().helloTest1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

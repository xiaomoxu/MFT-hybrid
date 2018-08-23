package com.antler.mft.server.rpc;

import com.antler.mft.logging.Log;
import com.antler.mft.logging.LogFactory;
import com.antler.mft.protocol.rpc.RpcRequest;
import com.antler.mft.protocol.rpc.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static ThreadPoolExecutor threadPoolExecutor;

    private Log log = LogFactory.getLog(RpcRequestHandler.class);

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final RpcRequest request) throws Exception {
        this.submit(new Runnable() {
            @Override
            public void run() {
                log.debug("Receive request " + request.getRequestId());
                RpcResponse response = new RpcResponse();
                response.setRequestId(request.getRequestId());
                try {
                    Object result = handleRequestFromClient(request);
                    response.setResult(result);
                } catch (Throwable t) {
                    response.setError(t.toString());
                    log.error("RPC Server handle request error", t);
                }
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        log.debug("Send response for request " + request.getRequestId());
                    }
                });
            }
        });
    }

    public abstract Object handleRequestFromClient(RpcRequest request) throws Throwable;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("server caught exception", cause);
        ctx.close();
    }

    /**
     * 这样写不好，容易发生线程问题
     *
     * @param task
     */
    private void submit(Runnable task) {
        if (threadPoolExecutor == null) {
            synchronized (DefaultRpcRequestHandler.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
                            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }

}

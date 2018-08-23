package com.antler.mft.server.rpc;

import com.antler.mft.protocol.rpc.RpcDecoder;
import com.antler.mft.protocol.rpc.RpcEncoder;
import com.antler.mft.protocol.rpc.RpcRequest;
import com.antler.mft.protocol.rpc.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpcServer implements InitializingBean {
    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private String serverAddress;
    @Autowired
    private RpcRequestHandler rpcRequestHandler;

    public RpcServer() {

    }

    public RpcServer(String serverAddress, RpcRequestHandler rpcRequestHandler) {
        this.serverAddress = serverAddress;
        this.rpcRequestHandler = rpcRequestHandler;
    }

    public void start() throws Exception {
        if (bossGroup == null && workerGroup == null) {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                                    .addLast(new RpcDecoder(RpcRequest.class))
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    .addLast(rpcRequestHandler);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String[] array = serverAddress.split(":");
            String host = array[0];
            int port = Integer.parseInt(array[1]);

            ChannelFuture future = bootstrap.bind(host, port).sync();

//            if (serviceRegistry != null) {
//                serviceRegistry.register(serverAddress);
//            }

            future.channel().closeFuture().sync();
        }
    }

    public void stop() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }
}

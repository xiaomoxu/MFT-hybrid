package mft.server.handler;

import com.antler.mft.protocol.SerializationUtil;
import com.antler.mft.protocol.mft.Handshake;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.UUID;

public class ConnectionHandler {

    public void sayHiToClient(SocketChannel socketChannel) throws IOException {
        Handshake handshake = new Handshake();
        String[] array = (socketChannel.getRemoteAddress() + "").split(":");
        String host = array[0];
        int port = Integer.parseInt(array[1]);
        handshake.setClientIp(host);
        handshake.setClientPort(port);
        handshake.setSessionId(UUID.randomUUID().toString());
        byte[] handshakeArray = SerializationUtil.serialize(handshake);
        int leng = handshakeArray.length;
        ByteBuffer buffer = ByteBuffer.wrap(handshakeArray);
        socketChannel.write(buffer);
        buffer.clear();
    }
}

package com.antler.mft.server.exceptions;

public class RpcException extends RuntimeException {
    private static final long serialVersionUID = -7537395265357977271L;

    public RpcException() {
        super();
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}

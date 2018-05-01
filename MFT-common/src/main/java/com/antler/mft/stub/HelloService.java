package com.antler.mft.stub;

public interface HelloService extends IStub {
    String hello(String name);
    String whatYourName(String nullable);
}

package com.antler.mft.server.rpc.stub.impl;

import com.antler.mft.server.rpc.RpcService;
import com.antler.mft.stub.HelloService;


@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    public HelloServiceImpl() {

    }

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }

    @Override
    public String whatYourName(String nullable) {
        return "Xu Xiao Mo";
    }


}

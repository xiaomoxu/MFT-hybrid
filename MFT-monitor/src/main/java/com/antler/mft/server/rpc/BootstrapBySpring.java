package com.antler.mft.server.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BootstrapBySpring {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("server-spring.xml");
    }
}

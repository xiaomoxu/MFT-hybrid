package com.antler.mft.server.rpc;

import com.antler.mft.stub.IStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

interface IServiceManage {
    public void registService(String interfaceName, IStub serviceImpl);

    public IStub getServiceImplByInterfaceName(String interfaceName);
}

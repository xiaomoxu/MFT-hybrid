package com.antler.mft.server.rpc;

import com.antler.mft.logging.Log;
import com.antler.mft.logging.LogFactory;
import com.antler.mft.server.exceptions.ExceptionFactory;
import com.antler.mft.stub.IStub;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("ServiceManage")//use this bean , please with @Qualifier("ServiceManage")
public class ServiceManage implements IServiceManage, ApplicationContextAware {

    private Log log = LogFactory.getLog(ServiceManage.class);

    private Map<String, IStub> serviceMap = new HashMap<>();


    @Override
    public void registService(String interfaceName, IStub serviceImpl) {
        if (!serviceMap.containsKey(interfaceName)) {
            serviceMap.put(interfaceName, serviceImpl);
            log.debug("Regist  service ... interface name  is [" + interfaceName + "] , Bean name is [" + serviceImpl.getClass().getName() + "]");
        }
    }

    @Override
    public IStub getServiceImplByInterfaceName(String interfaceName) {
        IStub stub = serviceMap.get(interfaceName);
        if (stub == null) {
            throw ExceptionFactory.wrapException("Not found the stub by name :" + interfaceName, null);
        }
        return stub;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (serviceBeanMap != null && !serviceBeanMap.isEmpty()) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                serviceMap.put(interfaceName, (IStub) serviceBean);
            }
        }
    }

}

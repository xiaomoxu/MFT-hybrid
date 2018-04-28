package com.antler.mft.server.rpc;

import com.antler.mft.protocol.RpcRequest;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DefaultRpcRequestHandler extends RpcRequestHandler {

    @Autowired
    @Qualifier("ServiceManage")
    private IServiceManage iServiceManage;


    public DefaultRpcRequestHandler() {

    }

    public DefaultRpcRequestHandler(IServiceManage iServiceManage) {
        this.iServiceManage = iServiceManage;
    }

    @Override
    public Object handleRequestFromClient(RpcRequest request) throws Throwable {
        String className = request.getClassName();
//        Object serviceBean = handlerMap.get(className);
        Object serviceBean = this.iServiceManage.getServiceImplByInterfaceName(className);

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

//        logger.debug(serviceClass.getName());
//        logger.debug(methodName);
        for (int i = 0; i < parameterTypes.length; ++i) {
//            logger.debug(parameterTypes[i].getName());
        }
        for (int i = 0; i < parameters.length; ++i) {
//            logger.debug(parameters[i].toString());
        }

        // JDK reflect
        /*Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);*/

        // Cglib reflect
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }


}

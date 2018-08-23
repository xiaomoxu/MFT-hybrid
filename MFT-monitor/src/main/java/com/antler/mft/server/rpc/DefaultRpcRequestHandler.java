package com.antler.mft.server.rpc;

import com.antler.mft.logging.Log;
import com.antler.mft.logging.LogFactory;
import com.antler.mft.protocol.rpc.RpcRequest;
import io.netty.channel.ChannelHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@ChannelHandler.Sharable
public class DefaultRpcRequestHandler extends RpcRequestHandler {

    private Log log = LogFactory.getLog(DefaultRpcRequestHandler.class);

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
        String interfaceName = request.getClassName();
        Object serviceBean = this.iServiceManage.getServiceImplByInterfaceName(interfaceName);
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        log.debug("Client request calling interface name is [" + interfaceName + "] , Bean name is [" + serviceClass.getName() + "] , invoke method name is [" + methodName + "]");
        for (int i = 0; i < parameterTypes.length; ++i) {
            log.debug("Parameter type [" + i + "] : " + parameterTypes[i].getName());
        }
        for (int i = 0; i < parameters.length; ++i) {
            log.debug("Parameter value [" + i + "] : " + parameters[i].toString());
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

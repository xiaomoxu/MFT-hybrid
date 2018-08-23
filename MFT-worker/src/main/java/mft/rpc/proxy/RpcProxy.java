package mft.rpc.proxy;

import com.antler.mft.protocol.rpc.RpcRequest;
import mft.rpc.RpcFuture;
import mft.rpc.ConnectManage;
import mft.rpc.RpcClientHandler;

import java.util.UUID;

public class RpcProxy<T> implements IRpcProxy {

    private Class<T> clazz;

    public RpcProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public RpcFuture remoteCall(String methodName, RpcAsyncCallback callback, Object[] args) {
        RpcClientHandler handler = ConnectManage.getInstance().chooseHandler();
        RpcRequest request = createRequest(clazz.getName(), methodName, args);
        RpcFuture rpcFuture = handler.sendRequest(request, callback);
        return rpcFuture;
    }

    private RpcRequest createRequest(String className, String methodName, Object[] args) {
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameters(args);
        if (args != null && args.length > 0) {
            Class[] parameterTypes = new Class[args.length];
            // Get the right class type
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = getClassType(args[i]);
            }
            request.setParameterTypes(parameterTypes);
        }

//        Method[] methods = clazz.getDeclaredMethods();
//        for (int i = 0; i < methods.length; ++i) {
//            // Bug: if there are 2 methods have the same name
//            if (methods[i].getName().equals(methodName)) {
//                parameterTypes = methods[i].getParameterTypes();
//                request.setParameterTypes(parameterTypes); // get parameter types
//                break;
//            }
//        }

//        LOGGER.debug(className);
//        LOGGER.debug(methodName);
//        for (int i = 0; i < parameterTypes.length; ++i) {
////            LOGGER.debug(parameterTypes[i].getName());
//        }
//        for (int i = 0; i < args.length; ++i) {
////            LOGGER.debug(args[i].toString());
//        }

        return request;
    }


    private Class<?> getClassType(Object obj) {
        Class<?> classType = obj.getClass();
        String typeName = classType.getName();
        switch (typeName) {
            case "java.lang.Integer":
                return Integer.TYPE;
            case "java.lang.Long":
                return Long.TYPE;
            case "java.lang.Float":
                return Float.TYPE;
            case "java.lang.Double":
                return Double.TYPE;
            case "java.lang.Character":
                return Character.TYPE;
            case "java.lang.Boolean":
                return Boolean.TYPE;
            case "java.lang.Short":
                return Short.TYPE;
            case "java.lang.Byte":
                return Byte.TYPE;
        }

        return classType;
    }
}
package cn.cube.base.web;

import cn.cube.base.web.protocol.request.ProtocolRequest;

/**
 * Created by Administrator on 2017/3/22.
 */
public class ServerContext {

    private static ThreadLocal<ProtocolRequest> threadLocal = new ThreadLocal<ProtocolRequest>();

    public static ProtocolRequest getProtocolRequest() {
        return threadLocal.get();
    }

    public static void setProtocolRequest(ProtocolRequest protocolRequest) {
        threadLocal.set(protocolRequest);
    }

    public static void removeProtocolRequest() {
        threadLocal.remove();
    }

    public static long getUid() {
        return ServerContext.getProtocolRequest().getUid();
    }

}

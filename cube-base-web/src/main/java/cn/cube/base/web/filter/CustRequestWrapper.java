package cn.cube.base.web.filter;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * Description:CustRequestWrapper
 * Author:zhanglida
 * Date:2020/3/12
 * Email:406504302@qq.com
 */
public class CustRequestWrapper extends HttpServletRequestWrapper {
    private Map<String, String> paramMap = Maps.newHashMap();

    public CustRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public CustRequestWrapper(HttpServletRequest request, Map<String, String> paramMap) {
        super(request);
        this.paramMap = paramMap;
    }

//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        StringBuilder sb = new StringBuilder();
//        paramMap.keySet().forEach(key -> {
//            sb.append(key).append("=").append(paramMap.get(key)).append("&");
//        });
//        sb.deleteCharAt(sb.lastIndexOf("&"));
//        byte[] bytes = sb.toString().getBytes("utf-8");
//        return new DelegatingServletInputStream(new ByteArrayInputStream(bytes));
//    }


    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParamMap().keySet());
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = Maps.newHashMap();
        Set<String> keySet = getParamMap().keySet();
        keySet.forEach(key -> {
            map.put(key, new String[]{paramMap.get(key)});
        });
        return map;
    }

    public Map<String, String> getParamMap() {
        if (null == paramMap){
            return Maps.newHashMap();
        }
        return paramMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        String parameter = getParameter(name);
        return new String[]{parameter};
    }

    //覆盖getParameter
    @Override
    public String getParameter(String name) {
        return paramMap.get(name);
    }
}

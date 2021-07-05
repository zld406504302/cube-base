package cn.cube.base.web.filter;

import cn.cube.base.web.util.IpUtils;
import cn.cube.base.core.util.LoggerUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Description:WebLoggerFilter
 * Author:zhanglida
 * Date:2017/12/25
 * Email:406504302@qq.com
 */
public class WebLoggerFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerUtils.getLogger(WebLoggerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomizeResponseWrapper responseWrapper = new CustomizeResponseWrapper(response);
        Long start = System.currentTimeMillis();
        try {
            CustRequestWrapper custRequestWrapper = new CustRequestWrapper(request);
            filterChain.doFilter(custRequestWrapper, responseWrapper);
            response.getOutputStream().write(responseWrapper.getByteArrayOutputStream().toByteArray());
        }finally {
            Long end = System.currentTimeMillis();
            logger(request,responseWrapper,end-start);
        }
    }


    private void logger(HttpServletRequest request, CustomizeResponseWrapper responseWrapper,Long cost){
        logger.info("[uri:{}] [method:{}] [status:{}] [ip:{}] [cost:{}] [request:{}] [response:{}]",request.getRequestURI(), request.getMethod(),responseWrapper.getStatus(), IpUtils.getIpAddress(request),String.valueOf(cost)+"ms",requestParam(request),responseParam(responseWrapper));
    }

    private String requestParam(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            obj.put(name, request.getParameter(name));
        }
        return obj.toJSONString();
    }

    private String responseParam(CustomizeResponseWrapper response) {
        return response.getTextContent();
    }


    private class CustomizeResponseWrapper extends HttpServletResponseWrapper {
        private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private PrintWriter printWriter = new PrintWriter(outputStream);


        public CustomizeResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return printWriter;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener listener) {

                }

                @Override
                public void write(int b) throws IOException {
                    outputStream.write(b);
                }
            };
        }

        public void flush() {
            try {
                printWriter.flush();
                printWriter.close();
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            flush();
            return outputStream;
        }


        public String getTextContent() {
            flush();
            return outputStream.toString();
        }
    }
}

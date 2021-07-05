package cn.cube.base.web.filter;

import cn.cube.base.core.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description:CustResponseWrapper
 * Author:zhanglida
 * Date:2020/3/12
 * Email:406504302@qq.com
 */
public class CustResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintWriter printWriter = new PrintWriter(outputStream);


    public CustResponseWrapper(HttpServletResponse response) {
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
        return outputStream;
    }

    public String getTextContent() {
        flush();
        String file = getHeader("Content-Disposition");
        if (!StringUtils.isEmpty(file) && file.indexOf("attachment") > -1) {
            return file;
        }
        return outputStream.toString();
    }

    public byte[] getContent() {
        flush();
        return outputStream.toByteArray();
    }
}

package cn.cube.base.third.wx;

import cn.cube.base.core.util.LoggerUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * 公众平台支付client
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:36
 */
public class WxMpPayClient extends WxOpenPayClient {
    private static Logger log = LoggerUtils.getLogger(WxMpPayClient.class);

    public WxMpPayClient() {
    }

    @Override
    public void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException, CertificateException {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        URL url = Thread.currentThread().getContextClassLoader().getResource(WxMpPayConfigure.getCertLocalPath());
        File file = new File(url.toURI());
        InputStream instream = new FileInputStream(file);//加载本地的证书进行https加密传输
        try {

            keyStore.load(instream, WxMpPayConfigure.getCertPassword().toCharArray());//设置证书密码
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WxMpPayConfigure.getCertPassword().toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        super.setHttpClient(httpClient);

    }

}

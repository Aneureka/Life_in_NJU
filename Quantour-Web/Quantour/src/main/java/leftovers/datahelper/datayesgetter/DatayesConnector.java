package leftovers.datahelper.datayesgetter;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.security.util.HostnameChecker;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by Hiki on 2017/6/2.
 */
public class DatayesConnector {

    // token
    private final String ACCESS_TOKEN = "e92fdfeb0fe67bc86deb7dd62dc3261f000328cf2b83b6354ad2623f851df04a";
    // 创建HttpClient
    private CloseableHttpClient httpClient;

    // 单例
    private static DatayesConnector instance;

    private DatayesConnector() {
        this.httpClient = createHttpsClient();
    }

    public static DatayesConnector getInstance(){
        if (instance == null)
            instance = new DatayesConnector();
        return instance;
    }


    // 创建http client
    private CloseableHttpClient createHttpsClient(){
        // 创建一个X509证书验证管理器的匿名类，由于重载了相关验证方法，不做处理而达到跳过验证的效果
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        // 因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509mgr}, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    // 获取Json数据
    public String getContent(String url){
        // 创建get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);

        // 获取response
        String content = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

}

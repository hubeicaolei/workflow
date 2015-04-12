package cn.liley.hummer.utils.webservice;

import cn.liley.hummer.utils.logging.LogManager;
import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by caolei on 2015/4/7.
 */
public class WebServiceManager {

    private final String CLASS_NAME = "WebServiceManager";
    private Logger logger = LogManager.getLogger(this.getClass().getName());
    private String host = "localhost";
    private int port = 3411;

    private String context = "/rest-api/";
    private String version = "v1";

    private final static WebServiceManager instance = new WebServiceManager();

    private WebServiceManager(){
        String METHOD = "WebServiceManager";
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(System.getProperty("webservice.configurationFile","conf/webservice.properties")));
            Properties props = new Properties();
            props.load(inputStream);
            host = props.getProperty("host","localhost");
            port = Integer.parseInt(props.getProperty("port","3411"));
            context = props.getProperty("context","/rest-api/");
            version = props.getProperty("version","v1");

        } catch (SecurityException e) {
            logger.logp(Level.SEVERE,CLASS_NAME,METHOD,"an security exception", e);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != inputStream)
            {
                try{
                    inputStream.close();
                }catch(Exception e){}
            }
        }
    }

    /**
     * get resources
     * @param url
     * @return
     */
    public  String httpGet(String url) throws IOException {

        String METHOD = "httpGet";
        logger.entering(CLASS_NAME, METHOD, url);

        String httpRequest = "http://" + host + ":" + port + context + version + url;
        HttpGet hg = new HttpGet(httpRequest);
        CloseableHttpClient hc = HttpClients.createDefault();
        ResponseHandler<String> rh = new ResponseHandler<String>() {

            @Override
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        String result = hc.execute(hg,rh);
        logger.exiting(CLASS_NAME,METHOD,result);
        return result;
    }


    /**
     * create resources
     * @param url
     * @param jsonPost
     * @return
     */
    public static String httpPost(String url, String jsonPost){
        return null;
    }

    /**
     * update resources
     * @param url
     * @param jsonPut
     * @return
     */
    public static String httpPut(String url, String jsonPut){
        return null;
    }

    /**
     * delete resources
     * @param url
     * @param jsonDelete
     * @return
     */
    public static String httpDelete(String url, String jsonDelete){
        return null;
    }

}

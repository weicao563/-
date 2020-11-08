package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import java.io.IOException;
@Test

public class LoginTwo {

    public void loginone() {
        LoginTwo.loginon();
    }

    public static String loginon() {
        String token = null;

        CloseableHttpClient cn = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://boss-api-pre.ai-ways.com/upm/upmLoginCallback?code=" + LoginOne.loginone() + "&account=fanqunfei");
        get.addHeader("Content-Type", "application/json");
        try {
            CloseableHttpResponse res = cn.execute(get);
            int zx = (res.getStatusLine().getStatusCode());
            if (zx != 200) {
                assert false;
            }
            String entity = EntityUtils.toString(res.getEntity());
            System.err.println(entity);
            JSONObject obj = JSON.parseObject(entity);

            token = TestUtil.getValueByJPath(obj, "data/token");
            System.out.println(token);

        } catch (IOException e) {
            e.printStackTrace();
        }
    return token;
    }

    }

package Tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class Testaa {
    @Test
    public void aa() {
        CloseableHttpClient c = HttpClients.createDefault();
        HttpGet n = new HttpGet("https://reqres.in/api/unknown");
        n.addHeader("Content-Type", "application/json");

        CloseableHttpResponse res;

        {
            try {
                res = c.execute(n);
                int zx = (res.getStatusLine().getStatusCode());
                if (zx != 200) {
                    assert false;
                }
                String entity = EntityUtils.toString(res.getEntity());
                System.err.println(entity);
                JSONObject obj = JSON.parseObject(entity);
                String redirectUrl = TestUtil.getValueByJPath(obj, "data[1]/name");
                System.err.println(redirectUrl);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



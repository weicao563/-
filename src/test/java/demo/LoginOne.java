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
import java.util.Arrays;


@Test
public class LoginOne {

    public void loginone2() {
        LoginOne.loginone();
    }

    public static String loginone() {

        String exp = "";
        CloseableHttpClient cn = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://boss-api-pre.ai-ways.com/upm/oauth/v2/authorize?" +
                "clientId=upm&account=fanqunfei&password=123456");
        get.addHeader("Content-Type", "application/json");
        String code = null;

        try {
            CloseableHttpResponse res = cn.execute(get);
            int zx = (res.getStatusLine().getStatusCode());
            if (zx != 200) {
                assert false;
            }
            String entity = EntityUtils.toString(res.getEntity());
            System.err.println(entity);
            JSONObject obj = JSON.parseObject(entity);
            String redirectUrl = TestUtil.getValueByJPath(obj, "data/redirectUrl");
            //h?efiue
            System.err.println(redirectUrl);
            String[] splitQ = redirectUrl.split("\\?");//
            System.err.println(Arrays.toString(splitQ));
            code = splitQ[1].split("&")[0].split("=")[1];
            System.err.println(code);
             /*if(!s.equals(exp)){
                assert false;
            }LoginTwo.loginon();
*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;

    }
}


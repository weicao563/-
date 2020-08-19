package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class huoqu {
    @Test
    public void huoqu1(){
        String exp = "18629369721";
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet get = new HttpGet("http://10.100.13.4:8001/urm/account/getUserByMobile?mobile=18629369721");
        get.addHeader("Content-Type","application/json");
        get.addHeader("account","admin");
        try {
            CloseableHttpResponse response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            //System.out.println(statusCode);
            if(statusCode != 200){
                assert false;
            }
            String re = EntityUtils.toString (response.getEntity());
            JSONObject obj = JSON.parseObject(re);
            System.err.println(obj);
           String s = TestUtil.getValueByJPath(obj,"data/mobile");
           System.err.println(s);
            if(!s.equals(exp)){
                assert false;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

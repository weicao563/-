package demo;



import com.alibaba.fastjson.JSONObject;

import com.qa.util.TestUtil;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class JdbcTest {


    @Test
    public void Updataindex(){

        String exp = "000fdec9-c04c-4b10-b0eb-44bbf610b074";
        CloseableHttpClient clinet = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://10.100.13.130:8099/index/getAllReportMessage");
        httpPost.addHeader("account","weic");
        httpPost.addHeader("Content-Type","application/json");
        List<BasicNameValuePair> lst = new ArrayList<BasicNameValuePair>();
        lst.add(new BasicNameValuePair("reportName","用户活跃及留存情况分析"));

        try {
            UrlEncodedFormEntity un = new UrlEncodedFormEntity(lst,"UTF-8");
            httpPost.setEntity(un);
            CloseableHttpResponse response = clinet.execute(httpPost);
            boolean entity=(response.getStatusLine().getStatusCode())==200;
            if(!entity){
                assert false;
                }

            String responseString = EntityUtils.toString(response.getEntity());
            //System.err.println(responseString);
            JSONObject responseJson = JSONObject.parseObject(responseString);
            String s = TestUtil.getValueByJPath(responseJson,"data[0]/reportId");
            System.out.println(s);
            if(!s.equals(exp)){
                assert false;
            }
            assert true;



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

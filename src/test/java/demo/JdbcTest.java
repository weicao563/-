package demo;


import Tools.ExcelUtils;
import Tools.JDBCTools;
import Tools.JdbcMySQLTools;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.core.util.TestUtils;
import com.qa.Log4jUtils;
import com.qa.util.TestUtil;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.testng.annotations.DataProvider;


import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcTest {


    @Test
    public void Updataindex(){
        JDBCTools.createConnection();


        //System.err.println(ss);
        String exp = "eca2c813-74c8-41cf-af83-b6ed5aa3b26c";
        //Logger log = Log4jUtils.getLogger(JdbcTest.class);
        //log.info("测试开始");
        CloseableHttpClient clinet = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://10.100.13.130:8099/index/getAllReportMessage");
        httpPost.addHeader("account","weic");
        httpPost.addHeader("Content-Type","application/json");
        List<BasicNameValuePair> lst = new ArrayList<BasicNameValuePair>();
        lst.add(new BasicNameValuePair("reportName","车辆行程报表"));

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
            String s = TestUtil.getValueByJPath(responseJson,"data[0]/reportTypeId");
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

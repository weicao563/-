package demo;


import Tools.ExcelUtils;
import Tools.JDBCTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qa.util.TestUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TopicTest {


    @Test
    public void Topic()  {
        String exp = "06_商城用户行为";
        JDBCTools.createConnection();
        CloseableHttpClient client =  HttpClients.createDefault();
        HttpPost post = new HttpPost("http://10.100.13.130:8099/index/getAllReportMessage");
        List<NameValuePair> lst = new ArrayList<NameValuePair>();
        post.addHeader("account","weic");
        post.addHeader("Content-Type","application/json");
        lst.add(new BasicNameValuePair("topicld","APP主题"));

        try {
            UrlEncodedFormEntity un = new UrlEncodedFormEntity(lst,"UTF-8");
            post.setEntity(un);
            HttpResponse response = client.execute(post);
            boolean entity=(response.getStatusLine().getStatusCode())==200;
            if(!entity){
                assert false;
            }
            String re = EntityUtils.toString (response.getEntity());
            //System.err.println(re);
            JSONObject obj = JSON.parseObject(re);
            String s = TestUtil.getValueByJPath(obj,"data[0]/reportName");
            System.err.println(s);
            if(!s.equals(exp)){
                assert false;
                return ;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

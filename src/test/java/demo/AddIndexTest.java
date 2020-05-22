package demo;

import Tools.ExcelUtils;
import com.alibaba.fastjson.JSONObject;
import com.qa.util.TestUtil;
import org.apache.http.NameValuePair;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class AddIndexTest {
    @DataProvider
    public Object[][] data(){
        Object[][] obj= ExcelUtils.read("/test_data.xlsx",2,4,4,1,8);
        return obj;
    }


    @Test(dataProvider="data")
    public void addIndex() {
        CloseableHttpClient clinet =  HttpClients.createDefault();
        HttpPost post = new HttpPost("http://10.100.13.130:8099/index/saveOrUpdateIndex");
        post.addHeader("account","weic");
        post.addHeader("Content-Type","application/json");
        List<NameValuePair> lst = new ArrayList<NameValuePair>();
        lst.add(new BasicNameValuePair("applicationSystem ","报表系统"));
        lst.add(new BasicNameValuePair("businessCaliber","哈哈"));
        lst.add(new BasicNameValuePair("displayName","哈哈"));
        lst.add(new BasicNameValuePair("indexDetails","indexDetails"));
        lst.add(new BasicNameValuePair("indexDetails[]","哈哈"));
        lst.add(new BasicNameValuePair("indexTopics","APP主题"));
        lst.add(new BasicNameValuePair("reportFirst",""));
        lst.add(new BasicNameValuePair("reportId","哈哈"));
        lst.add(new BasicNameValuePair("reportSecond",""));
        lst.add(new BasicNameValuePair("standardName","哈哈"));
        lst.add(new BasicNameValuePair("indexDetails[]","哈哈"));


        try {
            UrlEncodedFormEntity un = new UrlEncodedFormEntity(lst,"UTF-8");
            post.setEntity(un);
            CloseableHttpResponse response = clinet.execute(post);
            boolean entity=(response.getStatusLine().getStatusCode())==200;
            System.err.println(entity);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject responseJson = JSONObject.parseObject(responseString);
            //String s = TestUtil.getValueByJPath(responseJson,"data[0]/reportTypeId");
           // System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

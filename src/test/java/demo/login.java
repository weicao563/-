package demo;

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
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;


@Test
public class login {
    //String firstFollowTime= "";
    public void shanchu() {
        CloseableHttpClient cl = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://10.100.13.4:8001/urm/cso/avg/convertRatio");
        List<NameValuePair> lst = new ArrayList<NameValuePair>();
        post.addHeader("Content-Type", "application/json");
        post.addHeader("account", "admin");
        lst.add(new BasicNameValuePair("beginTime","2017/08/17"));
        lst.add(new BasicNameValuePair("endTime","2020/08/15"));
        lst.add(new BasicNameValuePair("partnerCodes","P257111019"));
        try {
            UrlEncodedFormEntity en = new UrlEncodedFormEntity(lst,"UTF-8");
            post.setEntity(en);
            HttpResponse response = cl.execute(post);
            boolean entity=(response.getStatusLine().getStatusCode())==200;
            if(!entity){
                assert false;
            }
            String re = EntityUtils.toString (response.getEntity());
            System.err.println(re);
            JSONObject obj = JSON.parseObject(re);
            //String s = TestUtil.getValueByJPath(obj,"data[0]/firstFollowTime");
           // System.err.println(s);
           /* if(!s.equals(exp)){
                assert false;
                return ;
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}





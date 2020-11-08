package demo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AddUser {

    @Test
    public void adduser(){
        CloseableHttpClient clinet = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://168.63.70.73:18095/paas/api/appspace/app/user/addUser?iv-user=014300");
        post.addHeader("Content-Type","application/json");
        List<NameValuePair> lst = new ArrayList<NameValuePair>();
        lst.add(new BasicNameValuePair("appId","000002"));
        lst.add(new BasicNameValuePair("method","post"));
        lst.add(new BasicNameValuePair("roleId","12"));
        lst.add(new BasicNameValuePair("userId","K19800006"));

        try {
            UrlEncodedFormEntity en = new UrlEncodedFormEntity(lst, "UTF-8");
            post.setEntity(en);
            HttpResponse re = clinet.execute(post);
            int entity = re.getStatusLine().getStatusCode();
            System.err.println(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

package demo;

import Tools.ExcelUtils;
import com.qa.tests.Log4jUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class AddUsers {
    @DataProvider
    public Object[][] data(){
        Object[][] obj= ExcelUtils.read("res/test_data.xlsx",1,2,4,1,21);
        return obj;
    }


@Test(dataProvider="data")
    public void addu(String md, String bh, String address, String bigRegion, String childredState, String id, String idCard, String mobile,
                        String name, String ownCar, String partnerCode, String partnerName, String regProvince,
                        String regStrict, String sex, String userSource, String userSource_2, String userSource_3,
                        String userType, String regCity , String exp){
        CloseableHttpClient cl = HttpClients.createDefault();
        Logger log= Log4jUtils.getLogger(AddUsers.class);
        log.info("测试目的" + md + "测试编号" + bh);
        HttpPost post = new HttpPost("http://10.100.13.4:8001/urm/account/addAccount");
        post.addHeader("Content-Type", "application/json;charset=utf8");
        post.addHeader("admin", "admin");
        List<NameValuePair> lst = new ArrayList<NameValuePair>();
        lst.add(new BasicNameValuePair("address", address));
        lst.add(new BasicNameValuePair("bigRegion", bigRegion));
        lst.add(new BasicNameValuePair("id", id));
        lst.add(new BasicNameValuePair("childredState", childredState));
        lst.add(new BasicNameValuePair("idCard", idCard));
        lst.add(new BasicNameValuePair("mobile", mobile));
        lst.add(new BasicNameValuePair("name", name));
        lst.add(new BasicNameValuePair("ownCar", ownCar));
        lst.add(new BasicNameValuePair("partnerCode", partnerCode));
        lst.add(new BasicNameValuePair("partnerName", partnerName));
        lst.add(new BasicNameValuePair("regCity", regCity));
        lst.add(new BasicNameValuePair("regProvince", regProvince));
        lst.add(new BasicNameValuePair("regStrict", regStrict));
        lst.add(new BasicNameValuePair("sex", sex));
        lst.add(new BasicNameValuePair("userSource", userSource));
        lst.add(new BasicNameValuePair("userSource_2", userSource_2));
        lst.add(new BasicNameValuePair("userSource_3", userSource_3));
        lst.add(new BasicNameValuePair("userType", userType));

        try {
            UrlEncodedFormEntity en = new UrlEncodedFormEntity(lst, "UTF-8");
            post.setEntity(en);
            HttpResponse re = cl.execute(post);
            int entity = re.getStatusLine().getStatusCode();
            if (entity != 200) {
                assert false;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

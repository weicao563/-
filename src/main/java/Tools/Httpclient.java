package Tools;


import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Httpclient {
    private static final Logger LOG = LoggerFactory.getLogger(Httpclient.class);

    private static Gson gson = new Gson();
    private String url = "http://168.63.70.73:18095/paas/api/appspace/app/user/addUser?iv-user=010320";
    private Map<String, String> param = new HashMap<>();

    {
        param.put("appId", "000348");
        param.put("method", "post");
        param.put("roleId", "12");
        param.put("userId", "K1980006");
    }

    @Test
    public void Sdd2() throws IOException {

        HttpMethod method = request(new PostMethod(url), param, true);
        Map<?, ?> map = bodyToMap(method);
        Object code = map.get("code");
        Object msg = map.get("msg");
        System.out.println("code = " + code);
        System.out.println("msg = " + msg);
    }

    @Test
    public void Sdd() {
        CloseableHttpClient vl = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://168.63.70.73:18095/paas/api/appspace/app/user/addUser?iv-user=010320");
        post.addHeader("Content-Type", "application/json;charset=utf-8");
//        List<NameValuePair> lst = new ArrayList<NameValuePair>();
//        lst.add(new BasicNameValuePair("appId","000348"));
//        lst.add(new BasicNameValuePair("method","post"));
//        lst.add(new BasicNameValuePair("roleId","12"));
//        lst.add(new BasicNameValuePair("userId","K1980006"));
        HashMap<String, String> param = new HashMap<>();
        param.put("appId", "000348");
        param.put("method", "post");
        param.put("roleId", "12");
        param.put("userId", "K1980006");
        //            UrlEncodedFormEntity ue =  new UrlEncodedFormEntity(lst,"UTF-8");
        StringEntity stringEntity = new StringEntity(param.toString(), "UTF-8");
        stringEntity.setContentType("application/json;charset=utf-8");
        post.setEntity(stringEntity);
        try {
            HttpResponse re = vl.execute(post);
            int code = re.getStatusLine().getStatusCode();
            System.err.println(code);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * @param method    请求方式 post->PostMethod get->GetMethod...
     * @param param     请求参数map k v
     * @param json      是否为json格式参数
     * @param statusArr 当处于什么状态码时打印响应日志
     * @return HttpMethod
     */
    public static HttpMethod request(HttpMethod method, Map<?, ?> param, boolean json, int... statusArr) {

        HttpClient client = new HttpClient();
        if (!param.isEmpty() && param.size() > 0) {
            if (json) {

                String jsonStr = gson.toJson(param);
                try {
                    ((PostMethod) method).setRequestEntity(new StringRequestEntity(jsonStr, "application/json", "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                for (Map.Entry<?, ?> entry : param.entrySet()) {
                    ((PostMethod) method).addParameter(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
            method.getParams().setContentCharset("utf-8");
        }
        try {
            int status = client.executeMethod(method);
            LOG.info("status: {}", status);
            if (Arrays.stream(statusArr).anyMatch(value -> value == status)) {
                LOG.info("result: \n{}", method.getResponseBodyAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * json body to map
     * @param method
     * @return
     */
    public static Map<?, ?> bodyToMap(HttpMethod method) {

        String bodyAsString = null;
        try {
            bodyAsString = method.getResponseBodyAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(bodyAsString, Map.class);
    }
}
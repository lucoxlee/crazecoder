package top.latfat.crazecoder.httpclint;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import top.latfat.crazecoder.entity.TulingSaid;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TalkingHttpClint {

	private	CloseableHttpClient client = HttpClients.createDefault();
	private ObjectMapper mapper = new ObjectMapper();
	
	public String talkingTuling(String info, String user) {
		HttpPost post = new HttpPost("http://www.tuling123.com/openapi/api");
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("key", "87b840fa3810d8a85deb3ae13cf6d7cd"));
		formparams.add(new BasicNameValuePair("info", info));
		formparams.add(new BasicNameValuePair("userid", user));
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            post.setEntity(uefEntity);  
          //  System.out.println("executing request " + post.getURI());  
            CloseableHttpResponse response = client.execute(post);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                	TulingSaid said = (TulingSaid) mapper.readValue(EntityUtils.toString(entity), TulingSaid.class);
                	if (said.getCode() == 100000) {
						return said.getText();
					}
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        return "啊，我爸爸出去打酱油了，爸爸不让我跟陌生人说话。";
	}
	
	public void destory() {
	            // 关闭连接,释放资源    
	            try {
	            	if (client != null) {
	            		client.close();  
					}
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	}
}

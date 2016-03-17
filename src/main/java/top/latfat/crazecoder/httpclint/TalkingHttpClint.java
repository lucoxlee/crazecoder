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

import top.latfat.crazecoder.entity.tuling.Menu;
import top.latfat.crazecoder.entity.tuling.News;
import top.latfat.crazecoder.entity.tuling.TulingSaid;
import top.latfat.crazecoder.entity.tuling.TulingSaidList;
import top.latfat.crazecoder.entity.tuling.TulingSaidURL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TalkingHttpClint {

	private	CloseableHttpClient client;
	private ObjectMapper mapper;
	
	private String host;
	private final String key;
	private String serverDownSaid;
	
	public TalkingHttpClint(String host, String key, String defaultSay) {
		this.key = key;
		this.host = host;
		this.serverDownSaid = defaultSay;
		client = HttpClients.createDefault();
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	@SuppressWarnings("unchecked")
	public TulingSaid talkingTuling(String info, String user) {
		HttpPost post = new HttpPost(host);
		TulingSaid said = new TulingSaid();
		said.setCode(100000);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("key", key));
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
                String json = EntityUtils.toString(entity);
         //       System.out.println(json);
                if (entity != null) {  
                	said =  mapper.readValue(json, TulingSaid.class);
                	switch(said.getCode()) {
                	case 100000:
						return said;
                	case 200000:
                		return mapper.readValue(json, TulingSaidURL.class);
                	case 302000:
                		return (TulingSaidList<News>) mapper.readValue(json, getCollectionType(TulingSaidList.class, News.class));
                	case 308000:
                		return (TulingSaidList<Menu>) mapper.readValue(json, getCollectionType(TulingSaidList.class, Menu.class));
					}
                }  
            } catch(Exception e){
            	e.printStackTrace();
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {
        	e.printStackTrace();
		}
        said.setText(serverDownSaid);
        return said;
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
    /**   
     * 获取泛型的Collection Type  
     * @param collectionClass 泛型的Collection   
     * @param elementClasses 元素类   
     * @return JavaType Java类型   
     * @since 1.0   
     */   
     public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
         return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
     } 
}

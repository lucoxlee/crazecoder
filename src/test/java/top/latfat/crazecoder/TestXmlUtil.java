package top.latfat.crazecoder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import top.latfat.crazecoder.entity.tuling.TulingSaid;
import top.latfat.crazecoder.entity.wechat.InputMessage;
import top.latfat.crazecoder.entity.wechat.Item;
import top.latfat.crazecoder.entity.wechat.OutputMessage;
import top.latfat.crazecoder.service.impl.SerializeXmlUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;

public class TestXmlUtil {

	@Test
	public void testXml() {
		String xml = "<xml><ToUserName><![CDATA[oIDrpjqASyTPnxRmpS9O_ruZGsfk]]></ToUserName><FromUserName><![CDATA[gh_680bdefc8c5d]]></FromUserName><CreateTime>1359036631</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[【深圳】天气实况 温度：27℃ 湿度：59% 风速：东北风3级11月03日 周日 27℃~23℃ 小雨 东北风4-5级11月04日 周一 26℃~21℃ 阵雨 微风11月05日 周二 27℃~22℃ 阴 微风]]></Content></xml>";
		String response = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content></xml>";
		XStream xs = SerializeXmlUtil.createXstream(); 
		xs.processAnnotations(InputMessage.class);  
		xs.processAnnotations(OutputMessage.class);  
		// 将指定节点下的xml节点数据映射为对象  
//		xs.alias("xml", InputMessage.class); 
//		InputMessage in = (InputMessage) xs.fromXML(xml);
		xs.alias("xml", OutputMessage.class);
		OutputMessage out = (OutputMessage) xs.fromXML(response);
		//System.out.println(in + "\n");
		System.out.println(xs.toXML(out));
		System.out.println(System.currentTimeMillis()/1000);
	}
	
	@Test
	public void testJson() throws Exception {
		String str = "{\"code\":100000,\"text\":\"2016年03月15日 星期二 凌晨 0:23\"}";
		ObjectMapper mapper = new ObjectMapper();
		TulingSaid said = (TulingSaid) mapper.readValue(str, TulingSaid.class);
		System.out.println(said);
	}
	
	@Test
	public void testTuling() throws InterruptedException {
//	//	TalkingHttpClint clint = new TalkingHttpClint(host, key, defaultSay);
//		System.out.println(clint.talkingTuling("明天哪班飞机有票啊", "122"));
//		Thread.sleep(2000);
//		System.out.println(clint.talkingTuling("上海", "122"));
//		Thread.sleep(2000);
//		System.out.println(clint.talkingTuling("深圳", "122"));
	}
	
	@Test
	public void testOutputMessage() {
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(OutputMessage.class);
		xs.alias("xml", OutputMessage.class);
		OutputMessage out = new OutputMessage();
		out.setToUserName("0");
		out.setFromUserName("1");
		out.setArticleCount(2);
		List<Item> list = new ArrayList<Item>();
		Item i1 = new Item();
		Item i2 = new Item();
		i1.setTitle("00000");
		i2.setTitle("22222");
		list.add(i1);
		list.add(i2);
		
		out.setArticles(list);
		System.out.println(xs.toXML(out));
	}
}

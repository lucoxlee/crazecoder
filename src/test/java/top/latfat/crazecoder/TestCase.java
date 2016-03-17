package top.latfat.crazecoder;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.latfat.crazecoder.entity.tuling.Menu;
import top.latfat.crazecoder.entity.tuling.News;
import top.latfat.crazecoder.entity.tuling.TulingSaid;
import top.latfat.crazecoder.entity.tuling.TulingSaidList;
import top.latfat.crazecoder.entity.wechat.WechatMsg;
import top.latfat.crazecoder.httpclint.TalkingHttpClint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:spring.xml", "classpath:spring-hibernate.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCase{

	@Autowired
	private WechatMsg msg;
	@Autowired
	private TalkingHttpClint clint;
	
	@Test
	public void testTuling() throws InterruptedException {
		System.out.println(clint.talkingTuling("明天哪班飞机有票啊", "122"));
		Thread.sleep(2000);
		System.out.println(clint.talkingTuling("上海", "122"));
		Thread.sleep(2000);
		System.out.println(clint.talkingTuling("深圳", "122"));
	}
	
	@Test
	public void testTulingList() throws InterruptedException {
		TulingSaid list =  clint.talkingTuling("鱼香肉丝怎么做？", "11");
		System.out.println(list.toString());
	}
	
}

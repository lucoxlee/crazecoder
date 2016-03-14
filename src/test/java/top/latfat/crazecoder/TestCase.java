package top.latfat.crazecoder;

import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.latfat.crazecoder.entity.WechatMsg;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:spring.xml", "classpath:spring-hibernate.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCase{

	@Autowired
	private WechatMsg msg;
	
	@Test
	public void test() {
		
		String[] arr = new String[]{"abc", "def", "ghk"};
		Arrays.sort(arr);
		System.out.println(arr.toString());
	}
	
}

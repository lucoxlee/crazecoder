package top.latfat.crazecoder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import top.latfat.crazecoder.entity.User;

/**
 * 
 * @author sean
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:spring.xml", "classpath:spring-hibernate.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUser {
	
	@Autowired
	private SessionFactory factory;
	
	private Session session;
	
	private Transaction tx;

	@Before
	public void init(){
		session = factory.openSession();
		tx = session.beginTransaction();
	}
	
	@Test
	public void test5() {
		User user = new User("asdasd", "tututu");
		session.delete(user);
		System.out.println("session delete test: ");
		test2();
	}
	
	@Test
	public void test4() {
		User user = new User("asdasd", "babab");
		session.update(user);
		System.out.println("session update test: ");
		test2();
	}
	
	@Test
	public void test2() {
		User user = (User) session.get(User.class, "asdasd");
		System.out.println("session Find test: " + user);
	}
	
	@Test
	public void test1() {
		User user = new User("asdasd", "tututu");
		System.out.println(user.getId());
		java.io.Serializable id = session.save(user);
		System.out.println("session save test: " + id);
	}
	
	@Test
	public void test0() {
		System.out.println("session class test: " + session);
	}
	
	@After
	public void destory() {
		session.flush();
		tx.commit();
		session.close();
	}
}

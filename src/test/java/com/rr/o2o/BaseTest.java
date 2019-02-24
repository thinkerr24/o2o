package com.rr.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * Configuring integration of spring and junit£¬ 
 * Loading springIOC container when junit starts
 */
@RunWith(SpringJUnit4ClassRunner.class)
// Telling junit the location of spring configuration file
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest {
	
}

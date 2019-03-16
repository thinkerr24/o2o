package com.rr.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rr.o2o.BaseTest;
import com.rr.o2o.dto.ShopExecution;
import com.rr.o2o.entity.Area;
import com.rr.o2o.entity.PersonInfo;
import com.rr.o2o.entity.Shop;
import com.rr.o2o.entity.ShopCategory;
import com.rr.o2o.enums.ShopStateEnum;
import com.rr.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺");
		File shopImg = new File("D:\\projectdev\\image\\autumn.jpg");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.modifyShop(shop, is, "autumn.jpg");
		System.out.println("新的图片地址为:" + shopExecution.getShop().getShopImg());
	}
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺4");
		shop.setShopDesc("test4");
		shop.setShopAddr("test4");
		shop.setPhone("test4");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");		
		
		File shopImg = new File("D:\\projectdev\\image\\rabbit.jpg");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution se = shopService.addShop(shop, is, shopImg.getName());
		
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
		
	}
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop(); 
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 3, 2);
		System.out.println("shopList-number:" + se.getShopList().size());
		System.out.println("shopList-total-number: " + se.getCount());
	}
}

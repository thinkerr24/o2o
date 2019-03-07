package com.rr.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rr.o2o.BaseTest;
import com.rr.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest {
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	
	@Test
	public void testQueryShopCategory() {
		ShopCategory shopCategory = new ShopCategory();
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategory);
		// table has 2 rows! Before you test, insert sql-script the row!
		assertEquals(2, shopCategoryList.size());
		ShopCategory testShopCategory = new ShopCategory();
		ShopCategory parentShopCategory = new ShopCategory();
		parentShopCategory.setShopCategoryId(1L);
		testShopCategory.setParent(parentShopCategory);
		shopCategoryList = shopCategoryDao.queryShopCategory(testShopCategory);
		assertEquals(1, shopCategoryList.size());	
		System.out.println(shopCategoryList.get(0).getShopCategoryName());
	}
}

package com.rr.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rr.o2o.BaseTest;
import com.rr.o2o.dto.ImageHolder;
import com.rr.o2o.dto.ProductExecution;
import com.rr.o2o.entity.Product;
import com.rr.o2o.entity.ProductCategory;
import com.rr.o2o.entity.Shop;
import com.rr.o2o.enums.ProductStateEnum;
import com.rr.o2o.exceptions.ProductOperationException;
import com.rr.o2o.exceptions.ShopOperationException;

public class ProductServiceTest extends BaseTest{
	@Autowired
	private ProductService productService;

	@Test
	public void testAddProduct() throws ShopOperationException, FileNotFoundException, ProductOperationException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1l);

		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1l);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1描述");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

		File thumbnailFile = new File("D:/projectdev/image/cat.jpg");
		InputStream in = new FileInputStream(thumbnailFile);

		ImageHolder thumbnail = new ImageHolder(in, thumbnailFile.getName());

		File productImg1 = new File("D:/projectdev/image/flower2.jpg");
		InputStream in1 = new FileInputStream(productImg1);
		File productImg2 = new File("D:/projectdev/image/flower1.jpg");
		InputStream in2 = new FileInputStream(productImg2);

		List<ImageHolder> productImgList = new ArrayList<>();
		productImgList.add(new ImageHolder(in1, productImg1.getName()));
		productImgList.add(new ImageHolder(in2, productImg2.getName()));

		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);

		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());

	}
	
	@Test
	public void testModifyProduct() throws ProductOperationException, FileNotFoundException {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1l);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1l);
		product.setProductId(2l);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("正式商品");
		product.setProductDesc("正式商品描述");

		File thumbnailFIle = new File("D:/projectdev/image/flower1.jpg");
		InputStream is = new FileInputStream(thumbnailFIle);
		ImageHolder thumbnail = new ImageHolder(is, thumbnailFIle.getName());

		File productImg1 = new File("D:/projectdev/image/flower1.jpg");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("D:/projectdev/image/flower1.jpg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<>();
		productImgList.add(new ImageHolder(is1, productImg1.getName()));
		productImgList.add(new ImageHolder(is2, productImg2.getName()));

		ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
}

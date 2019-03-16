package com.rr.o2o.service;

import java.io.InputStream;

import com.rr.o2o.dto.ShopExecution;
import com.rr.o2o.entity.Shop;
import com.rr.o2o.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 * Return shop-list according to shopCondition pagination
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	/**
	 * Get shop-info by shop-id
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId); 
	
	/**
	 * Update shop-info
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
	/**
	 * Register shop-info, including picture-deal
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}

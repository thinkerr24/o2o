package com.rr.o2o.dao;

import com.rr.o2o.entity.Shop;

public interface ShopDao {
  /**
   * Add new shop
   */
	int insertShop(Shop shop);

  /**
   * Update shop info
   */
	int updateShop(Shop shop);

  /**
   * Query shop by shopId
   */
	Shop queryByShopId(long shopId);
}

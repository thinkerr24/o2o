package com.rr.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rr.o2o.entity.Shop;

public interface ShopDao {
   /**
    *  Pagination-querying shop, Support condition: shop-name(), shop-status, 
    *  category, id, owner
    */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
			@Param("rowIndex")int rowIndex, @Param("pageSize")int pageSize);
  /**
   * Query Shop-Number
   */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
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

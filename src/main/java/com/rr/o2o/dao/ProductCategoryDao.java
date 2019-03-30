package com.rr.o2o.dao;

import java.util.List;

import com.rr.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * Query productCategory by shopId
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
}

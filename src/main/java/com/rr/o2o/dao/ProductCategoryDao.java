package com.rr.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rr.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * Query productCategory by shopId
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	/**
	 * Batch add product-category s 
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	/**
	 * Delete specific productCategory
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId, @Param("shopId")long shopId);
}

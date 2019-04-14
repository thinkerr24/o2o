package com.rr.o2o.dao;

import com.rr.o2o.entity.Product;

public interface ProductDao {
	/**
	 * Add product
	 */
	int insertProduct(Product product);
	
	Product queryProductById(long productId);
	
	int updateProduct(Product product);
	
}

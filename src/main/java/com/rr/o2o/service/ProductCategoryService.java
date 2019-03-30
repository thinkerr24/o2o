package com.rr.o2o.service;

import java.util.List;

import com.rr.o2o.entity.ProductCategory;

public interface ProductCategoryService {
	List<ProductCategory> getProductCategoryList(long shopId);
}

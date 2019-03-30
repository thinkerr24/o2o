package com.rr.o2o.service;

import java.util.List;

import com.rr.o2o.dto.ProductCategoryExecution;
import com.rr.o2o.entity.ProductCategory;
import com.rr.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	List<ProductCategory> getProductCategoryList(long shopId);
	
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
	throws ProductCategoryOperationException;
}

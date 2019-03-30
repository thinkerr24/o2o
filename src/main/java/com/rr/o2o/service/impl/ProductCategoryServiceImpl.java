package com.rr.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rr.o2o.dao.ProductCategoryDao;
import com.rr.o2o.dto.ProductCategoryExecution;
import com.rr.o2o.entity.ProductCategory;
import com.rr.o2o.enums.ProductCategoryStateEnum;
import com.rr.o2o.exceptions.ProductCategoryOperationException;
import com.rr.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}
	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectedNum <= 0) {
					throw new ProductCategoryOperationException("店铺类别创建失败");
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			} catch(Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
			}
		} else {
			return  new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

}

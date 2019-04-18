package com.rr.o2o.service;

import java.util.List;

import com.rr.o2o.dto.ImageHolder;
import com.rr.o2o.dto.ProductExecution;
import com.rr.o2o.entity.Product;
import com.rr.o2o.exceptions.ProductOperationException;

public interface ProductService {
	Product getProductById(long productId);
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, 
			List<ImageHolder> productImgHolderList) throws ProductOperationException;
	/**
	 * Add product-info and image-deal
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgList) throws ProductOperationException;
	
	/**
	 * 查询商品列表并分页，可输入的条件有：商品名（模糊）、商品状态、店铺id、商品类别
	 * 
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}

package com.rr.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.rr.o2o.dto.ImageHolder;
import com.rr.o2o.dto.ProductExecution;
import com.rr.o2o.entity.Product;
import com.rr.o2o.exceptions.ProductOperationException;

public interface ProductService {
	/**
	 * Add product-info and image-deal
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgList) throws ProductOperationException;
	
}

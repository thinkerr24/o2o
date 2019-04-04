package com.rr.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rr.o2o.dao.ProductDao;
import com.rr.o2o.dao.ProductImgDao;
import com.rr.o2o.dto.ImageHolder;
import com.rr.o2o.dto.ProductExecution;
import com.rr.o2o.entity.Product;
import com.rr.o2o.exceptions.ProductOperationException;
import com.rr.o2o.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired 
	private ProductImgDao productImgDao;
	
	@Override
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		
		return null;
	}

}

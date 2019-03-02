package com.rr.o2o.service;

import java.io.InputStream;

import com.rr.o2o.dto.ShopExecution;
import com.rr.o2o.entity.Shop;
import com.rr.o2o.exceptions.ShopOperationException;

public interface ShopService {
	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}

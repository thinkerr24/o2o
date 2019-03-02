package com.rr.o2o.service;

import java.io.File;

import com.rr.o2o.dto.ShopExecution;
import com.rr.o2o.entity.Shop;

public interface ShopService {
	ShopExecution addShop(Shop shop, File shopImg);
}

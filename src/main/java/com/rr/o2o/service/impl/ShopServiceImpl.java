package com.rr.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rr.o2o.dao.ShopDao;
import com.rr.o2o.dto.ShopExecution;
import com.rr.o2o.entity.Shop;
import com.rr.o2o.enums.ShopStateEnum;
import com.rr.o2o.exceptions.ShopOperationException;
import com.rr.o2o.service.ShopService;
import com.rr.o2o.util.ImageUtil;
import com.rr.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName)
			throws ShopOperationException {
			// Judge null-value
			if (shop == null) {
				return new ShopExecution(ShopStateEnum.NULL_SHOP);
			}
			try {
				// Init shop
				shop.setEnableStatus(0);
				shop.setCreateTime(new Date());
				shop.setLastEditTime(new Date());
				// Add shop to db
				int effectedNum = shopDao.insertShop(shop);
				if (effectedNum <= 0) {
					throw new ShopOperationException("店铺创建失败");
				} else {
					if (shopImgInputStream != null) {
						// Store pic
						try {
							addShopImg(shop, shopImgInputStream, fileName);
						} catch (Exception e) {
							throw new ShopOperationException("添加商店图片错误:" + e.getMessage());
						}
						// Renew shop-pic-addr
						effectedNum = shopDao.updateShop(shop);
						if (effectedNum <= 0) {
							throw new ShopOperationException("更新图片地址失败");
						}
					}
				}
				
			} catch(Exception e) {
				throw new ShopOperationException("添加商店错误:" + e.getMessage());
			}
			return new ShopExecution(ShopStateEnum.CHECK, shop);
		}


	private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
		// Get shop-pic-dir relative dir
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
		shop.setShopImg(shopImgAddr);
		
	}


	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}


	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
			throws ShopOperationException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		} else {
		// 1.Judge to deal picture?
			try {
			if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
				Shop tempShop = shopDao.queryByShopId(shop.getShopId());
				if (tempShop.getShopImg() != null) {
					ImageUtil.deleteFileOrPath(shop.getShopImg());
				}
				addShopImg(shop, shopImgInputStream, fileName);
			}
		// 2.Update shop-info
			shop.setLastEditTime(new Date());
			int effectedNum = shopDao.updateShop(shop);
			if (effectedNum < 1) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			} else {
				shop = shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS, shop);
			}
		  } catch(Exception ex) {
				throw new ShopOperationException("modifyShop error:" + ex.getMessage());
			}
		} 

	}


}

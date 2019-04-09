package com.rr.o2o.service.impl;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rr.o2o.dao.ProductDao;
import com.rr.o2o.dao.ProductImgDao;
import com.rr.o2o.dto.ImageHolder;
import com.rr.o2o.dto.ProductExecution;
import com.rr.o2o.entity.Product;
import com.rr.o2o.entity.ProductImg;
import com.rr.o2o.enums.ProductStateEnum;
import com.rr.o2o.exceptions.ProductOperationException;
import com.rr.o2o.service.ProductService;
import com.rr.o2o.util.ImageUtil;
import com.rr.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired 
	private ProductImgDao productImgDao;
	
	@Override
	@Transactional
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationException {
		// 1. 处理缩略图，获取罗略图相对路径并赋值给product
				// 2. 往tb_product写入商品信息，获取productId
				// 3. 结合productId批量处理商品详情图
				// 4. 将商品详情图列表批量插入tb_product_img中

				if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
					product.setCreateTime(new Date());
					product.setLastEditTime(new Date());
					product.setEnableStatus(1);

					if (thumbnail != null) {
						addThumbnail(product, thumbnail);
					}
					try {
						int effectedNum = productDao.insertProduct(product);
						if (effectedNum <= 0) {
							throw new ProductOperationException("创建商品失败");
						}
					} catch (Exception e) {
						throw new ProductOperationException("创建商品失败" + e.toString());
					}

					// 若商品详情图不为空则添加
					if (productImgList != null && productImgList.size() > 0) {
						addProductImgList(product, productImgList);
					}
					return new ProductExecution(ProductStateEnum.SUCCESS, product);
				} else {
					return new ProductExecution(ProductStateEnum.EMPTY);
				}
	}

	// Batch add detail-image
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<>();
		// Add product-Imgs into entity-class
		for (ImageHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		// if need, batch insert into db. 
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperationException("创建商品详情图片失败" + e.toString());
			}
		}
		
	}

	// Add thumbnail
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
		
	}
	

}

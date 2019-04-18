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
import com.rr.o2o.util.PageCalculator;
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

	@Override
	public Product getProductById(long productId) {
		
		return productDao.queryProductById(productId);
	}

	@Override
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgHolderList) throws ProductOperationException {
				// 1. 若缩略图参数有值，则处理缩略图
				// 2. 若原来存在缩略图则先删除再添加新的缩略图，之后获取缩略图相对路径并赋值给product
				// 3. 若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
				// 4. 将tb_product_img下面的该商品原来的商品详情图记录全部清除
				// 5. 更新tb_product_img以及tb_product的信息
				if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
					// 给商品设置默认属性
					product.setLastEditTime(new Date());
					// 若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
					if (thumbnail != null) {
						// 先获取一遍原有的信息，因为原来的信息有原图片地址
						Product tempProduct = productDao.queryProductById(product.getProductId());
						if (tempProduct.getImgAddr() != null) {
							ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
						}
						addThumbnail(product, thumbnail);
					}
					// 如果有新存入的商品详情图，则先删除原来的，再添加新的
					if (productImgHolderList != null && productImgHolderList.size() > 0) {
						deleteProductImgList(product.getProductId());
						addProductImgList(product, productImgHolderList);
					}
					try {
						// 更新商品信息
						int effectedNum = productDao.updateProduct(product);
						if (effectedNum <= 0) {
							throw new ProductOperationException("更新商品信息失败");
						}
						return new ProductExecution(ProductStateEnum.SUCCESS, product);
					} catch (Exception e) {
						throw new ProductOperationException("创建商品详情图片失败;" + e.toString());
					}
				} else {
					return new ProductExecution(ProductStateEnum.EMPTY);
				}
		}
				
		/**
		 * 删除某个商品下的所有详情图
		 * 
		 * @param productId
		 */
		private void deleteProductImgList(Long productId) {
			// 根据productId获取原来的图片
			List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
			// 删除原来的图片
			for (ProductImg productImg : productImgList) {
				ImageUtil.deleteFileOrPath(productImg.getImgAddr());
			}
			// 删除数据库里原有的图片的嘻嘻
			productImgDao.deleteProductImgByProductId(productId);		
			
	}

		@Override
		public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
			// 页码转换成数据库的行，并调用dao层取回指定页码的商品列表
			int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
			// 基于同样的查询条件返回该查询条件下的商品总数
			int count = productDao.queryProductCount(productCondition);
			ProductExecution pe = new ProductExecution();
			pe.setProductList(productList);
			pe.setCount(count);
			return pe;
		}
	

}

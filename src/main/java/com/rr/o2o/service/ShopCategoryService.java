package com.rr.o2o.service;

import java.util.List;
import com.rr.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	/**
	 * 根据查询条件获取商店类别列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}

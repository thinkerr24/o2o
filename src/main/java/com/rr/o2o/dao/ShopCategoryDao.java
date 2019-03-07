package com.rr.o2o.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.rr.o2o.entity.ShopCategory;

public interface ShopCategoryDao {
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCodition")ShopCategory shopCategoryCodition);
}
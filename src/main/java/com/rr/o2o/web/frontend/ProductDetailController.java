package com.rr.o2o.web.frontend;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rr.o2o.entity.Product;
import com.rr.o2o.enums.ProductStateEnum;
import com.rr.o2o.service.ProductService;
import com.rr.o2o.util.HttpServletRequestUtil;


@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

	@Autowired
	private ProductService productService;

	/**
	 * 获取商品详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listproductdetailpageinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		if (productId != -1) {
			product = productService.getProductById(productId);
			modelMap.put("product", product);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ProductStateEnum.EMPTY.getStateInfo());
		}
 		return modelMap;
	}
}
package com.rr.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.o2o.dto.ImageHolder;
import com.rr.o2o.dto.ProductExecution;
import com.rr.o2o.entity.Product;
import com.rr.o2o.entity.Shop;
import com.rr.o2o.enums.ProductStateEnum;
import com.rr.o2o.exceptions.ProductOperationException;
import com.rr.o2o.service.ProductService;
import com.rr.o2o.util.CodeUtil;
import com.rr.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	// Upload-imgage max num.
	private static final int IMAGEMAXCOUNT = 6;
	
	@RequestMapping(value="/addproduct", method = RequestMethod.POST) 
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartRquest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			// If request exists file-stream, get the file (image)
			if (multipartResolver.isMultipart(request)) {
				multipartRquest = (MultipartHttpServletRequest) request;
				
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile)multipartRquest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
				
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRquest.getFile("productImg" + i);
					if (productImgFile != null) {
						ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(), productImgFile.getOriginalFilename());
						productImgList.add(productImg);
					} else {
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传的照片不能为空"); 
				return modelMap;
			}
			
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString()); 
			return modelMap;
		}
		
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString()); 
			return modelMap;
		}
		
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo()); 
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString()); 
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息"); 
			
		}
		return modelMap;
	}
	
}

package com.rr.o2o.web.shopadmin;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rr.o2o.dto.ShopExecution;
import com.rr.o2o.entity.PersonInfo;
import com.rr.o2o.entity.Shop;
import com.rr.o2o.enums.ShopStateEnum;
import com.rr.o2o.exceptions.ShopOperationException;
import com.rr.o2o.service.ShopService;
import com.rr.o2o.util.HttpServletRequestUtil;
import com.rr.o2o.util.ImageUtil;
import com.rr.o2o.util.PathUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@RequestMapping(value="/registershop", method=RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 1. Receive and convert corresponding argument, including shop information and pic information
		
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
			
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		// Deal file-stream
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver resolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (resolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 2. Register shop
		if (shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			// Session TODO
			owner.setUserId(1L);
			shop.setOwner(owner);
	
			ShopExecution se;
			try {
				se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());				
			}
			
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		
	}
	

}

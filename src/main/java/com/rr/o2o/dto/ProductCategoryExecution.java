package com.rr.o2o.dto;

import java.util.List;

import com.rr.o2o.entity.ProductCategory;
import com.rr.o2o.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
	// Result-state
	private int state;
	
	//State-identify
	private String stateInfo;
	
	private List<ProductCategory> productCategoryList;
	
	public ProductCategoryExecution() {
		
	}
	
	// Constructor when operate-fail
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
		state = stateEnum.getState();
		stateInfo = stateEnum.getStateInfo();
	}
	
	// Constructor when operate-success
	public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {
		state = stateEnum.getState();
		stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList; 
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
	
}

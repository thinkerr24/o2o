package com.rr.o2o.dto;

import java.util.List;

import com.rr.o2o.entity.Shop;
import com.rr.o2o.enums.ShopStateEnum;

public class ShopExecution {
	// Result state
	private int state;
	
	// State identify
	private String stateInfo;
	
	// Shop number
	private int count;
	
	// Operating shop(When Create Delete Update shop)
	private Shop shop;
	
	// Shop list(When Query shop-list)
	private List<Shop> shopList;
	
	public ShopExecution() {}
	
	// Use this constructor When fail to operate shop
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	// Use this constructor When  operate shop successfully
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		this(stateEnum);
		this.shop = shop;
	}
	
	// Use this constructor When  operate shop successfully
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
		this(stateEnum);
		this.shopList = shopList;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
}

package com.rr.o2o.util;

public class PathUtil {
	private static String SEPERATOR = System.getProperty("file.seperator");
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/image";
		} else {
			basePath = "/home/rr/image/";
		}
		basePath = basePath.replace("/", SEPERATOR);
		return basePath;
	}
	
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", SEPERATOR);
	}
}

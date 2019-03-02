package com.rr.o2o.util;

public class PathUtil {
	private static String SEPARATOR = System.getProperty("file.separator");
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/image/";
		} else {
			basePath = "/home/rr/image";
		}
		return basePath.replace("/", SEPARATOR);
	
	}
	
	public static String getShopImagePath(long shopId) {
		String imagePath = "upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", SEPARATOR);
	}
}

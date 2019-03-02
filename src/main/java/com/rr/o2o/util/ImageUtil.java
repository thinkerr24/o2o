package com.rr.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	public static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat SDATEFORMAT = new SimpleDateFormat("yyyyMMddmmss");
	private static final Random r = new Random();
	
	public static String  generateThumbnail(File thumbnail, String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDIrPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25F)
			.outputQuality(0.8F)
			.toFile(dest);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return relativeAddr;
	}
	
	/**
	 *  Create target directory. Such as /home/work/user/xxx.jpg, 
	 *  So directory home, work, user will be created.
	 * @param targetAddr
	 */
	
	private static void makeDIrPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
	}
	// Get InputStream file extension name
	private static String getFileExtension(File file) {
		String originalFileName = file.getName();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * Generate random filename, current time year+month+second+random_5_bit_number
	 * @return
	 */
	private static String getRandomFileName() {
		// Get random_5_bit_number
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = SDATEFORMAT.format(new Date());
		return nowTimeStr + rannum;
	}
	
}

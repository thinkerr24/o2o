package com.rr.o2o.util;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rr.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;


public class ImageUtil {
	public static String basepath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat SDATEFORMAT = new SimpleDateFormat("yyyyMMddmmss");
	private static final Random r = new Random();
	private static final Log log = LogFactory.getLog(ImageUtil.class);
	
	public static String  generateThumbnail(ImageHolder thumbnail,  String targetAddr) {
		String realFileName = getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basepath + "/watermark.jpg")), 0.25F)
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
	
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
	}
	// Get InputStream file extension name
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * Generate random filename, current time year+month+second+random_5_bit_number
	 * @return
	 */
	public static String getRandomFileName() {
		// Get random_5_bit_number
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = SDATEFORMAT.format(new Date());
		return nowTimeStr + rannum;
	}
	/**
	 *  stotrPath is file-path or dir-path?
	 *  if file-path: remove the file;
	 *  if  dir-path: remove all file of the dir.
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File[] files = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}

	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		
		// Get random-value of no-repeat
		String realFileName = getRandomFileName();
		// Get file-extendsion-name
		String extension = getFileExtension(thumbnail.getImageName());
		// If target-dir not exists, create it!
		makeDirPath(targetAddr);
		// Get file-save relative dir (with filename)
		String relativeAddr = targetAddr + realFileName + extension;
		log.debug("current relativeAddr is : " + relativeAddr);
		// Get relative-dir to save file 
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		log.debug("current complete addr is : " + PathUtil.getImgBasePath() + relativeAddr);
		// Generate pic with watermark
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basepath + "/watermark.jpg")), 0.25f)
					.outputQuality(0.9F).toFile(dest);
		} catch (IOException e) {
			log.error(e.toString());
			e.printStackTrace();
			throw new RuntimeException("创建缩略图片失败" + e.toString());
		}
		return relativeAddr;
	}
}

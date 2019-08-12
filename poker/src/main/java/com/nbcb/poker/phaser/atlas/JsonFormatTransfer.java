package com.nbcb.poker.phaser.atlas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.nbcb.common.util.InputStreamUtil;
import com.nbcb.common.util.JsonUtil;

public class JsonFormatTransfer {

	public static void transfer(String originDir, String destDir)
			throws Exception {
		File oriFile = new File(originDir);
		if (!oriFile.exists()) {
			throw new Exception();
		}
		File destFile = new File(destDir);
		if (!destFile.exists()) {
			destFile.mkdir();
		}
		File files[] = oriFile.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".json")) {
				try{
					FileInputStream fis = new FileInputStream(file);
					byte[] bytes = InputStreamUtil.readStream(fis, fis.available());
					fis.close();
					String str = new String(bytes);
					str = transfer(str);
					System.out.println(file.getName());
					System.out.println(str);
					FileOutputStream fos = new FileOutputStream(destDir + "\\"
							+ file.getName());
					fos.write(str.getBytes());
					fos.close();
				}catch(Exception e){
					
				}
			}
		}

	}

	public static String transfer(String str) throws Exception {
//		Map map = (Map) JsonUtil.decode(str, HashMap.class);
//		map = (Map) map.get("frames");
//		Set set = map.keySet();
//		AtlasJson aj = new AtlasJson();
//
//		for (Object obj : set) {
//			String key = (String) obj;
//			System.out.println(key);
//			Map tmpMap = (Map) map.get(key);
//			int x = (int) tmpMap.get("x");
//			int y = (int) tmpMap.get("y");
//			int w = (int) tmpMap.get("w");
//			int h = (int) tmpMap.get("h");
//			int offX = (int) tmpMap.get("offX");
//			int offY = (int) tmpMap.get("offY");
//			int sourceW = (int) tmpMap.get("sourceW");
//			int sourceH = (int) tmpMap.get("sourceH");
//
//			AtlasJson.Item item = new AtlasJson.Item();
//			aj.addItem(item);
//
//			item.setFilename(key);
//
//			AtlasJson.AtlasJsonXywh frame = new AtlasJson.AtlasJsonXywh();
//			frame.setX(x);
//			frame.setY(y);
//			frame.setW(w);
//			frame.setH(h);
//			item.setFrame(frame);
//
//			AtlasJson.AtlasJsonXywh spriteSourceSize = new AtlasJson.AtlasJsonXywh();
//			spriteSourceSize.setX(offX);
//			spriteSourceSize.setY(offY);
//			spriteSourceSize.setW(sourceW);
//			spriteSourceSize.setH(sourceH);
//			item.setSpriteSourceSize(spriteSourceSize);
//
//			AtlasJson.AtlasJsonXywh sourceSize = new AtlasJson.AtlasJsonXywh();
//			sourceSize.setW(sourceW);
//			sourceSize.setH(sourceH);
//			item.setSourceSize(sourceSize);
//
//		}
//		Map retMap = new HashMap();
//		retMap.put("frames", aj.getFrame());
//		return JsonUtil.encode(retMap);
		return "";
	}

	public static void main(String[] args) throws Exception {
		 FileInputStream fis = new FileInputStream(
		 "C:\\Users\\Administrator\\Desktop\\十三关\\图片\\thirteen_poker.json");
		 byte[] bytes = InputStreamUtil.readStream(fis, fis.available());
		 String str = new String(bytes);
		 str = transfer(str);
		 System.out.println(str);
		 FileOutputStream fos = new FileOutputStream(
		 "C:\\Users\\Administrator\\Desktop\\thirteen_poker.json");
		 fos.write(str.getBytes());
		 fos.close();
		 fis.close();

//		transfer("C:\\Users\\Administrator\\Desktop\\十三关\\图片\\", "C:\\Users\\Administrator\\Desktop\\十三关\\图片1");

	}

}

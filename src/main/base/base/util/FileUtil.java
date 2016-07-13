package base.util;

import java.io.File;

public class FileUtil {

	/**
	 * 判断文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean fileExist(String name) {
		String value=name.replaceAll("\r|\n", "");
		File file = new File(Constent.FILE_ROOT_PAHT+value);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 获得文件的绝对路径
	 * @param name
	 * @return
	 */
	public static String getPath(String name){
		String value=name.replaceAll("\r|\n", "");
		return Constent.FILE_ROOT_PAHT+value;
	}
}

package base.util;

public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value)) {
			return true;
		} else {
			return false;
		}
	}
}

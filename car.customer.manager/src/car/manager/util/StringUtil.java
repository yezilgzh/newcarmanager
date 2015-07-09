package car.manager.util;

public class StringUtil {

	/**
	 * 过滤null字符串
	 * 
	 * @param str
	 *            如果参数为null，则返回""，否则直接返回str
	 * @return 过滤后的字符串
	 * @exclude
	 */
	public static String filterNull(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

}

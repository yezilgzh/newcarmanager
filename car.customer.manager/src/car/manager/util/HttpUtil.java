package car.manager.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import car.manager.dto.Consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

public class HttpUtil {
	private static final String URL = "http://localhost:6969/web";
	static Logger log = Logger.getLogger(HttpUtil.class);

	private static String getFromUrl(String domain) throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(domain);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream(), "GBK"));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) {
			throw e;
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		String result = getFromUrl("http://localhost:6969/web/carquery?carno=939");

		List rs = (List) JSON.parseObject(result, new TypeReference() {
		}, new Feature[0]);

		System.out.println(rs.size());
		String result2 = getFromUrl("http://localhost:6969/web/userrecords?userid="
				+ ((Consumer) rs.get(0)).getCarno());
		System.out.println(result2);
		Consumer cs = (Consumer) JSON.parseObject(result2, Consumer.class);
		System.out.println(cs);
	}
}
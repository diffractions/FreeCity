package utils;

public class HTTP_URL_Utils {
	public static String encode(String request) {
		if (request != null && !request.equals("?null"))
			return request.replaceAll("\\?", "!1").replaceAll("=", "!2").replaceAll("&", "!3");
		return "";
	}
	public static String decode(String request) {
		if (request != null)
			return request.replaceAll("!1", "?").replaceAll("!2", "=").replaceAll("!3", "&");
		return "";
	}
}

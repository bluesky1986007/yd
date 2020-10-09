package common.frame.utils;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CookieJar是用户保存Cookie的
 */
public class LocalCookieJar implements CookieJar {

	List<Cookie> cookies;


	@Override
	public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
		this.cookies =  cookies;

	}

	@Override
	public List<Cookie> loadForRequest(HttpUrl arg0) {

		if (cookies != null)
		return cookies;
		return new ArrayList<Cookie>();
	}
}

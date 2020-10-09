package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Headers;

import java.net.MalformedURLException;
import java.util.List;

public interface HttpClientService {

	/**
	 * doGet
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public JSONArray doGetOfJSONArray(String url) throws MalformedURLException, Exception;

	/**
	 * doGet 返回JSONObject
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public JSONObject doGetOfJSONObject(String url) throws Exception;


	/**
	 * doGetWithCookie
	 * @param url
	 * @return body
	 * @throws Exception
	 */
	public String doGetWithCookie(String url,String cookie) throws Exception;

	/**
	 * doGetWithCookieOfJSONObject
	 * @param url
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public JSONObject doGetWithCookieOfJSONObject(String url,String cookie) throws Exception;


	/**
	 * doGetWithCookieOfJSONObject,有wait等待时间的
	 * @param url
	 * @param cookie
	 * @param waitTime
	 * @return
	 * @throws Exception
	 */
	public JSONObject doGetWithCookieOfJSONObject(String url,String cookie,long waitTime) throws Exception;

	/**
	 * doPostOfJSONObject
	 * @param data
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public JSONObject doPostOfJSONObject(String url, String data, String dataType) throws Exception;

	/**
	 * doPost
	 * @param url
	 * @param data
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url, String data, String dataType) throws Exception;


	/**
	 * doPostWithCookie
	 * @param data
	 * @param dataType
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public JSONObject doPostWithCookieOfJSONObjectNoCache(String url, String data, String dataType, String cookie) throws MalformedURLException, Exception;

	/**
	 *doPostWithCookieOfJSONObject
	 * @param url
	 * @param data
	 * @param dataType
	 * @param cookie
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public JSONObject doPostWithCookieOfJSONObject(String url, String data, String dataType, String cookie) throws MalformedURLException, Exception;


	/**
	 * doPostWithCookieOfJSONObject 需要等待时间
	 * @param url
	 * @param data
	 * @param dataType
	 * @param cookie
	 * @param waitTime
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public JSONObject doPostWithCookieOfJSONObject(String url, String data, String dataType, String cookie, long waitTime) throws MalformedURLException, Exception;


	/**
	 * doPost 重定向返回302的请求并且需要cookie
	 * @param data
	 * @param dataType
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Headers doPost302HeadersWithCookie(String url, String data, String dataType, String cookie) throws Exception;


	/**
	 * doPost302Headers
	 * @param url
	 * @param data
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public Headers doPost302Headers(String url, String data, String dataType) throws Exception;

	/**
	 * doGet 重定向返回302的请求并且需要cookie
	 * @param url
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public Headers doGet302HeadersWithCookie(String url, String cookie) throws Exception;

	/**
	 * 获取post请求response中的headers信息
	 * @param url
	 * @param data
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public List doPostAndGetOneOfHeaders(String url, String data, String dataType, String key) throws Exception;


}

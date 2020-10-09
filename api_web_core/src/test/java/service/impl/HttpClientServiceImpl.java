package service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.frame.utils.LocalCookieJar;
import okhttp3.*;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import service.HttpClientService;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpClientServiceImpl extends AbstractTestNGSpringContextTests implements HttpClientService {

	@Override
	public JSONArray doGetOfJSONArray(String url) throws MalformedURLException, Exception {

//		JSONObject[] jsonResult=null;

		logger.info("doGetOfJSONArray开始...");
		JSONArray jsonResult;
//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("cache-control", "no-cache")
				.build();

		Response response = client.newCall(request).execute();
		Thread.sleep(1000);
		String tempResponse =  response.body().string();

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);

		logger.info("tempResponseOfGet = "+tempResponse);
		jsonResult= JSON.parseArray(tempResponse);

		return jsonResult;

	}

	@Override
	public JSONObject doGetOfJSONObject(String url) throws Exception {
		JSONObject jsonResult;

		logger.info("doGetOfJSONObject开始...");
//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(20,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("cache-control", "no-cache")
				.build();

		Response response = client.newCall(request).execute();
//		Thread.sleep(2000);
		String tempResponse =  response.body().string();

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);

		logger.info("tempResponseOfGet = "+tempResponse);

		jsonResult= JSON.parseObject(tempResponse);

		return jsonResult;
	}

	@Override
	public String doGetWithCookie(String url,String cookie) throws Exception {

		logger.info("doGetWithCookie开始...");
//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("cache-control", "no-cache")
				.addHeader("cookie", cookie)
				.build();

		Response response = client.newCall(request).execute();
//		Thread.sleep(2000);
		String tempResponse =  response.body().string();

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);

		if (!tempResponse.contains("<!DOCTYPE html")){

			logger.info("tempResponseOfGet = "+tempResponse);

		}

		return tempResponse;
	}

	@Override
	public JSONObject doGetWithCookieOfJSONObject(String url, String cookie) throws Exception {
		logger.info("doGetWithCookieOfJSONObject开始...");
		JSONObject jsonResult=null;
//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("cache-control", "no-cache")
				.addHeader("cookie", cookie)
				.build();

		Response response = client.newCall(request).execute();
//		Thread.sleep(2000);
		String tempResponse =  response.body().string();

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);
//		logger.info("tempResponseOfGet = "+tempResponse);

		if (!tempResponse.contains("<!DOCTYPE html>")){

			logger.info("tempResponseOfGet = "+tempResponse);
			jsonResult= JSON.parseObject(tempResponse);

		}

		return jsonResult;
	}

	@Override
	public JSONObject doGetWithCookieOfJSONObject(String url, String cookie, long waitTime) throws Exception {

		logger.info("doGetWithCookieOfJSONObject开始...");
		JSONObject jsonResult=null;
//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(30,TimeUnit.SECONDS).readTimeout(40,TimeUnit.SECONDS).build();

		Request request = new Request.Builder()
				.url(url)
				.get()
//				.addHeader("cache-control", "no-cache")
				.addHeader("Connection", "keep-alive")
				.addHeader("Accept", "*/*")
				.addHeader("cookie", cookie)
				.build();

		Response response = client.newCall(request).execute();
		Thread.sleep(waitTime);
		String tempResponse =  response.body().string();

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);
//		logger.info("tempResponseOfGet = "+tempResponse);

		if (!tempResponse.contains("<!DOCTYPE html>")){

			logger.info("tempResponseOfGet = "+tempResponse);
			jsonResult= JSON.parseObject(tempResponse);

		}

		return jsonResult;
	}

	@Override
	public JSONObject doPostOfJSONObject(String url, String data, String dataType) throws Exception {
		// TODO Auto-generated method stub

		logger.info("doPostOfJSONObject开始...");
		JSONObject jsonResult;

//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(20,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();

		MediaType mediaType = MediaType.parse(dataType);
		RequestBody body = RequestBody.create(mediaType,data);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", dataType)
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache")
				.addHeader("Connection", "keep-alive")
				.build();

		Response response = client.newCall(request).execute();
//		Thread.sleep(3000);
		String tempResponse =  response.body().string();

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);


		logger.info("tempResponseOfPost = "+tempResponse);
		jsonResult= JSON.parseObject(tempResponse);

		return jsonResult;
	}

	@Override
	public String doPost(String url, String data, String dataType) throws Exception {

		logger.info("doPostOfString开始...");

//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(20,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();

		MediaType mediaType = MediaType.parse(dataType);
		RequestBody body = RequestBody.create(mediaType,data);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", dataType)
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache")
				.addHeader("Connection", "keep-alive")
				.build();

		Response response = client.newCall(request).execute();
//		Thread.sleep(3000);
		String tempResponse =  response.body().string();

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);

//		logger.info("tempResponseOfPost = "+tempResponse);

		return tempResponse;

	}

	@Override
	public JSONObject doPostWithCookieOfJSONObjectNoCache(String url, String data, String dataType, String cookie) throws MalformedURLException, Exception {
		JSONObject jsonResult;

		logger.info("doPostWithCookieOfJSONObject noCache开始...");
//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(20,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();

		MediaType mediaType = MediaType.parse(dataType);
		RequestBody body = RequestBody.create(mediaType,data);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", dataType)
				.addHeader("cookie", cookie)
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache")
				.addHeader("Connection", "keep-alive")
//				.addHeader("Host", "b2b.uat.homedo.com")
				.build();

		Response response = client.newCall(request).execute();
//		Thread.sleep(2000);
		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);

		String tempResponse =  response.body().string();
		logger.info("tempResponseOfPost = "+tempResponse);
		jsonResult= JSON.parseObject(tempResponse);


		return jsonResult;
	}

	@Override
	public JSONObject doPostWithCookieOfJSONObject(String url, String data, String dataType, String cookie) throws MalformedURLException, Exception {

		JSONObject jsonResult;

		logger.info("doPostWithCookieOfJSONObject开始...");
//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(30,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();
//		OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(new LocalCookieJar()).connectTimeout(20,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();


		MediaType mediaType = MediaType.parse(dataType);
		RequestBody body = RequestBody.create(mediaType,data);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", dataType)
				.addHeader("cookie", cookie)
				.addHeader("Accept", "*/*")
//				.addHeader("Cache-Control", "no-cache")
				.addHeader("Connection", "keep-alive")
//				.addHeader("Host", "b2b.uat.homedo.com")
				.build();

		Response response = client.newCall(request).execute();

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);

		String tempResponse =  response.body().string();
		logger.info("tempResponseOfPost = "+tempResponse);
		jsonResult= JSON.parseObject(tempResponse);

		return jsonResult;

	}

	@Override
	public JSONObject doPostWithCookieOfJSONObject(String url, String data, String dataType, String cookie, long waitTime) throws MalformedURLException, Exception {
		JSONObject jsonResult = null;

		logger.info("doPostWithCookieOfJSONObject and waitTime开始...");
//		OkHttpClient client = new OkHttpClient();
		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(30,TimeUnit.SECONDS).readTimeout(40,TimeUnit.SECONDS).build();
//		OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(new LocalCookieJar()).connectTimeout(20,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();


		MediaType mediaType = MediaType.parse(dataType);
		RequestBody body = RequestBody.create(mediaType,data);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", dataType)
				.addHeader("cookie", cookie)
				.addHeader("Accept", "*/*")
//				.addHeader("Cache-Control", "no-cache")
				.addHeader("Connection", "keep-alive")
//				.addHeader("Host", "b2b.uat.homedo.com")
				.build();

		Response response = client.newCall(request).execute();
		Thread.sleep(waitTime);//uat, fat环境问题,需要进行线程等待

		logger.info("code = "+response.code());
		Assert.assertEquals(response.code(),200);

		String tempResponse =  response.body().string();
		logger.info("tempResponseOfPost = "+tempResponse);

		if (!"".equals(tempResponse)){

			jsonResult= JSON.parseObject(tempResponse);
		}

		return jsonResult;
	}


	@Override
	public Headers doPost302HeadersWithCookie(String url, String data, String dataType, String cookie) throws Exception {

		logger.info("doPost302HeadersWithCookie...");

		OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false).connectTimeout(10,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();

		MediaType mediaType = MediaType.parse(dataType);
		RequestBody body = RequestBody.create(mediaType, data);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", dataType)
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache")
				.addHeader("Cookie", cookie)
				.build();

		Response response = client.newCall(request).execute();
//		Thread.sleep(2000);

		Headers resule = response.headers();
		String headers = resule.toString();
		logger.info("headers="+headers);

		return resule;
	}

	@Override
	public Headers doPost302Headers(String url, String data, String dataType) throws Exception {

		logger.info("doPost302Headers...");

		OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false).cookieJar(new LocalCookieJar()).connectTimeout(10,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();

		MediaType mediaType = MediaType.parse(dataType);
		RequestBody body = RequestBody.create(mediaType, data);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", dataType)
				.addHeader("Accept", "*/*")
				.addHeader("cache-control", "no-cache")
				.addHeader("Connection", "keep-alive")
				.addHeader("Host", "cas.homedo.com")
				.addHeader("Origin", "https://cas.homedo.com")
				.addHeader("Referer", "https://cas.homedo.com/cas/login?service=http%3a%2f%2ferp.homedo.com%2flogin%3fgoto%3dhttp%3a%2f%2foms.homedo.com")
				.addHeader("Upgrade-Insecure-Requests", "1")
				.build();


		Response response = client.newCall(request).execute();
//		Thread.sleep(2000);
		int status = response.code();
		logger.info("status="+status);


		Headers resule = response.headers();
		String headers = resule.toString();
		logger.info("headers="+headers);

		return resule;
	}

	@Override
	public Headers doGet302HeadersWithCookie(String url, String cookie) throws Exception {


		logger.info("doGet302HeadersWithCookie...");

		OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false).connectTimeout(10,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();

		Request request = new Request.Builder()
				.url(url)
				.get()
				.addHeader("cache-control", "no-cache")
				.addHeader("cookie", cookie)
				.build();

		Response response = client.newCall(request).execute();
		Thread.sleep(1000);

		Headers resule = response.headers();
		String headers = resule.toString();
		logger.info("headers="+headers);

		return resule;


	}

	@Override
	public List doPostAndGetOneOfHeaders(String url, String data, String dataType, String key) throws Exception {
		logger.info("doPostHeaders.....");
		List list = new ArrayList<>();

		OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(20,TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).build();

		MediaType mediaType = MediaType.parse(dataType);
		RequestBody body = RequestBody.create(mediaType, data);

		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.addHeader("Content-Type", dataType)
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache")
				.build();

		Response response = client.newCall(request).execute();
//		Thread.sleep(2000);

		Headers resule = response.headers();
		String headers = resule.toString();
		logger.info("headers="+headers);

		list = response.headers(key);
		int size = list.size();
		logger.info("list size="+list.size());

		if (0==size){

			logger.info("headers中未找到相对应的key，无法获取对应的value值！");

		}

		return list;
	}


}

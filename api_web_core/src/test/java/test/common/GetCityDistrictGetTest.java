package test.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.frame.data.ExcelProvider;
import common.frame.test.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class GetCityDistrictGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--获取城市列表",timeOut=300000)
	public void getCityDistrict(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("请求地址&封装数据");
		logger.info("env="+env);
		String url=null;

		if ("uat".equals(env)){
			url = "https://japi.uat.homedo.com/product/area/getCityDistrict?Id=0";
		}else if("pro".equals(env)){
			url = "https://japi.homedo.com/product/area/getCityDistrict?Id=0";
		}else if("fat".equals(env)){
			url = "https://japi.fat.homedo.com/product/area/getCityDistrict?Id=0";

		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String msg = resp.getString("msg");
		logger.info("msg="+msg);
		Assert.assertEquals(msg,datadriven.get("msg"));

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}
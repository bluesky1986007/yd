package test.common;

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

public class GetLoginUserInfoMoreGetTest extends BaseTest {

	String ticket = null;
	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--用户拓展信息",timeOut=300000)
	public void getLoginUserInfoMore(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		if ("uat".equals(env)){

			url = "https://api.uat.homedo.com/Account/GetLoginUserInfoMore";

		}else if("fat".equals(env)){
			url = "https://api.fat.homedo.com/Account/GetLoginUserInfoMore";

		}else {
			//生产环境
			url = "https://api.homedo.com/Account/GetLoginUserInfoMore";
		}

		logger.info("url="+url);
		logger.info("name="+name);
		logger.info("pwd="+pwd);

		ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookie);

		logger.info("结果校验");
		String success = resp.getString("success");
		logger.info("success="+success);
		Assert.assertEquals(success.trim(),datadriven.get("success").trim());



		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

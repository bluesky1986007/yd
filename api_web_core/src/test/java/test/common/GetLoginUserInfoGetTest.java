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

public class GetLoginUserInfoGetTest extends BaseTest {

	String ticket = null;
	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--用户信息",timeOut=300000)
	public void getLoginUserInfo(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		if ("uat".equals(env)){

			url = "https://api.uat.homedo.com/Account/GetLoginUserInfo";

		}else if("fat".equals(env)){
			url = "https://api.fat.homedo.com/Account/GetLoginUserInfo";

		}else {
			//生产环境
			url = "https://api.homedo.com/Account/GetLoginUserInfo";
		}

		logger.info("url="+url);
		logger.info("name="+name);
		logger.info("pwd="+pwd);

		ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookie);

		logger.info("结果校验");
		String accountName = resp.getString("AccountName");
		logger.info("accountName="+accountName);
		Assert.assertEquals(accountName,name);



		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

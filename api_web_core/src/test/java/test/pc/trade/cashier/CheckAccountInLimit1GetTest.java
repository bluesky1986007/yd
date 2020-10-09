package test.pc.trade.cashier;

import com.alibaba.fastjson.JSONObject;
import common.frame.data.ExcelProvider;
import common.frame.test.BaseTest;
import okhttp3.Headers;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class CheckAccountInLimit1GetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--查询用户id是否存在于白名单",timeOut=300000)
	public void checkAccountInLimit1(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("获取用户cookie");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		String ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;
		logger.info("cookie="+cookie);


		logger.info("请求地址");
		String url = null;
		if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/mall-pcweb-compositeservice/member/actWhiteList/checkAccountInLimit1";
		}else {
			url = "https://b2b.homedo.com/mall-pcweb-compositeservice/member/actWhiteList/checkAccountInLimit1";
		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookie);

		logger.info("结果校验");
		String respDesc = resp.getString("respDesc");
		logger.info("respDesc:"+respDesc);

		Assert.assertEquals(respDesc,datadriven.get("respDesc"));

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

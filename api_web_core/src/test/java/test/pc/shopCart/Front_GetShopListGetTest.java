package test.pc.shopCart;

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

public class Front_GetShopListGetTest extends BaseTest {

	String ticket = null;
	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P2_PC前端接口_购物车页_购物车商品信息",timeOut=300000)
	public void getShopListGetTest(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");
		ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		logger.info("ticket="+ticket);
		if ("uat".equals(env)){
			url = "https://b2b.uat.homedo.com/shopping/api/getShopList?platform=B2B&key="+ticket;
		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/shopping/api/getShopList?platform=B2B&key="+ticket;
		}else {
			url = "https://b2b.homedo.com/shopping/api/getShopList?platform=B2B&key="+ticket;

		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String code = resp.getString("code");
		logger.info("code="+code);
		Assert.assertEquals(code,datadriven.get("code"));

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

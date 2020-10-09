package test.pc.trade.orderSubmit;

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

public class Front_GetOrderInfoByOrderNoGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC交易_下单_获取订单号是否处理完成",timeOut=300000)
	public void getOrderInfoByOrderNo(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("获取cookie");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		String ticket = helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;
		logger.info("cookie:"+cookie);

		logger.info("请求地址");
		String url = null;
		String id = datadriven.get("id");
		if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/shopping/api/getOrderInfoByOrderNo?id="+id;
		}else {
			url = "https://b2b.homedo.com/shopping/api/getOrderInfoByOrderNo?id="+id;

		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookie);
		logger.info("结果校验");
		String respCode = resp.getString("respCode");
		logger.info("respCode = "+respCode);
		Assert.assertEquals(respCode,datadriven.get("respCode"));

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {
	}
	
}		

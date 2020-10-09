package test.pc.trade.payAndResult;

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

public class PayOrderSuccessGetTest extends BaseTest {

	String ticket = null;
	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口-支付成功页-商城-直接返回支付成功页",timeOut=300000)
	public void payOrderSuccess(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");
		String orderId = datadriven.get("orderId");

		ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;
		logger.info("cookie="+cookie);


		if ("uat".equals(env)){
			url = "https://pay.uat.homedo.com/PayOrder/Success?s=2&orderids="+orderId;
		}else if("fat".equals(env)){
			url = "https://pay.fat.homedo.com/PayOrder/Success?s=2&orderids="+orderId;
		}else {
			url ="https://pay.homedo.com/PayOrder/Success?s=2&orderids="+orderId;
		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		String resp = httpClientService.doGetWithCookie(url,cookie);

		logger.info("结果校验");
		boolean hasElement = resp.contains("订单提交成功，我们将尽快为您发货！");
		logger.info("hasElement="+hasElement);
		Assert.assertTrue(hasElement);


		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

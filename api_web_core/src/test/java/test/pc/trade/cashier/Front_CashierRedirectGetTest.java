package test.pc.trade.cashier;

import common.frame.data.ExcelProvider;
import common.frame.test.BaseTest;
import okhttp3.Headers;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Front_CashierRedirectGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

		//获取之前trade.orderSubmit.SettlementAsyncPostTest脚本生成的orderid
//		ISuite suite = Reporter.getCurrentTestResult().getTestContext().getSuite();
//		this.orderids = (String) suite.getAttribute(PC_ORDERID);
//		logger.info("orderId="+orderids);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--中间页跳转收银台接口",timeOut=300000)
	public void cashierRedirect(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("获取用户cookie");
		String ticket = null;
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;
		logger.info("cookie="+cookie);

		logger.info("获取订单编号");
		String addressId = datadriven.get("addressId");
		String invocieId = datadriven.get("invocieId");
		String productId = datadriven.get("productId");
		String count = datadriven.get("count");
		String promotionId = datadriven.get("promotionId");
		String price = datadriven.get("price");

		String orderBizCode = helperService.getOrderBizCode(env,addressId,invocieId,productId,count,promotionId,price,cookie);
//		String orderNo = helperService.getOrderNo(env,orderBizCode,cookie,1000L);
		String orderNo = helperService.getOrderNoByInterval(env,orderBizCode,cookie,10,2000L,1000L);

		logger.info("请求地址");
		String url = null;
		if("fat".equals(env)){
			url = "https://pay2.fat.homedo.com/Cashier/Redirect?orderIds="+orderNo+"&payPlatformType=2&platform=web&os=pc&appVersion=&failOrderNos=";
		}else {
			url = "https://pay2.homedo.com/Cashier/Redirect?orderIds="+orderNo+"&payPlatformType=2&platform=web&os=pc&appVersion=&failOrderNos=";
		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		Headers resp = httpClientService.doGet302HeadersWithCookie(url,cookie);

		logger.info("结果校验");
		String location = resp.get("Location");
		boolean hasUrl = location.contains("cashierDesk");
		logger.info("hasUrl="+hasUrl);
		Assert.assertTrue(hasUrl);

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

package test.pc.trade.cashier;

import com.alibaba.fastjson.JSONObject;
import common.frame.data.ExcelProvider;
import common.frame.test.BaseTest;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueryDeskParamsPostTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

		//获取之前trade.orderSubmit.SettlementAsyncPostTest脚本生成的orderid
//		ISuite suite = Reporter.getCurrentTestResult().getTestContext().getSuite();
//		this.orderId = (String) suite.getAttribute(PC_ORDERID);
//		logger.info("orderId="+orderId);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--支付--查询并返回支付方式",timeOut=300000)
	public void queryDeskParams(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("获取cookie");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		String ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;
		logger.info("cookie="+cookie);

		logger.info("获取payKey和payToken");
		String orderNo = datadriven.get("orderNo");
		HashMap<String,String> params = helperService.getPayKeyAndPayToken(env,orderNo,cookie);
		String payKey = params.get("payKey");
		String payToken = params.get("payToken");

		logger.info("封装数据");
		String data = "{\"payKey\":\""+payKey+"\",\"payToken\":\""+payToken+"\",\"payType\":\"undefined\"}";
		logger.info("data="+data);

		String dataType = "application/json";
		logger.info("dataType="+dataType);

		logger.info("请求地址");
		String url = null;
		if ("uat".equals(env)){
			url="https://scfin-payment-restws.uat.homedo.com/pay/queryDeskParams";
		}else if("fat".equals(env)){
			url="https://scfin-payment-restws.fat.homedo.com/pay/queryDeskParams";
		}else {
			url="https://scfin-payment-restws.homedo.com/pay/queryDeskParams";
		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doPostWithCookieOfJSONObjectNoCache(url,data,dataType,cookie);

		logger.info("结果校验");
		String respDesc = resp.getString("respDesc");
		logger.info("respDesc="+respDesc);
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

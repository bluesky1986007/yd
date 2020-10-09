package test.pc.trade.payAndResult;

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

public class PaySyncBackUnionPayHandleGetTest extends BaseTest {

	String url = null;
	String payToken = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

		//获取支付提交时候的token信息，来自于脚本PaySubmitPostTest
		ISuite suite = Reporter.getCurrentTestResult().getTestContext().getSuite();
		payToken = (String) suite.getAttribute(PC_PAY_TOKEN);
		logger.info("payToken="+payToken);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口-收银台-支付-查询支付结果",timeOut=300000)
	public void paySyncBackUnionPayHandle(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		if ("uat".equals(env)){
			url = "https://scfin-payment-restws.uat.homedo.com/paySyncBack/unionPayHandle?code=2&payToken="+payToken;
		}else if("fat".equals(env)){
			url = "https://scfin-payment-restws.fat.homedo.com/paySyncBack/unionPayHandle?code=2&payToken="+payToken;
		}else {
			url ="https://scfin-payment-restws.homedo.com/paySyncBack/unionPayHandle?code=2&payToken="+payToken;
		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		Headers resp = httpClientService.doGet302HeadersWithCookie(url,"");

		logger.info("结果校验");
		String location = resp.get("Location");
		boolean hasElement = location.contains("FrontReturnCallback");
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

package test.pc.trade.payAndResult;

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

public class CashierFrontReturnCallbackGetTest extends BaseTest {

	String ticket = null;
	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口-收银台-商城-查询结束跳中间页",timeOut=300000)
	public void cashierFrontReturnCallback(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;
		logger.info("cookie="+cookie);


		if ("uat".equals(env)){
			url = "https://pay.uat.homedo.com/Cashier/FrontReturnCallback?payBillNo=PAY1196708739315400753&businessMergeTradeNo=519340&orderIds=100932157&code=2&message=PROCESSING&businessReqReserved=b2b";
		}else if("fat".equals(env)){
			url = "https://pay.fat.homedo.com/Cashier/FrontReturnCallback?payBillNo=PAY1196706595212361804&businessMergeTradeNo=610399&orderIds=101095946&code=2&message=PROCESSING&businessReqReserved=b2b";
		}else {
			url ="https://pay.homedo.com/Cashier/FrontReturnCallback?payBillNo=PAY1196684678455627849&businessMergeTradeNo=2085514&orderIds=102235995&code=2&message=PROCESSING&businessReqReserved=b2b";
		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		Headers resp = httpClientService.doGet302HeadersWithCookie(url,cookie);

		logger.info("结果校验");
		String location = resp.get("Location");
		boolean hasUrl = location.contains("OrderPayOrSubmitSuccess");
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

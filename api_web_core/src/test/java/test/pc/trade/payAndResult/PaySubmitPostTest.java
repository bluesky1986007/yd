package test.pc.trade.payAndResult;

import com.alibaba.fastjson.JSONObject;
import common.frame.data.ExcelProvider;
import common.frame.test.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.pc.trade.orderSubmit.Front_OrderSubmitPostTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PaySubmitPostTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--支付提交及结果页--线上(线下)支付提交",timeOut=300000)
	public void paySubmit(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);
//		String payType=null;
		String payTypeOfdataProvider = datadriven.get("payType");
//		switch (payTypeOfdataProvider){
//			case "线下支付":
//				payType = "offlinePayRequest";
//				break;
//			case "线上支付":
//				payType = "";
//
//		}

		logger.info("支付方式为--"+payTypeOfdataProvider);
		logger.info("请求地址");
		String url = null;
		if ("uat".equals(env)){
			url="https://scfin-payment-restws.uat.homedo.com/pay/submit";
		}else if("fat".equals(env)){
			url="https://scfin-payment-restws.fat.homedo.com/pay/submit";
		}else {
			url="https://scfin-payment-restws.homedo.com/pay/submit";
		}

		logger.info("url="+url);

		logger.info("获取cookie");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		String ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;
		logger.info("cookie="+cookie);

		logger.info("开始封装数据");
		String addressId = datadriven.get("addressId");
		String invocieId = datadriven.get("invocieId");
		String productId = datadriven.get("productId");
		String count = datadriven.get("count");
		String promotionId = datadriven.get("promotionId");
		String price = datadriven.get("price");

//		String dataId = helperService.getOrderDataId(env,addressId,productId,count,promotionId,cookie);
//		String orderId = helperService.getOrderId(env,dataId,cookie,1000L);
//		String orderAmount = helperService.getOrderAmount(env,orderId,cookie);

		String orderBizCode = helperService.getOrderBizCode(env,addressId,invocieId,productId,count,promotionId,price,cookie);
//		String orderNo = helperService.getOrderNo(env,orderBizCode,cookie,1000L);
		String orderNo = helperService.getOrderNoByInterval(env,orderBizCode,cookie,10,2000L,1000L);
		String orderAmount = helperService.getOrderRealAmount(env,orderNo,cookie);

		HashMap<String,String> params = helperService.getPayKeyAndPayToken(env,orderNo,cookie);
		String payKey = params.get("payKey");
		String payToken = params.get("payToken");
		String bankId = datadriven.get("bankId");

		String data = null;
		if ("线下支付".equals(payTypeOfdataProvider)){//线下支付

			data = "{\"payToken\":\""+payToken+"\",\"payKey\":\""+payKey+"\",\"unionPayRequest\":\"\",\"hbPayRequest\":\"\",\"unionGetwayPayRequest\":\"\",\"unionGetwayToBPayRequest\":\"\",\"alipayTradeRequest\":\"\",\"wxPayRequest\":\"\",\"yfhPayRequest\":\"\",\"offlinePayRequest\":{\"remittingCompany\":\"luyi autotest\",\"remittingBank\":\"621111122223333\",\"remittingSubBranch\":\"021\",\"remittingBankNo\":\"021\",\"remittingAmount\":\""+orderAmount+"\",\"remittingBankTradeNo\":\"021\",\"remark\":\"\",\"collectionBankId\":"+bankId+"}}";

		}else if("线上支付".equals(payTypeOfdataProvider)){

			data = "{\"payToken\":\""+payToken+"\",\"payKey\":\""+payKey+"\",\"unionPayRequest\":\"\",\"hbPayRequest\":\"\",\"unionGetwayPayRequest\":\"\",\"unionGetwayToBPayRequest\":\"\",\"alipayTradeRequest\":{\"payAmount\":"+orderAmount+",\"callBackUrl\":\"https://pay.homedo.com/Cashier/FrontReturnCallback\"},\"wxPayRequest\":\"\",\"yfhPayRequest\":\"\",\"eblinkPayRequest\":\"\"}";

		}

		logger.info("data="+data);

		String dataType = "application/json";
		logger.info("dataType="+dataType);

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

		//payToken参数需要传入脚本PaySyncBackUnionPayHandleGetTest进行使用
//		ISuite suite = Reporter.getCurrentTestResult().getTestContext().getSuite();
//		suite.setAttribute(PC_PAY_TOKEN,payToken);


	}
	
}		

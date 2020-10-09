package test.pc.trade.cashier;

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

public class PayRemoteApplyPostTest extends BaseTest {

	String ticket = null;
	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}

	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--支付--返回收银台url",timeOut=300000)
	public void payRemoteApply(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String url=null;
		if ("uat".equals(env)){
			url = "https://scfin-payment-restws.uat.homedo.com/scfin-payment-restws/payRemote/apply";
		}else if("fat".equals(env)){
			url = "https://scfin-payment-restws.fat.homedo.com/scfin-payment-restws/payRemote/apply";
		}else {
			url ="https://scfin-payment-restws.homedo.com/scfin-payment-restws/payRemote/apply";
		}

		logger.info("url="+url);

		String data = "{\"uId\":\"163006\",\"uName\":\"xinlang123\",\"appId\":\"SHOPPING\",\"payDesc\":\"\",\"payCurrency\":\"CNY\",\"billOrderRequest\":" +
				"{\"businessMergeTradeNo\":\"737032\",\"businessMergeTradeDesc\":\"商城订单\",\"businessReqReserved\":\"b2b\",\"frontReturnUrl\":" +
				"\"https://pay.homedo.com/Cashier/FrontReturnCallback\",\"businessMergeOrderTotalAmount\":3316.00,\"businessMergeHbDeductionAmount\":0.0," +
				"\"businessMergeOrderAmount\":3316.00,\"orderPayWay\":0,\"orderPayDuration\":2,\"orderPayRedPacketFlag\":null,\"cyclePayDeadline\":null," +
				"\"advancePayStartTime\":null,\"advancePayEndTime\":null,\"businessOrderFlowRequestList\":[{\"businessOrderNo\":\"101690111\"," +
				"\"businessOrderType\":1,\"businessOrderSubject\":\"\",\"businessOrderDesc\":\"\",\"businessOrderDetailUrl\":" +
				"\"//center.homedo.com/Order/Detail/101690111\",\"businessOrderAmount\":3316.00,\"hbDeductionAamount\":0.0,\"businessOrderTotalAmount" +
				"\":3316.00,\"businessGoodsDetail\":null}],\"deskTitleWords\":\"<dl class=\\\"infor-bd cashier-infor\\\">" +
				"<dt class=\\\"success-icon PlaceOrder-icon\\\"></dt><dd><div class=\\\"cashier-text fl\\\"><p class=\\\"text-infor\\\">" +
				"<strong>订单提交成功，请尽快完成支付！</strong></p><small><em>2个工作日</em>内未完成支付的，订单将被系统自动取消。</small>" +
				"</div></dd></dl>\",\"promotionType\":2},\"timestamp\":637063863827387181,\"sign\":\"70a7179d8814b0d3cc87b6d087178594\"," +
				"\"clientInfo\":{\"platform\":\"web\",\"appVersion\":null,\"os\":\"pc\"}}";

//		logger.info("data="+data);

		String dataType = "application/json";
//		logger.info("dataType="+dataType);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doPostOfJSONObject(url,data,dataType);

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

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

public class Front_OrderSubmitPostTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC交易_下单_提交订单",timeOut=300000)
	public void orderSubmit(Map<String, String> datadriven)throws Exception {

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
		if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/shopping/api/orderSubmit";
		}else {
			url = "https://b2b.homedo.com/shopping/api/orderSubmit";

		}
		logger.info("url="+url);

		logger.info("封装数据");
		String addressId = datadriven.get("addressId");
		String invoiceId = datadriven.get("invoiceId");
		String count = datadriven.get("count");
		String productId = datadriven.get("productId");
		String promotionId = datadriven.get("promotionId");
		String price = datadriven.get("price");
		String data = "{\"addressId\":\""+addressId+"\",\"dataId\":\"\",\"deliveryDate\":\"\",\"invoiceId\":\""+invoiceId+"\",\"necaptchaValidate\":\"\",\"orderRemark\":\"立即发货\",\"paymentMethod\":0,\"productData\":{\"addressId\":\""+addressId+"\",\"couponFreightItemId\":null,\"couponsData\":[],\"integral\":0,\"isWaitDeliver\":false,\"pdRuleId\":0,\"productData\":[{\"count\":\""+count+"\",\"productId\":\""+productId+"\",\"promotionId\":\""+promotionId+"\",\"price\":"+price+",\"priceMinus\":0,\"hebiScale\":0,\"isNeiCai\":false,\"productIntegralPresentScale\":0,\"productIsLianYing\":0,\"promotionCanBuy\":1,\"promotionIntegralPresentScale\":0,\"promotionIsCanUseIntegral\":false,\"promotionIsCanUseRed\":false,\"promotionUseRedType\":2,\"promotionMoreThanMoney\":0,\"promotionSubtractMoney\":0,\"promotionType\":65536,\"isQuDao\":false,\"shopId\":\"0\",\"isUsedCycle\":true,\"promotionSubtractType\":0,\"deposit\":0,\"maiZengList\":[]}],\"packageData\":[]},\"zbData\":null,\"cycle\":null,\"shopProductListMapAReq\":null}";
		String dataType = "application/json";

		logger.info("data:"+data);
		logger.info("dataType:"+dataType);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doPostWithCookieOfJSONObject(url,data,dataType,cookie);

		logger.info("结果校验");
		String respDesc = resp.getString("respDesc");
		logger.info("respDesc = "+respDesc);
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

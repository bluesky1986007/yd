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

public class Front_GetCacheGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC交易_下单_获取缓存中的基础商品信息",timeOut=300000)
	public void getCache(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("获取cookie");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		String ticket = helperService.getTicketOfPCLogin(env,name,pwd);
		logger.info("ticket="+ticket);

		logger.info("将商品信息放入缓存获取");
		String submitUrl = null;
		if("fat".equals(env)){
			submitUrl = "https://b2b.fat.homedo.com/mall-pcweb-compositeservice/shopping/submitSettlementRedis?key="+ticket;
		}else {
			submitUrl = "https://b2b.homedo.com/mall-pcweb-compositeservice/shopping/submitSettlementRedis?key="+ticket;

		}

		logger.info("submitUrl="+submitUrl);

		String productId = datadriven.get("productId");
		String productName = datadriven.get("productName");
		String productCount = datadriven.get("productCount");
		String data = "{\"products\":[{\"promotionId\":\"0\",\"productId\":\""+productId+"\",\"name\":\""+productName+"\",\"count\":\""+productCount+"\"}],\"pinDanRuleId\":0}";
		String dataType = "application/json";

		logger.info("data:"+data);
		logger.info("dataType:"+dataType);

		JSONObject respOfsubmitRedis = httpClientService.doPostOfJSONObject(submitUrl,data,dataType);

		JSONObject resultData = respOfsubmitRedis.getJSONObject("data");
		String HMDS = resultData.getString("key");
		logger.info("HMD_S:"+HMDS);

		logger.info("封装数据");
		String cookieValue ="ticket="+ticket+";HMD_S="+HMDS;

		logger.info("请求地址");
		String url = null;
		if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/shopping/api/getCache";
		}else {
			url = "https://b2b.homedo.com/shopping/api/getCache";

		}
		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookieValue);
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

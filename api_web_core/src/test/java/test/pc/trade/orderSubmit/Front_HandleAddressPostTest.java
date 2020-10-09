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

public class Front_HandleAddressPostTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC交易_下单_收货地址增删改",timeOut=300000)
	public void handleAddress(Map<String, String> datadriven)throws Exception {

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
			url = "https://b2b.fat.homedo.com/shopping/api/handleAddress";
		}else {
			url = "https://b2b.homedo.com/shopping/api/handleAddress";

		}
		logger.info("url="+url);

		logger.info("封装数据");
		String type = datadriven.get("type");
		String accountId = datadriven.get("accountId");
		String data = "{\"type\":\""+type+"\",\"data\":\"{\\\"accountId\\\":\\\""+accountId+"\\\",\\\"address\\\":\\\"备选测试收获地址001\\\",\\\"cityId\\\":\\\"109\\\",\\\"cityName\\\":\\\"上海\\\",\\\"company\\\":\\\"\\\",\\\"districtId\\\":\\\"1174\\\",\\\"districtName\\\":\\\"浦东新区\\\",\\\"email\\\":\\\"\\\",\\\"id\\\":\\\"251391\\\",\\\"identiy\\\":\\\"上海 上海 浦东新区\\\",\\\"insertTime\\\":1595385585397,\\\"isDefault\\\":false,\\\"mobile\\\":\\\"13112345677\\\",\\\"postcode\\\":\\\"\\\",\\\"provinceId\\\":\\\"9\\\",\\\"provinceName\\\":\\\"上海\\\",\\\"recipient\\\":\\\"luyi\\\",\\\"remoteLevel\\\":\\\"1\\\",\\\"tag\\\":\\\"\\\",\\\"telephone\\\":\\\"\\\",\\\"updateTime\\\":1595385585397,\\\"mostRecently\\\":false}\"}";
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

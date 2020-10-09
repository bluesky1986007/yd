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

public class Front_ZbServiceAddressPostTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC交易_下单_服务地址增删改",timeOut=300000)
	public void zbServiceAddress(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

        logger.info("获取cookie");
        String name = datadriven.get("name");
        String pwd = datadriven.get("pwd");

        String ticket = helperService.getTicketOfPCLogin(env,name,pwd);
        String cookie = "ticket="+ticket;
        logger.info("cookie::"+cookie);

		logger.info("请求地址");
		String url = null;
		if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/shopping/api/zbServiceAddress";
		}else {
			url = "https://b2b.homedo.com/shopping/api/zbServiceAddress";

		}
		logger.info("url="+url);

		logger.info("封装数据");
		String type = datadriven.get("type");
		String id = datadriven.get("id");
		String data = "{\"type\":\""+type+"\",\"key\":\""+ticket+"\",\"data\":\"{\\\"id\\\":"+id+",\\\"linkman\\\":\\\"陆祎\\\",\\\"mobile\\\":\\\"13112345678\\\",\\\"provinceid\\\":9,\\\"cityid\\\":109,\\\"districtid\\\":1174,\\\"streetid\\\":16246,\\\"address\\\":\\\"自动化测试地址\\\",\\\"servicetime\\\":\\\"2020-07-23 00:00:00\\\"}\"}";
		String dataType = "application/json";

		logger.info("data:"+data);
		logger.info("dataType:"+dataType);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doPostWithCookieOfJSONObject(url,data,dataType,cookie);

		logger.info("结果校验");
		String respCode = resp.getString("respCode");
		logger.info("respCode = "+respCode);
		Assert.assertEquals(respCode,datadriven.get("respCode"));

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {
	}
	
}		

package test.pc.trade.itemDetails;

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

public class GetNewMzInfoByProductIdGetTest extends BaseTest {

	String ticket = null;
	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

		//获取之前添加会员用例中的name和mobile数据
//		ISuite suite = Reporter.getCurrentTestResult().getTestContext().getSuite();
//		ticket = (String) suite.getAttribute(PC_TICKET);
//		ticket = "d9103102454040ada640f81ae3380cf1-371506-login";

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--商详页抽奖接口",timeOut=300000)
	public void getNewMzInfoByProductId(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");
		String productIds = datadriven.get("productIds");
		ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		logger.info("ticket="+ticket);
		if ("uat".equals(env)){
			url = "https://japi.uat.homedo.com/activity/prize/mz/getNewMzInfoByProductId?platform=b2b&Identification=&version=1.3.3&key="+ticket+"&productIds="+productIds+"&type=2";
		}else if("fat".equals(env)){
			url = "https://japi.fat.homedo.com/activity/prize/mz/getNewMzInfoByProductId?platform=b2b&Identification=&version=1.3.3&key="+ticket+"&productIds="+productIds+"&type=2";
		}else {
			url = "https://japi.homedo.com/activity/prize/mz/getNewMzInfoByProductId?platform=b2b&Identification=&version=1.3.3&key="+ticket+"&productIds="+productIds+"&type=1";

		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String code = resp.getString("code");
		logger.info("code="+code);
		Assert.assertEquals(code,datadriven.get("code"));

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

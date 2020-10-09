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

public class GetFooterHtmlSourceCodeGetTest extends BaseTest {
	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--获取收银台底部html",timeOut=300000)
	public void getFooterHtmlSourceCode(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String url=null;
		if ("uat".equals(env)){
			url = "https://japi.uat.homedo.com/activity/commonPC/getFooterHtmlSourceCode";
		}else if("fat".equals(env)){
			url = "https://japi.fat.homedo.com/activity/commonPC/getFooterHtmlSourceCode";
		}else {
			url ="https://japi.homedo.com/activity/commonPC/getFooterHtmlSourceCode";
		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String msg = resp.getString("msg");
		logger.info("msg="+msg);
		Assert.assertEquals(msg,datadriven.get("msg"));

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

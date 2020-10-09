package test.common;

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

public class GetZBDirectlyMiniShopCartGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--众包服务接口",timeOut=300000)
	public void getZBDirectlyMiniShopCart(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);
		String url=null;
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		String key = helperService.getTicketOfPCLogin(env,name,pwd);

		if ("uat".equals(env)){

			url = "https://japi.uat.homedo.com/product/zb/directly/getZBDirectlyMiniShopCart?platform=b2b&Identification=&version=&imageSize=s&key="+key;

		}else if("fat".equals(env)){
			url = "https://japi.fat.homedo.com/product/zb/directly/getZBDirectlyMiniShopCart?platform=b2b&Identification=&version=&imageSize=s&key="+key;

		}else {
            //生产环境
			url = "https://japi.homedo.com/product/zb/directly/getZBDirectlyMiniShopCart?platform=b2b&Identification=&version=&imageSize=s&key="+key;
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String msg = resp.getString("msg");
		String code = resp.getString("code");
		System.out.println("msg = "+msg);
		System.out.println("code = "+code);

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

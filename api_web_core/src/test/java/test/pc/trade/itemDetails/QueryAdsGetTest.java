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

public class QueryAdsGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P2_PC接口_查询广告位信息",timeOut=300000)
	public void queryAdsGetTest(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);
		String url=null;
		String ids = datadriven.get("ids");

		if ("uat".equals(env)){

			url = "https://framework-cms-restws.uat.homedo.com/framework-cms-restws/cms/queryads?ids="+ids;

		}else if("fat".equals(env)){
			url = "https://framework-cms-restws.fat.homedo.com/framework-cms-restws/cms/queryads?ids="+ids;

		}else {
            //生产环境
			url = "https://framework-cms-restws.homedo.com/framework-cms-restws/cms/queryads?ids="+ids;
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

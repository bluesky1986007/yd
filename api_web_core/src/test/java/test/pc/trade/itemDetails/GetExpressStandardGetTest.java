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

public class GetExpressStandardGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--获取运费信息",timeOut=300000)
	public void getExpressStandard(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);
		String url=null;
		String productIdCounts = datadriven.get("productIdCounts");

		if ("uat".equals(env)){

			url = "https://api.uat.homedo.com/Express/ExpressCalculate/GetExpressStandard?model.platform=b2b&model.productIdCounts="+productIdCounts;

		}else if("fat".equals(env)){
			url = "https://api.fat.homedo.com/Express/ExpressCalculate/GetExpressStandard?model.platform=b2b&model.productIdCounts="+productIdCounts;

		}else {
            //生产环境
			url = "https://api.homedo.com/Express/ExpressCalculate/GetExpressStandard?model.platform=b2b&model.productIdCounts="+productIdCounts;
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String isOK = resp.getString("isOK");
		System.out.println("isOK = "+isOK);
		Assert.assertEquals(isOK,datadriven.get("isOK"));


		logger.info("--------------接口测试结束------------------------");


	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

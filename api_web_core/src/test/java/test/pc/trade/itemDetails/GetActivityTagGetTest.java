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

public class GetActivityTagGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口-商详页-商城-获取促销标签",timeOut=300000)
	public void getActivityTag(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);
		String url=null;
		String promotionId = datadriven.get("promotionId");

		if ("uat".equals(env)){

			url = "https://japi.uat.homedo.com/product/promotion/getActivityTag?platform=B2B&promotionId="+promotionId;

		}else if("fat".equals(env)){
			url = "https://japi.fat.homedo.com/product/promotion/getActivityTag?platform=B2B&promotionId="+promotionId;

		}else {
            //生产环境
			url = "https://japi.homedo.com/product/promotion/getActivityTag?platform=B2B&promotionId="+promotionId;
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String msg = resp.getString("msg");
		System.out.println("msg = "+msg);
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

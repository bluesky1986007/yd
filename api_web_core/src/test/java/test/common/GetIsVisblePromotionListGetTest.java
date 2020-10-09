package test.common;

import com.alibaba.fastjson.JSONArray;
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

public class GetIsVisblePromotionListGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--促销信息",timeOut=300000)
	public void getIsVisblePromotionList(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("请求地址&封装数据");
		logger.info("env="+env);
		String url=null;
		String promotionid = datadriven.get("PromotionId");

		if ("uat".equals(env)){
			url = "https://api.uat.homedo.com/Product/GetIsVisblePromotionList?promotionIds="+promotionid;
		}else if("pro".equals(env)){
			url = "https://api.homedo.com/Product/GetIsVisblePromotionList?promotionIds="+promotionid;
		}else if("fat".equals(env)){
			url = "https://api.fat.homedo.com/Product/GetIsVisblePromotionList?promotionIds="+promotionid;

		}

		logger.info("url="+url);
		logger.info("promotionid="+promotionid);

		logger.info("发送请求并获得response");
		JSONArray resp = httpClientService.doGetOfJSONArray(url);

		logger.info("结果校验");
		for (int i = 0; i < resp.size() ; i++) {

			JSONObject jasonValue = resp.getJSONObject(i);
			String resultOfpromotionid = jasonValue.get("PromotionId").toString();
			System.out.println("result PromotionId = "+resultOfpromotionid);
			Assert.assertEquals(resultOfpromotionid,promotionid);
				
		}

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}
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

public class GetStorageTransitTimeGetTest extends BaseTest {

	String url=null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC接口_商详页_获取运输时间",timeOut=300000)
	public void getStorageTransitTimeGetTest(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);

		String id = datadriven.get("id");
		String parentId = datadriven.get("parentId");
		String freightId = datadriven.get("freightId");

		if ("uat".equals(env)){

			url = "https://b2b.homedo.com/Goods/GetStorageTransitTime?id="+id+"&parentId="+parentId+"&freightId="+freightId;

		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/Goods/GetStorageTransitTime?id="+id+"&parentId="+parentId+"&freightId="+freightId;

		}else {
            //生产环境
			url = "https://b2b.homedo.com/Goods/GetStorageTransitTime?id="+id+"&parentId="+parentId+"&freightId="+freightId;
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String data = resp.getString("data");
		Assert.assertTrue(data.contains("Date"));


		logger.info("--------------接口测试结束------------------------");


	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

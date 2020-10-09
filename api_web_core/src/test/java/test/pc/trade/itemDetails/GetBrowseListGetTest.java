package test.pc.trade.itemDetails;

import com.alibaba.fastjson.JSONArray;
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

public class GetBrowseListGetTest extends BaseTest {

	String url=null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC接口_商详页_浏览记录",timeOut=300000)
	public void getBrowseList(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);

		String accountId = datadriven.get("accountId");

		if ("uat".equals(env)){

			url = "https://b2b.uat.homedo.com/Goods/GetBrowseList?AccountId="+accountId;

		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/Goods/GetBrowseList?AccountId="+accountId;

		}else {
            //生产环境
			url = "https://b2b.homedo.com/Goods/GetBrowseList?AccountId="+accountId;
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONArray resp = httpClientService.doGetOfJSONArray(url);

		logger.info("结果校验");
		int count = resp.size();
		logger.info("browseList count="+count);
		Assert.assertNotSame(count,0);


		logger.info("--------------接口测试结束------------------------");


	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

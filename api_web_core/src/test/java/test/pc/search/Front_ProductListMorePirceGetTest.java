package test.pc.search;

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

public class Front_ProductListMorePirceGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC前端接口--搜索结果页1带多码",timeOut=300000)
	public void productListMorePirce(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);
		String url=null;
		String productId = datadriven.get("productId");

		if ("uat".equals(env)){

			url = "https://b2b.uat.homedo.com/mall-pcweb-compositeservice/ProductList/morePirce?id="+productId;

		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/mall-pcweb-compositeservice/ProductList/morePirce?id="+productId;

		}else {
            //生产环境
			url = "https://b2b.homedo.com/mall-pcweb-compositeservice/ProductList/morePirce?id="+productId;
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String respDesc = resp.getString("respDesc");
		logger.info("respDesc = "+respDesc);
		Assert.assertEquals(respDesc,datadriven.get("respDesc"));


		logger.info("--------------接口测试结束------------------------");


	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

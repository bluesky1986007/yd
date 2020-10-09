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

public class GetZBProductDetailGetTest extends BaseTest {

	String url=null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P2_PC接口_获取众包产品信息",timeOut=300000)
	public void getZBProductDetail(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);
		String productId = datadriven.get("productId");
		logger.info("productId="+productId);

		if ("uat".equals(env)){

			url = "https://japi.uat.homedo.com/product/zb/getZBProductDetail?productId="+productId;

		}else if("fat".equals(env)){
			url = "https://japi.fat.homedo.com/product/zb/getZBProductDetail?productId="+productId;

		}else {
            //生产环境
			url = "https://japi.homedo.com/product/zb/getZBProductDetail?productId="+productId;
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String msg = resp.getString("msg");
		logger.info("msg="+msg);
		if ("服务为空".equals(msg)){
			Assert.assertEquals(msg,"服务为空");

		}else{

			Assert.assertEquals(msg,"成功");
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

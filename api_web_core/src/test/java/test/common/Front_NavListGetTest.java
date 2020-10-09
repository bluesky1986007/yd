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

public class Front_NavListGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC_公共_前端接口_左侧导航栏",timeOut=300000)
	public void navList(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);
		String url=null;

		if ("fat".equals(env)){

			url = "https://b2b.fat.homedo.com/mall-pcweb-compositeservice/act/leftNav/navList";

		}else {
            //生产环境
			url = "https://b2b.homedo.com/mall-pcweb-compositeservice/act/leftNav/navList";
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetOfJSONObject(url);

		logger.info("结果校验");
		String title = resp.getString("title");
		logger.info("title = "+title);
		boolean hasPic = title.contains("image01.homedo.com");
		logger.info("hasPic = "+hasPic);

		Assert.assertTrue(hasPic);


		logger.info("--------------接口测试结束------------------------");


	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
        return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

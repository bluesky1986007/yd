package test.pc.trade.itemDetails;

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

public class GetDetailTaoCanPostTest extends BaseTest {

	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P2_PC接口_商详页_获取套餐html",timeOut=300000)
	public void getDetailTaoCan(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String id = datadriven.get("Id");
		String categoryId3 = datadriven.get("categoryId3");

		if ("uat".equals(env)){
			url="https://b2b.uat.homedo.com/goods/GetDetailTaoCan";
		}else if("fat".equals(env)){
			url="https://b2b.fat.homedo.com/goods/GetDetailTaoCan";
		}else {
			url="https://b2b.homedo.com/goods/GetDetailTaoCan";
		}

		logger.info("url="+url);

		String data = "Id="+id+"&categoryId3="+categoryId3;
		logger.info("data="+data);

		String dataType = "application/x-www-form-urlencoded";
		logger.info("dataType="+dataType);

		logger.info("发送请求并获得response");
		String resp = httpClientService.doPost(url,data,dataType);

		logger.info("结果校验");
		String expectElement = datadriven.get("expectElement");
		boolean isElement=resp.contains(expectElement);
		logger.info("是否有预期元素存在="+isElement);
		Assert.assertTrue(isElement);


		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

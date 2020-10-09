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

public class Front_GetListHotProductsPostTest extends BaseTest {

	String ticket = null;
	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC-前端接口--新推荐接口",timeOut=300000)
	public void getListHotProducts(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String data = null;
		String skuIds = datadriven.get("skuIds");
		String slotSize = datadriven.get("slotSize");

		if ("uat".equals(env)){
			url="https://b2b.uat.homedo.com/mall-pcweb-compositeservice/GetListHotProducts";
			data = "{\"skuIds\":[\""+skuIds+"\"],\"thirdCategoryIds\":[\"11231\"],\"brandIds\":[\"1159\"],\"targetId\":\"301201\",\"slotSize\":"+slotSize+"}";

		}  if("fat".equals(env)){
			url="https://b2b.fat.homedo.com/mall-pcweb-compositeservice/GetListHotProducts";
			data = "{\"skuIds\":[\""+skuIds+"\"],\"targetId\":\"301202\",\"slotSize\":"+slotSize+"}";
		}else {
			url="https://b2b.homedo.com/mall-pcweb-compositeservice/GetListHotProducts";
			data = "{\"skuIds\":[\""+skuIds+"\"],\"thirdCategoryIds\":[\"10085\"],\"brandIds\":[\"2175\"],\"targetId\":\"301201\",\"slotSize\":"+slotSize+"}";
		}

		logger.info("url="+url);
		logger.info("data="+data);

		String dataType = "application/json";
		logger.info("dataType="+dataType);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doPostOfJSONObject(url,data,dataType);

		logger.info("结果校验");
		String respDesc = resp.getString("respDesc");
		logger.info("respDesc="+respDesc);
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

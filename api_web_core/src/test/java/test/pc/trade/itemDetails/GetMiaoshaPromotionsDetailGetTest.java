package test.pc.trade.itemDetails;

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

public class GetMiaoshaPromotionsDetailGetTest extends BaseTest {

	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC交易_商详页_秒杀商品详情页返回商品信息",timeOut=300000)
	public void getMiaoshaPromotionsDetail(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String productIds = datadriven.get("productIds");
		String promotionIds = datadriven.get("promotionIds");

		if("fat".equals(env)){
			url = "https://api.fat.homedo.com/Product/GetMiaoshaPromotionsDetail_json?"+"PromotionIds="+promotionIds+"&ProductIds="+productIds+"&platform=b2b"
					+"&url="+"https://www.homedo.com/SpikeContent.shtml?PromotionId="+promotionIds+"_ProductIds="+productIds;

		}else {
			url = "https://api.homedo.com/Product/GetMiaoshaPromotionsDetail_json?"+"PromotionIds="+promotionIds+"&ProductIds="+productIds+"&platform=b2b"
					+"&url="+"https://www.homedo.com/SpikeContent.shtml?PromotionId="+promotionIds+"_ProductIds="+productIds;

		}

		logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONArray resp = httpClientService.doGetOfJSONArray(url);

		logger.info("结果校验");
		String productIdOfResult = resp.getJSONObject(0).get("ProductId").toString();
		String PromotionIdOfResult = resp.getJSONObject(0).get("PromotionId").toString();
		logger.info("productIdOfResult="+productIdOfResult+";"+"PromotionIdOfResult="+PromotionIdOfResult);
		Assert.assertEquals(productIdOfResult,datadriven.get("productIds"));
		Assert.assertEquals(PromotionIdOfResult,datadriven.get("promotionIds"));

		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

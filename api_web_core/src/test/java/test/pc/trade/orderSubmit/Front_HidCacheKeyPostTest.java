package test.pc.trade.orderSubmit;

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

public class Front_HidCacheKeyPostTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC交易_下单_秒杀商品163滑动二次验证",timeOut=300000)
	public void hidCacheKey(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

        logger.info("获取cookie");
        String name = datadriven.get("name");
        String pwd = datadriven.get("pwd");

        String ticket = helperService.getTicketOfPCLogin(env,name,pwd);
        String cookie = "ticket="+ticket;
        logger.info("cookie:"+cookie);

		logger.info("请求地址");
		String url = null;
		if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/shopping/api/hidCacheKey";
		}else {
			url = "https://b2b.homedo.com/shopping/api/hidCacheKey";

		}
		logger.info("url="+url);

		logger.info("封装数据");
		String data = "{\"captchaValidate\":\"X49Z_s_pNUBaaxNv01CgVPBUSRgnwN6Tkh_KGEtxQkhpdGGRrx0bxSe8hg7YnziGzSlUw.tZORa05bkuhcTRpGzLOv7mWLNkRJvcOdZA77C7EWS_Rh_ZczYarIzSXAGn2e2HVNmHtEfcp8k4hCaAhc-YyLaZsaOyEXkDOOQnOQAPj9BPGPqraCR8YIIeCTWCOe0cPMgVbdg4WuR7GF1r8q-qvEygweHsv1IBe.-Phypg5mR.U2YWAgi6eagW6ZdLQYVJeANsqaBTHNSrGaxfVjmoeXyUIXuu4cwlNSLpcRvu1_QuTXsKwN8qhw9rZJiJlYushyOYhttxszHvcuxAdOQ8Ppys2fCoe01jvnOzpVeNfO2qQaaWlbPxAKfX-FvneN6soE5dKhm42HPbCetgvvjYahS_1p22wtkaeaiknJVE6Fv_STUDSskUiwceNJN6urwk7Oefw5MNvRBpqhXNuN7VFojvgbluEma6VEulaJfe6sma_5qnWC4b19A3\"}";
		String dataType = "application/json";

		logger.info("data:"+data);
		logger.info("dataType:"+dataType);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doPostWithCookieOfJSONObject(url,data,dataType,cookie);

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

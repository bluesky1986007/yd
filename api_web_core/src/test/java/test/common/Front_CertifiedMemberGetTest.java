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

public class Front_CertifiedMemberGetTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);
		
	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC-前端接口--获取认证会员的认证时间",timeOut=300000)
	public void certifiedMembershipBenefits(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("封装数据");
		logger.info("env="+env);
		String url=null;
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		String ticket = helperService.getTicketOfPCLogin(env,name,pwd);
		String cookie = "ticket="+ticket;
		logger.info("cookie="+cookie);

		if ("uat".equals(env)){

			url = "https://b2b.uat.homedo.com/mall-pcweb-compositeservice/act/certifiedMembershipBenefits";

		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/mall-pcweb-compositeservice/act/certifiedMembershipBenefits";

		}else {
            //生产环境
			url = "https://b2b.homedo.com/mall-pcweb-compositeservice/act/certifiedMembershipBenefits";
		}

        logger.info("url="+url);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookie);

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

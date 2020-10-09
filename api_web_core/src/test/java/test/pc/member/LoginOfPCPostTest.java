package test.pc.member;

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

public class LoginOfPCPostTest extends BaseTest {

	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="PC接口--网站会员登录",timeOut=300000)
	public void loginOfPC(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");

		if ("uat".equals(env)){

			url="https://passport.uat.homedo.com/Account/Login";

		}else if("fat".equals(env)){

			url="https://passport.fat.homedo.com/Account/Login";

		}else if("pro".equals(env)){

			url="https://passport.homedo.com/Account/Login";
		}
		else{

			logger.info("未正确获取到测试环境信息，导致请求url为null，无法获取到ticket值！");
		}

		String dataType="application/x-www-form-urlencoded";
		String data = "Name="+name+"&Password="+pwd+"&Code=&IsRemember=true&Url=";


		logger.info("url="+url);
		logger.info("data="+data);


		JSONObject resp = httpClientService.doPostOfJSONObject(url,data,dataType);

		logger.info("结果校验");
		String Name = resp.getString("Name");
		logger.info("Name="+Name);
		Assert.assertEquals(Name,datadriven.get("Name"));


		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

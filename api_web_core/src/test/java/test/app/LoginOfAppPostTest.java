package test.app;

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

public class LoginOfAppPostTest extends BaseTest {

	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfPRO;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="APP接口--移动会员登录",timeOut=300000)
	public void loginOfApp(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");
		logger.info("name="+name);
		logger.info("pwd="+pwd);

		String linkEnv=null;
		if ("uat".equals(env)){
			url = "https://apim.open.uat.homedo.com/member/member/subLogIn?key=2981eabb351143bc80b911f0b757d7ed&version=1.83&platform=mobile1.2.5&Identification=864394010640011";


		}else if("fat".equals(env)){
			url = "https://apim.open.fat.homedo.com/member/member/subLogIn?key=2981eabb351143bc80b911f0b757d7ed&version=1.83&platform=mobile1.2.5&Identification=864394010640011";


		}else if("pro".equals(env)){

			url = "https://apim.open.homedo.com/member/member/subLogIn?key=2981eabb351143bc80b911f0b757d7ed&version=1.83&platform=mobile1.2.5&Identification=864394010640011";
		}
		else{

			logger.info("未正确获取到测试环境信息，导致请求url为null，无法获取到ticket值！");
		}

		String dataType="application/json";
		String data = "{\r\n  \"account\": \""+name+"\",\r\n  \"installSource\": \"string\",\r\n  \"passWord\": \""+pwd+"\"\r\n}";

		logger.info("url="+url);
		logger.info("data="+data);

		JSONObject resp = httpClientService.doPostOfJSONObject(url,data,dataType);

		logger.info("结果校验");
		String msg = resp.getString("msg");
		logger.info("msg="+msg);
		Assert.assertEquals(msg,datadriven.get("msg"));


		logger.info("--------------接口测试结束------------------------");

	}
	
	@DataProvider(name = "testData")
	public Iterator<Object[]> data1test() throws IOException {
		return new ExcelProvider(env,this, "testData");
	}
	
	@AfterClass
	public void afterClass()  {	

	}
	
}		

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

public class Front_MemberAllOrderGroupslistPostTest extends BaseTest {

	String url = null;

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfUAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC前端接口_会员_我的订单列表信息",timeOut=300000)
	public void memberAllOrderGroupslist(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env);

		logger.info("请求地址&封装数据");
		String name = datadriven.get("name");
		String pwd = datadriven.get("pwd");
		String ticket=helperService.getTicketOfPCLogin(env,name,pwd);
		logger.info("ticket="+ticket);

		if ("uat".equals(env)){
			url="https://b2b.uat.homedo.com/mall-pcweb-compositeservice/member/allorder/groupslist?ticket="+ticket;
		}else if("fat".equals(env)){
			url="https://b2b.fat.homedo.com/mall-pcweb-compositeservice/member/allorder/groupslist?ticket="+ticket;
		}else {
			url="https://b2b.homedo.com/mall-pcweb-compositeservice/member/allorder/groupslist?ticket="+ticket;
		}

		logger.info("url="+url);

		String data = "endDate=&groupType=1&isExactType=0&orderName=&orderOperatorId=&orderStatus=0&orderType=0&pageIndex="+datadriven.get("pageIndex")+"&pageSize=10&purchaseInfo=&startDate=&examineType=0";
		logger.info("data="+data);

		String dataType = "application/x-www-form-urlencoded";
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

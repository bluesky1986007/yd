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

public class Front_HandleMemberInvoiceInfoPostTest extends BaseTest {

	@BeforeClass
	public void beforeClass()  {

//		env = BaseTest.envOfFAT;
//		logger.info("env="+env);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="P1_PC交易_下单_新增修改发票",timeOut=300000)
	public void handleMemberInvoiceInfo(Map<String, String> datadriven)throws Exception {

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
			url = "https://b2b.fat.homedo.com/shopping/api/handleMemberInvoiceInfo";
		}else {
			url = "https://b2b.homedo.com/shopping/api/handleMemberInvoiceInfo";

		}
		logger.info("url="+url);

		logger.info("封装数据");
		String type = datadriven.get("type");
		String accountId = datadriven.get("accountId");
		String id = datadriven.get("id");
		String data = "{\"type\":\""+type+"\",\"request\":{\"accountId\":\""+accountId+"\",\"address\":\"杜鹃路66号\",\"cityId\":\"109\",\"company\":\"c是\",\"companyAddress\":\"上海浦东新区\",\"companyBankName\":\"上海交通银行\",\"companyBankNumber\":\"621111122223333\",\"companyPhone\":\"62143333\",\"content\":\"明细\",\"districtId\":\"1174\",\"email\":\"\",\"generalType\":2,\"id\":\""+id+"\",\"mobile\":\"13112345678\",\"provinceId\":\"9\",\"recipient\":\"测试人员\",\"statusFlag\":2,\"taxpayerCode\":\"123456789012\",\"title\":\"\",\"titleType\":0,\"type\":2,\"submit\":true}}";
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

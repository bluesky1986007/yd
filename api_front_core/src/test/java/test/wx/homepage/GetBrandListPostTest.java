package test.wx.homepage;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import common.frame.data.ExcelProvider;
import common.frame.test.BaseTest;
import common.frame.utils.SignUtilsOfPHP;
import common.frame.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class GetBrandListPostTest extends BaseTest {

	String url = null;
	Map<String,Object> paramMap = Maps.newHashMap();

	@BeforeClass
	public void beforeClass()  {

		env = BaseTest.envOfPRO;
		client = BaseTest.clientOfwx;
		logger.info("env="+env+";"+"client="+client);

	}
	
	@Test(enabled = true,dataProvider = "" + "testData",description="流量接口--首页--活动列表",timeOut=300000)
	public void getBrandList(Map<String, String> datadriven)throws Exception {

		logger.info("接口测试开始...........");
		logger.info("env="+env+";"+"client="+client);

		String categoryId = datadriven.get("categoryId");
		String pageSize = datadriven.get("pageSize");
		String pageNum = datadriven.get("pageNum");
		String timeSort = datadriven.get("timeSort");
		String saleSort = datadriven.get("saleSort");
		String appKey = datadriven.get("appKey");
		String source = datadriven.get("source");
		String version = datadriven.get("version");
		String sessionId = datadriven.get("sessionId");
		String loginFlag = datadriven.get("loginFlag");
		String memberInviteCode = datadriven.get("memberInviteCode");
		String tstamp = Utils.getTstamp();

		paramMap.put("categoryId",categoryId);
		paramMap.put("pageSize",pageSize);
		paramMap.put("pageNum",pageNum);
		paramMap.put("timeSort",timeSort);
		paramMap.put("saleSort",saleSort);
		paramMap.put("appKey",appKey);
		paramMap.put("source",source);
		paramMap.put("version",version);
		paramMap.put("sessionId",sessionId);
		paramMap.put("loginFlag",loginFlag);
		paramMap.put("memberInviteCode",memberInviteCode);
		paramMap.put("tstamp",tstamp);

		url = "https://"+ Utils.getCurentUrlDomain(env,client)+"/v100/brand/getBrandList";
		logger.info("url="+url);

		String dataType="application/x-www-form-urlencoded";
		String sign = SignUtilsOfPHP.calcSign(paramMap);

		String data = "categoryId="+categoryId+"&pageSize="+pageSize +"&pageNum="+pageNum+"&timeSort="+timeSort
				+"&saleSort="+saleSort +"&appKey="+appKey+"&tstamp="+tstamp+"&source="+source+"&version="+version
				+"&sessionId="+sessionId+"&loginFlag="+loginFlag+"&memberInviteCode="+memberInviteCode+"&sign="+sign;

		logger.info("data="+data);

		JSONObject resp = httpClientService.doPostOfJSONObject(url,data,dataType);

		logger.info("结果校验");
		String msg = resp.getString("msg");
		logger.info("actual result:");
		logger.info("msg="+msg);

		String expMsg = datadriven.get("msg");
		logger.info("expected result:");
		logger.info("msg="+expMsg);

		Assert.assertEquals(msg,expMsg);

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

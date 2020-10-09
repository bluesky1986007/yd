package common.frame.test;

import common.frame.data.ExcelProviderByTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import orm.SqlServerDataDeal;
import service.HelperService;
import service.HttpClientService;

import java.util.Iterator;

@ContextConfiguration(locations={"classpath:/context/applicationContext.xml"})
public abstract class BaseTest extends AbstractTestNGSpringContextTests{
	
	@Autowired
	public ExcelProviderByTest excelProviderByTest;
	@Autowired
	public HttpClientService httpClientService;
	@Autowired
	public HelperService helperService;
	@Autowired
	public SqlServerDataDeal sqlServerDataDeal;

	public static String env = System.getProperty("envID");
	public static String envOfUAT = "uat";
	public static String envOfFAT = "fat";
	public static String envOfPRO = "pro";

//	public static final String PC_TICKET = "PC_TICKET";
//	public static final String PC_COOKIE = "PC_COOKIE";
//	public static final String PC_CACHEKEY = "PC_CACHEKEY";

	//支付提交生成的payToken
	public static final String PC_PAY_TOKEN = "PC_PAY_TOKEN";
	//订单号
	public static final String PC_ORDERID = "PC_ORDERID";

	public BaseTest(){

//		if ("pro".equals(env)){
//			env="";
//		}
//		logger.info("env="+env);
	}


	public Object getBean(String name)
  {
	   return this.applicationContext.getBean(name);
  }
	
	
//   public Iterator<Object[]> ExcelProviderByEnv(String env, Object aa, String sheetName) {
//
//	     return this.excelProviderByTest.excelProvider(env, aa, sheetName);
//   }



}

package test.zseltest;

import com.google.common.collect.Maps;
import common.frame.data.CvsWriter;
import common.frame.data.ExcelProvider;
import common.frame.test.BaseTest;
import common.frame.utils.SignUtilsOfJava;
import common.frame.utils.SignUtilsOfPHP;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class TestSel extends BaseTest {


  @BeforeClass
  public void beforeClass() {

    env = BaseTest.envOfPRO;
    logger.info("env="+env);
  }

  @Test(enabled = false,dataProvider = "" + "testData")
  public void function2(Map<String, String> datadriven) throws Exception {

    HashMap a = new HashMap();

    logger.info("获取cookie");
    String name = datadriven.get("name");
    String pwd = datadriven.get("pwd");

    String ticket=helperService.getTicketOfPCLogin(env,name,pwd);
    String cookie = "ticket="+ticket;
    logger.info("cookie="+cookie);

    logger.info("获取payKey和payToken");
    String orderNo = datadriven.get("orderNo");
    HashMap<String,String> params = helperService.getPayKeyAndPayToken(env,orderNo,cookie);
    String payKey = params.get("payKey");
    String payToken = params.get("payToken");
    logger.info("payKey:"+payKey+"; payToken:"+payToken);

//    a.put("cookie",cookie);
//    a.put("addressId",addressId);
//    a.put("productId",productId);
//    a.put("promotionId",promotionId);
//    a.put("cacheKey",cacheKey);
//    a.put("ticket",ticket);
//    a.put("dataId",dataId);
//    a.put("orderId",orderId);
    a.put("payKey",payKey);
    a.put("payToken",payToken);


    new CvsWriter(this,env,a);


  }

  @Test(enabled = true)
  public void function() throws Exception {

    Map<String,Object> paramMap = Maps.newHashMap();
    long tstamp = (int)(System.currentTimeMillis()/1000);
    String signKey = "jinribaotuan";

    paramMap.put("appKey",signKey);
    paramMap.put("tstamp",tstamp);
    paramMap.put("source",signKey);
    paramMap.put("version","1.0.0");

    paramMap.put("loginFlag","1");
    paramMap.put("memberInviteCode","MXCGYP");
    paramMap.put("class","2");
    paramMap.put("pageNum","1");
    paramMap.put("pageSize","50");
    paramMap.put("sessionId","N6ofXpw8s3NIuoBBehDAIiPUOMmseGyH");


    String sign = SignUtilsOfPHP.calcSign(paramMap);

    System.out.println("tstamp="+tstamp);
    System.out.println("sign="+sign);


  }

  @DataProvider(name = "testData")
  public Iterator<Object[]> data1test() throws IOException {
    return new ExcelProvider(env,this, "testData");  }

  @AfterClass
  public void afterClass() {
  }

}

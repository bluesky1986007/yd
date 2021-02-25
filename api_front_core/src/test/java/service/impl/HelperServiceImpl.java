package service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import service.HelperService;
import service.HttpClientService;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HelperServiceImpl extends AbstractTestNGSpringContextTests implements HelperService {

	@Autowired
	public HttpClientService httpClientService;


	@Override
	public String getAccountIdByBackSystem(String env, String name, String cookie) throws Exception {


		logger.info("getAccountIdByBackSystem开始...");
		String nameOfUrlEncode = URLEncoder.encode(name,"utf-8");

		logger.info("name="+name);
		logger.info("nameOfUrlEncode="+nameOfUrlEncode);

		String accountIdUrl = null;
		if ("uat".equals(env)){
			accountIdUrl = "http://application.oms.uat.homedo.com/FinanceAccountManage/QueryAllPage?Id=&Name="+nameOfUrlEncode+"&Mobile=&RealName=&Recommended=&AgentType=&StartTime=&EndTime=&RegisterStartTime=&RegisterEndTime=&Status=&IsUsed=&IsJiCai=&AuditType=&PreciseSearch=true&ParentAccountName=&IsSecondMember=false&IsCityPartners=false&IsManagePartner=false&HaveRecommended=&IsInner=&ServiceIdAladdinJob=&Platform=&page=1&rows=20";

		}else if("fat".equals(env)){
			accountIdUrl = "http://application.oms.fat.homedo.com/FinanceAccountManage/QueryAllPage?Id=&Name="+nameOfUrlEncode+"&Mobile=&RealName=&Recommended=&AgentType=&StartTime=&EndTime=&RegisterStartTime=&RegisterEndTime=&Status=&IsUsed=&IsJiCai=&AuditType=&PreciseSearch=true&ParentAccountName=&IsSecondMember=false&IsCityPartners=false&IsManagePartner=false&HaveRecommended=&IsInner=&ServiceIdAladdinJob=&Platform=&page=1&rows=20";

		}else {
			//生产环境
			accountIdUrl = "http://application.oms.homedo.com/FinanceAccountManage/QueryAllPage?Id=&Name="+nameOfUrlEncode+"&Mobile=&RealName=&Recommended=&AgentType=&StartTime=&EndTime=&RegisterStartTime=&RegisterEndTime=&Status=&IsUsed=&IsJiCai=&AuditType=&PreciseSearch=true&ParentAccountName=&IsSecondMember=false&IsCityPartners=false&IsManagePartner=false&HaveRecommended=&IsInner=&ServiceIdAladdinJob=&Platform=&page=1&rows=20";
		}

		logger.info("accountIdUrl="+accountIdUrl);

		JSONObject accountIdUrlOfresp = httpClientService.doGetWithCookieOfJSONObject(accountIdUrl,cookie);
		String memberRows = accountIdUrlOfresp.getString("rows");
		String accountId = getId(memberRows);
		Assert.assertNotNull(accountId);

		logger.info("accountId="+accountId);
		return accountId;
	}


	@Override
	public String getTicketOfPCLogin(String env, String name, String pwd) throws Exception {

		logger.info("获取PC登录后ticket...");
		String url=null;
		String setCookie=null;
		String ticket=null;
		List list = new ArrayList<>();

		if ("uat".equals(env)){
			url = "https://passport.uat.homedo.com/Account/Login";

		}else if("fat".equals(env)){
			url = "https://passport.fat.homedo.com/Account/Login";

		}else if("pro".equals(env)){
			url = "https://passport.homedo.com/Account/Login";
		}
		else{

			logger.info("未正确获取到测试环境信息，导致请求url为null，无法获取到ticket值！");
		}

		String dataType="application/x-www-form-urlencoded";
		String data = "Name="+name+"&Password="+pwd+"&Code=&IsRemember=true&Url=";

		logger.info("url="+url);
		logger.info("data="+data);

		list = httpClientService.doPostAndGetOneOfHeaders(url,data,dataType,"Set-Cookie");

		for (int i=0;i<list.size();i++){

			String keyValue = list.get(i).toString();

			if (keyValue.contains("ticket")){
				setCookie = list.get(i).toString();
				logger.info("setCookie="+setCookie);
			}

		}

		int rightSide = setCookie.indexOf(";");
		ticket = setCookie.substring(7,rightSide);

		logger.info("ticket获取完成ticket="+ticket);

		return ticket;
	}

	@Override
	public String getCacheKeyOfPC(String env, String productId, String cookie) throws Exception {

		logger.info("获取cacheKey...");
		String url=null;
		String key=null;

		if ("uat".equals(env)){

			url = "https://b2b.uat.homedo.com/Order/GetSettlement";

		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/Order/GetSettlement";

		}else if("pro".equals(env)){
			url = "https://b2b.homedo.com/Order/GetSettlement";
		}
		else{

			logger.info("未正确获取到测试环境信息，无法获取到key值！");
		}

		String dataType="application/x-www-form-urlencoded";
		String data = "dtos=%7B%22Products%22%3A%5B%7B%22PromotionId%22%3A%220%22%2C%22ProductId%22%3A%22"+productId+"%22%2C%22Name%22%3A%22%E5%A4%A7%E5%8D%8E%20%20DH-NVR1104-P%20POE%E4%BE%9B%E7%94%B5%20%E7%BD%91%E7%BB%9C%E7%A1%AC%E7%9B%98%E5%BD%95%E5%83%8F%E6%9C%BA%22%2C%22Count%22%3A%221%22%7D%5D%2C%22PinDanRuleId%22%3A0%7D";

		logger.info("url="+url);
		logger.info("data="+data);

		Headers headers = httpClientService.doPost302HeadersWithCookie(url,data,dataType,cookie);
		String location=headers.get("location");
		logger.info("location="+location);
		key =  location.substring(25);
		logger.info("key="+key);

		return key;

	}

	@Override
	public HashMap<String,String> getPayKeyAndPayToken(String env, String orderId, String cookie) throws Exception {


		logger.info("getPayKeyAndPayToken开始...");

		String redirectUrl=null;
		if("fat".equals(env)){
			redirectUrl = "https://pay2.fat.homedo.com/Cashier/Redirect?orderIds="+orderId+"&payPlatformType=2&platform=web&os=pc&appVersion=&failOrderNos=";
		}else {
			redirectUrl = "https://pay2.homedo.com/Cashier/Redirect?orderIds="+orderId+"&payPlatformType=2&platform=web&os=pc&appVersion=&failOrderNos=";
		}

		logger.info("redirectUrl="+redirectUrl);


		Headers redirectResp = httpClientService.doGet302HeadersWithCookie(redirectUrl,cookie);
		String location = redirectResp.get("Location");
		logger.info("Location="+location);

		logger.info("开始截取payKey和payToken");
		HashMap<String,String> result= new HashMap<String,String>();
		String[] data = location.split("/");
		int dataLeangth = data.length;
		logger.info("dataLeangth="+dataLeangth);
		String payKey = data[dataLeangth-2];
		String payToken = data[dataLeangth-1];

		logger.info("payKey="+payKey);
		logger.info("payToken="+payToken);

		result.put("payKey",payKey);
		result.put("payToken",payToken);

		return result;
	}

    @Override
    public String getOrderNoByInterval(String env, String orderBizCode, String cookie, int count, long interval, long waitTime) throws Exception {
        logger.info("获取订单orderNo...");
        logger.info("请求地址");
        String url = null;
        String orderNo = null;

        if("fat".equals(env)){
            url = "https://b2b.fat.homedo.com/shopping/api/getOrderInfoByOrderNo?id="+orderBizCode;
        }else {
            url = "https://b2b.homedo.com/shopping/api/getOrderInfoByOrderNo?id="+orderBizCode;

        }

        logger.info("url="+url);


        Boolean result = false;
        int round = 0;
        while(!result) {
            try {
                Thread.sleep(interval); //设置间隔时间
                round ++ ;
//                System.out.println("循环执行第" + round + "次");
                logger.info("循环执行第" + round + "次");
                logger.info("发送请求并获得response");
                JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookie,waitTime);
                String bizCode = resp.getString("bizCode");
                orderNo = "".equals(bizCode) ? null : bizCode;
                logger.info("orderNo = "+orderNo);

                if (!(null==orderNo)) {
                    result = true;
                    break ;
                }

                if (round == count) {
                    result = true;
                    break ;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Assert.assertNotNull(orderNo,"没有获取到订单号!");

        return orderNo;
    }


    public String getId(String rowsData){

		JSONArray rowsList = JSON.parseArray(rowsData);
		int rowsListSize = rowsList.size();
		logger.info("rowsListSize="+rowsListSize);
		String id = null;
		if (rowsListSize >= 1){
			JSONObject jasonValue = rowsList.getJSONObject(0);
			id = jasonValue.get("Id").toString();
			System.out.println("Id = "+id);


		}else {

			logger.info("未获取到相关记录！");
		}

		return id;


	}


}

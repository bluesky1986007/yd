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
	public String getCookieOfBackSystem(String env,String name,String pwd) throws Exception {

		logger.info("getCookieOfBackSystem开始...");

		String cookieUrl=null;
		if ("uat".equals(env)){
			cookieUrl = "http://erp.uat.homedo.com/Test/Go";

		}else if("fat".equals(env)){
			cookieUrl = "http://erp.fat.homedo.com/Test/Go";

		}else {
			//生产环境
			cookieUrl = "http://erp.homedo.com/Test/Go";
		}

		logger.info("cookieUrl="+cookieUrl);

		String dataType="application/x-www-form-urlencoded";
		String data = "username="+name+"&password="+pwd;
		logger.info("data="+data);

		logger.info("发送请求并获得response");
		JSONObject cookieUrlOfresp = httpClientService.doPostOfJSONObject(cookieUrl,data,dataType);

		String IsOK = cookieUrlOfresp.getString("IsOK");
		Assert.assertEquals(IsOK,"true");
		String Value = cookieUrlOfresp.getString("Value");
		String cookie = null;

		if ("uat".equals(env)){
			cookie = "COOKIE_USERuat.="+Value;

		}else if("fat".equals(env)){
			cookie = "COOKIE_USERfat.="+Value;

		}else {
			//生产环境
			cookie = "COOKIE_USER="+Value;
		}

		logger.info("cookie="+cookie);

		return cookie;
	}

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
	public void hebiAddAndApprovalByBackSystem(String env, String accountId, String integral, String name, String cookie) throws Exception {

		logger.info("hebiAddAndApprovalByBackSystem开始...");
		String dataType = "application/x-www-form-urlencoded";
		String nameOfUrlEncode = URLEncoder.encode(name,"utf-8");

		logger.info("新增河币申请");
		String addUrl = null;
		if ("uat".equals(env)){
			addUrl = "http://application.oms.uat.homedo.com/Integrals/SaveIntegral";

		}else if("fat".equals(env)){
			addUrl = "http://application.oms.fat.homedo.com/Integrals/SaveIntegral";

		}else {
			//生产环境
			addUrl = "http://application.oms.homedo.com/Integrals/SaveIntegral";
		}

		logger.info("addUrl="+addUrl);

		String addUrlOfdata = "Id=&AccountId="+accountId+"&Title=%E6%99%9A%E5%88%B0%E8%B5%94&Amount="+integral+"&Remark=%E6%B5%8B%E8%AF%95%E7%94%A8";

		logger.info("发送请求并获得response");
		JSONObject addUrlOfresp = httpClientService.doPostWithCookieOfJSONObject(addUrl,addUrlOfdata,dataType,cookie,2000L);
		logger.info("结果校验");
		String success = addUrlOfresp.getString("success");
		Assert.assertEquals(success,"True");


		logger.info("查询申请记录");
		String queryUrl = null;
		if ("uat".equals(env)){
			queryUrl = "http://application.oms.uat.homedo.com/Integrals/GetIntegralPage";

		}else if("fat".equals(env)){
			queryUrl = "http://application.oms.fat.homedo.com/Integrals/GetIntegralPage";

		}else {
			//生产环境
			queryUrl = "http://application.oms.homedo.com/Integrals/GetIntegralPage";
		}

		logger.info("queryUrl="+queryUrl);


		String queryUrlOfdata = "title=&status=8&accountName="+nameOfUrlEncode+"&companyName=&startInsertDate=&endInsertDate=&executeStartDate=&executeEndDate=&page=1&rows=20&sort=Id&order=desc";
//		String queryUrlOfdata = "status=8&page=1&rows=20&sort=Id&order=desc";

		logger.info("queryUrlOfdata="+queryUrlOfdata);

		logger.info("发送请求并获得response");
		JSONObject queryUrlOfresp = httpClientService.doPostWithCookieOfJSONObjectNoCache(queryUrl,queryUrlOfdata,dataType,cookie);
		String approveRows = queryUrlOfresp.getString("rows");
		String approveId = getId(approveRows);
		Assert.assertNotNull(approveId);

		logger.info("进行审核");
		String approveUrl = null;
		if ("uat".equals(env)){
			approveUrl = "http://application.oms.uat.homedo.com/Integrals/AuditIntegral";

		}else if("fat".equals(env)){
			approveUrl = "http://application.oms.fat.homedo.com/Integrals/AuditIntegral";

		}else {
			//生产环境
			approveUrl = "http://application.oms.homedo.com/Integrals/AuditIntegral";
		}

		logger.info("approveUrl="+approveUrl);

		String approveUrlOfdata = "id="+approveId+"&status=2&remark=%E6%B5%8B%E8%AF%95%E7%94%A8";

		logger.info("发送请求并获得response");
		JSONObject approveUrlOfresp = httpClientService.doPostWithCookieOfJSONObjectNoCache(approveUrl,approveUrlOfdata,dataType,cookie);

		logger.info("结果校验");
		String isOk = approveUrlOfresp.getString("IsOK");
		logger.info("IsOK="+isOk);
		Assert.assertEquals(isOk,"true");

	}

	@Override
	public String getTicketOfAppLogin(String env,String name,String pwd) throws Exception {

		logger.info("获取移动端登录后ticket...");
		String url=null;
		String ticket=null;

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

		JSONObject resp = httpClientService.doPostOfJSONObject(data,dataType,url);
		ticket=resp.getString("data");
		logger.info("ticket获取完成ticket="+ticket);

		return ticket;
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
	public String getOrderDataId(String env, String addressId, String productId, String count, String promotionId, String cookie) throws Exception {

		String url=null;

		logger.info("开始提交订单并获取dataId...");
		if ("uat".equals(env)){
			url = "https://b2b.uat.homedo.com/Order/SubmitSettlement";
		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/Order/SubmitSettlement";
		}else {
			url = "https://b2b.homedo.com/Order/SubmitSettlement";

		}

		String cacheKey= this.getCacheKeyOfPC(env,productId,cookie);

		String dataType = "application/x-www-form-urlencoded";
		String data = "FreightName=%E6%B1%BD%E8%BF%90&Integral=&OrderRemark=%E7%AB%8B%E5%8D%B3%E5%8F%91%E8%B4%A7&AddressId="+addressId+"&InvoiceId=0" +
				"&CouponsData=%5B%5D&ProductData=%5B%7B%22ProductId%22%3A%22"+productId+"%22%2C%22PromotionId%22%3A%22"+promotionId+"%22%2C%22Count%22%3A%22"+count+"%22%2C%22" +
				"DeliveryDate%22%3A%222019-09-06%22%2C%22IsNeiCai%22%3A%22False%22%2C%22IsQuDao%22%3A%22False%22%2C%22ShopId%22%3A0%7D%5D&PackageData=%5B%5D" +
				"&PaymentMethod=0&CycleByMonth=0&CycleByDay=0&OrderType=1&DataId=&PRI=10&NECaptchaValidate=&SelfRemark=&Linkman=&LinkmanPhone=&DZ_RequireId=" +
				"&IsWaitDeliver=false&WillType=&ServiceCharge=0&ZBServiceItemId=0&PdRuleId=0&CouponFreightItemId=0&CouponServiceItemId=0" +
				"&CacheKey="+cacheKey+"";

		logger.info("url="+url);
		logger.info("addressId="+addressId);
		logger.info("productId="+productId);
		logger.info("promotionId="+promotionId);
		logger.info("cookie="+cookie);
		logger.info("cacheKey="+cacheKey);

		JSONObject resp = httpClientService.doPostWithCookieOfJSONObject(url,data,dataType,cookie);
		Thread.sleep(1000);

//		if ("fat".equals(env)||"uat".equals(env)){
//			Thread.sleep(2000);
//		}


		String dataId = resp.getString("message");
		logger.info("dataId="+dataId);

		return dataId;
	}

	@Override
	public String getOrderId(String env, String dataId, String cookie, long waitTime) throws Exception {

		logger.info("获取订单orderid...");
		String url = null;
		if ("uat".equals(env)){
			url = "https://b2b.uat.homedo.com/Order/SettlementAsync?dataId="+dataId;
		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/Order/SettlementAsync?dataId="+dataId;
		}else {
			url = "https://b2b.homedo.com/Order/SettlementAsync?dataId="+dataId;
		}

		logger.info("url="+url);
		String data = "dataId="+dataId;

		JSONObject resp = null;
		if ("uat".equals(env)||"fat".equals(env)){
			resp = httpClientService.doPostWithCookieOfJSONObject(url,data,"",cookie,waitTime);
		}else {
			resp = httpClientService.doPostWithCookieOfJSONObject(url,data,"",cookie);
		}

		String orderid = resp.getString("orderid");
		logger.info("orderid="+orderid);

		return orderid;
	}

	@Override
	public String getOrderAmount(String env, String orderId, String cookie) throws Exception {

		logger.info("getOrderAmount开始...");
		String amount = null;
		String url=null;
		if ("uat".equals(env)){
			url = "https://b2b.uat.homedo.com/Order/GetOrderInfo";
		}else if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/Order/GetOrderInfo";
		}else {
			url = "https://b2b.homedo.com/Order/GetOrderInfo";

		}

		logger.info("url="+url);
		String data = "orderId="+orderId;
		String dataType = "application/x-www-form-urlencoded";

		logger.info("orderId="+orderId);
		JSONObject resp = null;
		if ("pro".equals(env)){
			resp = httpClientService.doPostWithCookieOfJSONObject(url,data,dataType,cookie);

			amount = resp.getString("Amount");
			logger.info("Amount="+amount);

		}else{
			resp = httpClientService.doPostWithCookieOfJSONObject(url,data,dataType,cookie,5000L);

			if (!(resp==null)){
				amount = resp.getString("Amount");
				logger.info("Amount="+amount);

			}else {
				logger.info(env+"环境未获取到response body信息");
			}
		}

		return amount;
	}

	@Override
	public String getOrderRealAmount(String env, String orderNo, String cookie) throws Exception {

		logger.info("开始获取订单金额getOrderRealAmount...");
		String amount = null;
		String url=null;
		if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/shopping/api/getOrderInfo?orderIds="+orderNo;
		}else {
			url = "https://b2b.homedo.com/shopping/api/getOrderInfo?orderIds="+orderNo;

		}

		logger.info("url="+url);

		JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookie);
		logger.info("获取订单上的amount");
		JSONObject dataObj = resp.getJSONObject("data");

		amount = "".equals(dataObj.getString("amount")) ? null : dataObj.getString("amount");
		logger.info("amount = "+amount);
		Assert.assertNotNull(amount,"没有获取到订单金额amount!");

		return amount;
	}

	@Override
	public HashMap<String,String> getPayKeyAndPayToken(String env, String orderId, String cookie) throws Exception {


		logger.info("getPayKeyAndPayToken开始...");
//		String redirectUrl=null;
//		if ("uat".equals(env)){
//			redirectUrl = "https://pay.uat.homedo.com/Cashier/Redirect?orderIds="+orderId+"&payPlatformType=2&platform=web&os=pc";
//		}else if("fat".equals(env)){
//			redirectUrl = "https://pay.fat.homedo.com/Cashier/Redirect?orderIds="+orderId+"&payPlatformType=2&platform=web&os=pc";
//		}else {
//			redirectUrl ="https://pay.homedo.com/Cashier/Redirect?orderIds="+orderId+"&payPlatformType=2&platform=web&os=pc";
//		}
//
//		logger.info("redirectUrl="+redirectUrl);

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
	public String getOrderBizCode(String env, String addressId, String invoiceId, String productId, String count, String promotionId, String price, String cookie) throws Exception {

		logger.info("开始提交订单并获取orderBizCode...");
		String orderBizCode = null;
		logger.info("请求地址");
		String url = null;
		if("fat".equals(env)){
			url = "https://b2b.fat.homedo.com/shopping/api/orderSubmit";
		}else {
			url = "https://b2b.homedo.com/shopping/api/orderSubmit";

		}
		logger.info("url="+url);

		logger.info("封装数据");
		String data = "{\"addressId\":\""+addressId+"\",\"dataId\":\"\",\"deliveryDate\":\"\",\"invoiceId\":\""+invoiceId+"\",\"necaptchaValidate\":\"\",\"orderRemark\":\"立即发货\",\"paymentMethod\":0,\"productData\":{\"addressId\":\""+addressId+"\",\"couponFreightItemId\":null,\"couponsData\":[],\"integral\":0,\"isWaitDeliver\":false,\"pdRuleId\":0,\"productData\":[{\"count\":\""+count+"\",\"productId\":\""+productId+"\",\"promotionId\":\""+promotionId+"\",\"price\":"+price+",\"priceMinus\":0,\"hebiScale\":0,\"isNeiCai\":false,\"productIntegralPresentScale\":0,\"productIsLianYing\":0,\"promotionCanBuy\":1,\"promotionIntegralPresentScale\":0,\"promotionIsCanUseIntegral\":false,\"promotionIsCanUseRed\":false,\"promotionUseRedType\":2,\"promotionMoreThanMoney\":0,\"promotionSubtractMoney\":0,\"promotionType\":65536,\"isQuDao\":false,\"shopId\":\"0\",\"isUsedCycle\":true,\"promotionSubtractType\":0,\"deposit\":0,\"maiZengList\":[]}],\"packageData\":[]},\"zbData\":null,\"cycle\":null,\"shopProductListMapAReq\":null}";
		String dataType = "application/json";

		logger.info("data:"+data);
		logger.info("dataType:"+dataType);

		logger.info("发送请求并获得response");
		JSONObject resp = httpClientService.doPostWithCookieOfJSONObject(url,data,dataType,cookie);

		orderBizCode = "".equals(resp.getString("bizCode")) ? null : resp.getString("bizCode");
		logger.info("orderBizCode = "+orderBizCode);

		Assert.assertNotNull(orderBizCode,"没有获取到orderBizCode!");

		return orderBizCode;
	}

	@Override
	public String getOrderNo(String env, String orderBizCode, String cookie, long waitTime) throws Exception {

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

		logger.info("发送请求并获得response");
		Thread.sleep(waitTime);
		JSONObject resp = httpClientService.doGetWithCookieOfJSONObject(url,cookie,waitTime);
		String bizCode = resp.getString("bizCode");
		orderNo = "".equals(bizCode) ? null : bizCode;
		logger.info("orderNo = "+orderNo);

		Assert.assertNotNull(orderNo,"没有获取到订单号!");

		return orderNo;
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

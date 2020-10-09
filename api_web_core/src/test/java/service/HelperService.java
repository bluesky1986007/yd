package service;


import java.util.HashMap;

public interface HelperService {



	/**
	 * 获取后台系统的登录态cookiet信息
	 * @return
	 * @throws Exception
	 */
	public String getCookieOfBackSystem(String env,String name,String pwd) throws Exception;

	/**
	 * 从后台获取账号accountId
	 * @param name
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public String getAccountIdByBackSystem(String env, String name, String cookie) throws Exception;

	/**
	 * 后台河币充值与审核
	 * @param env
	 * @param accountId
	 * @param integral
	 * @param name
	 * @param cookie
	 * @throws Exception
	 */
	public void hebiAddAndApprovalByBackSystem(String env, String accountId, String integral, String name, String cookie)throws Exception;

	/**
	 * 移动端登录接口获取登录态ticket
	 * @param env
	 * @return
	 * @throws Exception
	 */
	public String getTicketOfAppLogin(String env, String name, String pwd) throws Exception;


	/**
	 * 获取网站登录后ticket
	 * @param env
	 * @param name
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public String getTicketOfPCLogin(String env, String name, String pwd) throws Exception;


	/**
	 * 获取网站下单时候的cacheKey值
	 * @param env
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public String getCacheKeyOfPC(String env,String productId,String cookie) throws Exception;


	/**
	 *获取订单dataId,旧版获取订单会用,重构后不用dataid了,用getOrderBizCode方法
	 * @param env
	 * @param addressId
	 * @param productId
	 * @param promotionId
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public String getOrderDataId(String env,String addressId,String productId,String count,String promotionId,String cookie) throws Exception;


	/**
	 *获取订单id,旧版获取订单id的方法,重构后不用了,用getOrderNo方法
	 * @param env
	 * @param dataId
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public String getOrderId(String env, String dataId, String cookie, long waitTime) throws Exception;

	/**
	 * 获取订单上需要支付的金额,这是旧版的获取金额方法,重构后不用了,用getOrderRealAmount方法
	 * @param env
	 * @param orderId
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public  String getOrderAmount(String env,String orderId,String cookie) throws Exception;

	/**
	 * 获取订单上实际应付金额
	 * @param env
	 * @param orderNo
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public  String getOrderRealAmount(String env,String orderNo,String cookie) throws Exception;


	/**
	 * 获取payKey和payToken
	 * @param env
	 * @param orderId
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,String> getPayKeyAndPayToken(String env, String orderId, String cookie) throws Exception;


	/**
	 *获取订单生成前的一个bizCode,之后获取订单id要用
	 * @param env
	 * @param addressId
	 * @param invoiceId
	 * @param productId
	 * @param count
	 * @param promotionId
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public String getOrderBizCode(String env,String addressId,String invoiceId, String productId,String count,String promotionId,String price,String cookie) throws Exception;

	/**
	 * 获取订单号
	 * @param env
	 * @param orderBizCode
	 * @param cookie
	 * @param waitTime
	 * @return
	 * @throws Exception
	 */
	public String getOrderNo(String env, String orderBizCode, String cookie, long waitTime) throws Exception;

	/**
	 * 在限制的次数范围内,每间隔段时间去获取一次orderNo
	 * @param env
	 * @param orderBizCode
	 * @param cookie
	 * @param count
	 * @param interval
	 * @param waitTime
	 * @return
	 * @throws Exception
	 */
	public String getOrderNoByInterval(String env, String orderBizCode, String cookie, int count, long interval, long waitTime) throws Exception;


}

package service;


import java.util.HashMap;

public interface HelperService {


	/**
	 * 从后台获取账号accountId
	 * @param name
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public String getAccountIdByBackSystem(String env, String name, String cookie) throws Exception;

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
	 * 获取payKey和payToken
	 * @param env
	 * @param orderId
	 * @param cookie
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,String> getPayKeyAndPayToken(String env, String orderId, String cookie) throws Exception;

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

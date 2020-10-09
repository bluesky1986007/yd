package orm;

import java.sql.Connection;

public interface DataBaseDao {

	/**
	 * SqlServer数据库初始化
	 * @param dataBaseName
	 * @return
	 * @throws Exception
	 */
	public Connection getInstanceOfSqlServer(String env, String dataBaseName) throws Exception;




}

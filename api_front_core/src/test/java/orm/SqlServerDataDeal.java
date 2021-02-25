package orm;

import java.util.HashMap;
import java.util.List;

public interface SqlServerDataDeal {

	/**
	 * 查询结果数据
	 * @param DBName
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> getQueryData(String env, String DBName, String sql) throws Exception;




}

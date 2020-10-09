package orm.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import common.frame.test.BaseTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import orm.DataBaseDao;

import javax.sql.DataSource;
import java.sql.Connection;

public class DataBaseDaolImpl extends AbstractTestNGSpringContextTests implements DataBaseDao {

	@Override
	public Connection getInstanceOfSqlServer(String env,String dataBaseName) throws Exception {

		String DBType = null;

		logger.info("sqlServer数据库初始化开始...");
		if ("uat".equals(env)&&"Homedo".equals(dataBaseName)){

			DBType = "sqlserver_uat_Homedo";

		}

		DataSource ds = new ComboPooledDataSource(DBType);

		Connection conn = ds.getConnection();

		logger.info("生成conn成功!");

		return conn;
	}



}

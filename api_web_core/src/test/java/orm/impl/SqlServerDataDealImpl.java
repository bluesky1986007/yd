package orm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import orm.DataBaseDao;
import orm.SqlServerDataDeal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SqlServerDataDealImpl extends AbstractTestNGSpringContextTests implements SqlServerDataDeal {

	@Autowired
	public DataBaseDao dataBaseDao;

	@Override
	public List<HashMap> getQueryData(String env, String DBName, String sql) throws Exception {

		List list = new ArrayList();
		HashMap hashmap = new HashMap();

		logger.info("数据查询开始...");
		logger.info("初始化sqlserver...");
		Connection conn = dataBaseDao.getInstanceOfSqlServer(env,DBName);

		ResultSet rs = conn.createStatement().executeQuery(sql);
		//获得ResultSetMetaData对象
		ResultSetMetaData rsmd=rs.getMetaData();
        //获得返回此 ResultSet 对象中的列数
		int count = rsmd.getColumnCount();

		logger.info("封装查询结果数据...");
		while (rs.next()) {

			for (int i = 1; i <= count; i++) {
				//获取指定列的表目录名称
				String label = rsmd.getColumnLabel(i);
				//Object的形式获取此ResultSet对象的当前行中指定列的值
				Object object = rs.getObject(i);
				//把数据库中的字段名和值对应为一个map对象中的一个键值对
				hashmap.put(label, object);

			}

			//把每条对象封装成的map对象放进list中
			list.add(hashmap);

		}

		logger.info("关闭conn...");
		conn.close();

		return list;
	}








}

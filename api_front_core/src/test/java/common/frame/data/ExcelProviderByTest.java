package common.frame.data;

import common.frame.test.BaseTest;

import java.util.Iterator;

public class ExcelProviderByTest {
	
//	private String envTestID;

	public Iterator<Object[]> excelProvider(String env, Object aa, String sheetName) {
	   return new common.frame.data.ExcelProvider(env, aa, sheetName);
    }

//	public String getEnvTestID() {
//		return envTestID;
//	}
//
//	public void setEnvTestID(String envTestID) {
//		this.envTestID = envTestID;
//	}

}

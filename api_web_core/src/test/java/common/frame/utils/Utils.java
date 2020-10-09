package common.frame.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {
	
	
	/**
	 * 获取系统当前日期，格式yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDate(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		
		String currentDate =  df.format(new Date());//new Date()为获取系统当前时间
		
		System.out.println("系统当前日期为: " + currentDate);
				
		return currentDate;
	}

	/**
	 * 随机后去1位到9位随机数字
	 * @param digit
	 * @return
	 */
	public static int getRandomNum(int digit){

		int num;
		int temp=1;


		switch (digit){

			case 1:
				temp = 1;
				break;
			case 2:
				temp = 10;
				break;
			case 3:
				temp = 100;
				break;
			case 4:
				temp = 1000;
				break;
			case 5:
				temp = 10000;
				break;
			case 6:
				temp = 100000;
				break;
			case 7:
				temp = 1000000;
				break;
			case 8:
				temp = 10000000;
				break;
			case 9:
				temp = 100000000;
				break;

		}

		num = (int)((Math.random()*9+1)*temp);

		return num;
	}



}

package common.frame.data;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CvsWriter{

    public CvsWriter(Object aa, String envTestID, HashMap<String,String> data)
    {

        String result="";


        try
        {
            File writeFile = new File(new File("./").getCanonicalPath() + "//testDataWrite//"+ envTestID + "//"+ aa.getClass().getSimpleName() + ".csv");

            BufferedWriter writeText = new BufferedWriter(new FileWriter(writeFile,true));

            for (String value:data.values()){

                if ("".equals(result)){

                    result = value;
                }else {
                    result = result+","+value;

                }

            }

            writeText.write(result);
            writeText.newLine();


            writeText.flush();
            writeText.close();

        } catch (FileNotFoundException e) {
            System.out.println("没有找到指定文件");
        } catch (IOException e) {
            System.out.println("文件读写出错");
        }
    }



}

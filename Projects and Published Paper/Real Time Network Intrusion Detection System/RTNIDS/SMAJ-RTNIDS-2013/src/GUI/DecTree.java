/*
 * SMAJ-RTNIDS 2012-2013
 */

package GUI;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public class DecTree {
                Boolean success;                                                
                String train,test,result,analysis;
    		File file;
                DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                            
 String getTrain(){
                Date d=new Date();
                train="C:/SMAJ-RTNIDS/Decision Tree/Train Log/"+df.format(d);
                file = new File(train+"/a.txt");
                if (!file.isDirectory())
                    success = (new File(train)).mkdirs();
                
                String s=train+"/"+d.getHours()+"-"+d.getMinutes()+"-"+d.getSeconds();
 return s;
 }
String getTest(){
                Date d=new Date();
                test="C:/SMAJ-RTNIDS/Decision Tree/Test Log/"+df.format(d);
                file = new File(test+"/a.txt");
                if (!file.isDirectory())
                    success = (new File(test)).mkdirs();
                
                String s=test+"/"+d.getHours()+"-"+d.getMinutes()+"-"+d.getSeconds();
 return s;
 }
String getAnalyse(){
                Date d=new Date();
                analysis="C:/SMAJ-RTNIDS/Decision Tree/Analysis Log/"+df.format(d);
                file = new File(analysis+"/a.txt");
                if (!file.isDirectory())
                    success = (new File(analysis)).mkdirs();

                String s=analysis+"/"+d.getHours()+"-"+d.getMinutes()+"-"+d.getSeconds();
 return s;
 }
String getResult(){
                Date d=new Date();
                
                result="C:/SMAJ-RTNIDS/Decision Tree/Result/"+df.format(d);
		file = new File(result+"/a.txt");
                if (!file.isDirectory())
                    success = (new File(result)).mkdirs();
                
                String s=result+"/"+d.getHours()+"-"+d.getMinutes()+"-"+d.getSeconds();
 return s;
 }
}

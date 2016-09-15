/*
 * SMAJ-RTNIDS 2012-2013
 */
package GUI;

//comma seperated .csv output  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class convertdata {
    char a,b;
    int i,flag;
    File f,fout;
    FileInputStream fi;
    FileOutputStream fo;
    public convertdata() throws FileNotFoundException {
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException{
        convertdata c= new convertdata();
        //c.accept();
        c.attname();
        c.show();
    }

    void attname() throws FileNotFoundException, IOException{
        char b;
        fout=new File("C:/SMAJ-RTNIDS/cs1.csv");
        f=new File("C:/SMAJ-RTNIDS/attname.txt");
        fi=new FileInputStream(f);
        fo=new FileOutputStream(fout);
        i=fi.available();
        b=(char)fi.read();
        while((b!='\n')&&(i>3) ){
            fo.write(b);
            b=(char)fi.read();
            i--;
        }
        fo.write(b);

    
    }
    void show() throws IOException{
        f=new File("C:/SMAJ-RTNIDS/normal traffic.txt");
        fi=new FileInputStream(f);
        i=fi.available();
        a=(char)fi.read();
        i--;
        fi.skip(38);
        i=i-38;
            
    while (i>0){
readline();
    }
        
    }
    void readline() throws IOException{
    
            a=(char)fi.read();
            i--;
            while(a!=' '){
                a=(char)fi.read();
                i--;
                if(a==',')
                {
                    a=(char)fi.read();
                    i--;
                }
                fo.write(a); 
                }
            fi.skip(15);
            i=i-15;
            fo.write(',');
            club(6);
            club(6);
            club(2);
            column();
            column();
            club(2);
            club(2);
            column();
            column();
            column();
            column();
            club(2);
            club(4);
            club(4);
            club(2);
            club(2);
            club(4);
            
            club(4);
            club(2);
            club(2);
            club(2);
            club(2);
            
                    
            while(a!='\n'){
            a=(char)fi.read();
                    i--;
         
                if(a=='|')
                    fo.write(',');
                else
                    fo.write(a);
                
            }
            
            fi.skip(41);
            i=i-41;
    }
    void club(int p) throws IOException{
               if(a=='\n')
                       return;
               while(p>0){
                    a=(char)fi.read();                 
                    i--; 
                    if(a=='|')
                    {
                        p--;
                    }
                    else if(a=='\n')
                       return;
                    else
                        fo.write(a);                    
               }
               
                   fo.write(',');       
    }
    void column() throws IOException{
         if(a=='\n')
             return;
        a=(char)fi.read();
        i--;
        while(a!='|'&&a!='\n'){
            fo.write(a);
            a=(char)fi.read();
            i--;
        }
        if(a=='\n')
            return;
        fo.write(',');       
    }
}
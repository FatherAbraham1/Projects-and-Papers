/*
 * SMAJ-RTNIDS 2012-2013
 */
package GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class kNNTest {    
    Integer dosattack=0,probeattack=0,tcppktcount=0,udppktcount=0,icmppktcount=0,oderpktcount=0,totpktcount=0,tottcppktcount=0,totudppktcount=0,toticmppktcount=0,tdoscount=0,probecount=0,udoscount=0,idoscount=0,tottdoscount=0,totprobecount=0,totudoscount=0,totidoscount=0,Acount=0,Ncount=0,prevtime=0,posttime=0,j,prev,post,k;
    Integer o,attackval=0,normalval,temp=0,totpkt=0,pkt=0;
int a=1;    
String lastfile;
    int NORMALPKTS=0, DOSPKTS=0, PROBEPKTS=0;
    void test(kNNTestPkt P[],int i) throws IOException{    
        
        int x=0;
        pkt=i;totpkt=totpkt+i;probecount=0;
        temp=0;
        String []protcl = new String[10];
        int WT[]=new int[10];
        
    for(o=0;o<i;o++)
        {
        
        P[o].wt=0;
       
        
        //NEAREST NO OF PACKETS DEPENDING ON THEIR PROTOCOL
        if(P[o].protocol.equals("17"))
         {
             //UDP
            TestudpProcess(P, o);
            
            udppktcount++;
            totpktcount++;
            
         }
        else if(P[o].protocol.equals("01"))
         {
             //ICMP Protocol
            TesticmpProcess(P, o);
            icmppktcount++;
            totpktcount++;
         }
        else if(P[o].protocol.equals("06"))
         {
             //TCP Protocol
             TesttcpProcess(P, o);
             tcppktcount++;
             totpktcount++;
         }       
        else
        {
            TestProbeProcess(P,o);
            oderpktcount++;
            totpktcount++;
        }
        
        System.out.println();
        System.out.print(P[o].port);
        System.out.print("\t"+P[o].protocol);
        System.out.print("\t"+P[o].seq_no);
        System.out.print("\t"+P[o].size);
        System.out.print("\t"+P[o].src);
        System.out.print("\t"+P[o].status);
        System.out.print("\t"+P[o].time);
        System.out.print("\t"+P[o].type);
        System.out.print("\t"+ P[o].wt);
        if(P[o].ans.equals("NORMAL"))
            NORMALPKTS++;
        if(P[o].ans.equals("PROBE"))
            PROBEPKTS++;
        if(P[o].ans.equals("DOS"))
            DOSPKTS++;
        System.out.print("\t"+ P[o].ans);
        
        
    }
    
    }
   
    private void TestudpProcess(kNNTestPkt[] P, Integer o) {
        //UDP Process
     for(int y=0;y<20;y++)
     {
        if(P[o].protocol.equals(P[y].protocol))
        {
            if(P[o].port.equals(P[y].port))
            {
                if(P[o].src.equals(P[o].src))
                    {
                        if(P[o].seq_no.equals("00000000"))
                        P[o].wt=17;
                        int wt=P[o].wt;
                        //Find Nearest Neighbor...
                        P[o].ans= findNeighbour(wt);
                            
                    }
            }
        }
     }
    }

    private void TesttcpProcess(kNNTestPkt[] P, Integer o) {
      for(int y=0;y<20;y++)
     {
        if(P[o].protocol.equals(P[y].protocol))
        {
            if(P[o].port.equals(P[y].port))
            {
                if(P[o].src.equals(P[o].src))
                    {
                        if(P[o].seq_no.equals("00000000"))
                        P[o].wt=06;
                        int wt=P[o].wt;
                        //Find Nearest Neighbor...
                        P[o].ans= findNeighbour(wt);
                            
                    }
                
            }
        }
     }  
    }

    private void TestProbeProcess(kNNTestPkt[] P, Integer o) {
     for(int y=0;y<20;y++)
     {
        if(P[o].protocol.equals(P[y].protocol))
        {
            if(P[o].port.equals(P[y].port))
            {
                if(P[o].type.equals(P[y].type))
                  if(P[o].src.equals(P[o].src))
                    {
                        P[o].wt=100;
                        int wt=P[o].wt;
                        //Find Nearest Neighbor...
                        P[o].ans= findNeighbour(wt);
                            
                    }
                    else
                        TestoderProcess(P,o);
                
            }
            else
                TestoderProcess(P, o);
        }
     }
    }
private void TesticmpProcess(kNNTestPkt[] P, Integer o) {
        for(int y=0;y<20;y++)
     {
        if(P[o].protocol.equals(P[y].protocol))
        {
            if(P[o].port.equals(P[y].port))
            {
                if(P[o].src.equals(P[o].src))
                    {
                        if(P[o].seq_no.equals("00000000"))
                        P[o].wt=01;
                        int wt=P[o].wt;
                        //Find Nearest Neighbor...
                        P[o].ans= findNeighbour(wt);
                    }
            }
        }
     }
    }
private void TestoderProcess(kNNTestPkt[] P, Integer o) {
        for(int y=0;y<20;y++)
     {
          P[o].wt=0;
          int wt=P[o].wt;
          //Find Nearest Neighbor...
          P[o].ans= findNeighbour(wt);
                   
     }
    }

    
public String findNeighbour(int wt)
{
    String ans = null;
    if(wt==1)
    {
        //ICMP
        ans="DOS";
        
    }
    else if(wt==17)
    {
        //UDP
        ans="DOS";
        
    }
    else if(wt==06)
    {
        //TCP
        ans="DOS";
        
    }
    else if(wt==100)
    {
        ans="PROBE";
        
    }
    else if(wt==0)
    {
        //NORMAL
        ans="NORMAL";
        
    }
    return ans;
}
void writeLog(File infile) throws FileNotFoundException, IOException{        
        Long time=System.nanoTime();
        String line;        
        String name=Calendar.DATE+time.toString();
        //File log
        File f=new File("C:/SMAJ-RTNIDS/KNNLog/Test/"+name+".txt");
        lastfile="C:/SMAJ-RTNIDS/KNNLog/Test/"+name+".txt";
        FileInputStream fi=new FileInputStream(infile);
        BufferedReader bin=new BufferedReader(new InputStreamReader(fi));
        FileOutputStream fo =new FileOutputStream(f);
        BufferedWriter bout=new BufferedWriter(new OutputStreamWriter(fo));
        line=bin.readLine();
        while(line!=null){
            bout.append(line);
            bout.newLine();
            line=bin.readLine();            
        }
        bin.close();
        //Result analysis file
        File res=new File("C:/SMAJ-RTNIDS/KNNLog/RESULT/"+name+".txt");
        FileOutputStream fres =new FileOutputStream(res);
        BufferedWriter bres=new BufferedWriter(new OutputStreamWriter(fres));
        bout.newLine();            
        bout.append("NORMAL PACKETS:\t"+ NORMALPKTS+ "\tDOS ATTACK PACKETS:\t"+ DOSPKTS + "\tPROBE ATTACK PACKETS:\t" + PROBEPKTS);
        bout.newLine();
        bout.append("---------------------------------------------");
        bout.newLine();
        bout.append("TOTAL PACKETS:"+(NORMALPKTS+DOSPKTS+PROBEPKTS));
        bout.close();
        
        bres.newLine();            
        bres.append("NORMAL PACKETS: "+ NORMALPKTS);
        bres.newLine();
        bres.append("DOS ATTACK PACKETS: "+ DOSPKTS);
        bres.newLine();
        bres.append("PROBE ATTACK PACKETS: " + PROBEPKTS);
        bres.newLine();
        bres.append("----------------------------------------------");
        bres.newLine();
        bres.append("TOTAL PACKETS:"+(NORMALPKTS+DOSPKTS+PROBEPKTS));
        bres.newLine();
        bres.append("----------------------------------------------");
        bres.close();
    }
   }
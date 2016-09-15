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

public class kNNTrain {    
    Integer dosattack=0,probeattack=0,tcppktcount=0,udppktcount=0,icmppktcount=0,oderpktcount=0,totpktcount=0,tottcppktcount=0,totudppktcount=0,toticmppktcount=0,tdoscount=0,probecount=0,udoscount=0,idoscount=0,tottdoscount=0,totprobecount=0,totudoscount=0,totidoscount=0,Acount=0,Ncount=0,prevtime=0,posttime=0,j,prev,post,k;
    Integer o,attackval=0,normalval,temp=0,totpkt=0,pkt=0;
    int NORMALPKTS=0, DOSPKTS=0, PROBEPKTS=0, TOTALPKTS=0;
 int doscorrectcount=0,probecorrectcount=0, normalcorrectcount=0,dosincorrectcount=0,probeincorrectcount=0, normalincorrectcount=0;
    int DOSTOT=0,PROBETOT=0,NORMALTOT=0;
int nvppktcount=0;
int iso_ippktcount=0;
String lastfile;
    
    void train(KNNTrainPkt P[],int i) throws IOException{    
        
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
             udppktcount++;
            udpProcess(P, o);
         
            
         }
        else if(P[o].protocol.equals("01"))
         {
             //ICMP Protocol
             icmppktcount++;
            icmpProcess(P, o);
            
         }
        else if(P[o].protocol.equals("06"))
         {
             //TCP Protocol
             tcppktcount++;
             tcpProcess(P, o);
             
         }   
        else if(P[o].protocol.equals("11"))
         {
             //TCP Protocol
             nvppktcount++;
             nvpProcess(P, o);
             
         }   
        else if(P[o].protocol.equals("37"))
         {
             //DATAGRAM DELIVERY PROTOCOL
             probecount++;
             ProbeProcess(P, o);
         }  
        else if(P[o].protocol.equals("80"))
         {
             //TCP Protocol
             iso_ippktcount++;
             iso_ipProcess(P, o);
             
         }  
        
        else
        {
            //Is the packet a Probe?? 
            probecount++;
            ProbeProcess(P,o);
        }
        //writeLog(P,o);
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
        {
            if(P[o].myans.equals(P[o].ans))
                normalcorrectcount++;
            else
                normalincorrectcount++;
            NORMALPKTS++;
        }
        if(P[o].ans.equals("PROBE"))
        {
            if(P[o].myans.equals(P[o].ans))
                probecorrectcount++;
            else
                probeincorrectcount++;
            PROBEPKTS++;
        }
        if(P[o].ans.equals("DOS"))
        {
            DOSPKTS++;
            if(P[o].myans.equals(P[o].ans))
            doscorrectcount++;
            else
            {
                dosincorrectcount++;
               
            }
        }
        System.out.print("\t"+P[o].ans);
        System.out.println("\t"+P[o].myans);
            TOTALPKTS++;
            
        if(P[o].ans.equals("DOS"))
            DOSTOT++;
        if(P[o].ans.equals("PROBE"))
            PROBETOT++;
        if(P[o].ans.equals("NORMAL"))
            NORMALTOT++;
    }
    
    }
   
private void icmpProcess(KNNTrainPkt[] P, Integer o) {
    //System.out.println("Packets of ICMP Type are to be processed");
     for(int y=0;y<20;y++)
     {
       
         if(P[o].protocol.equals(P[y].protocol) && P[o].port.equals(P[y].port) && P[o].src.equals(P[y].src))
         {    P[o].myans="DOS";
                            P[o].wt=01;
         }
         else if (P[o].seq_no.equals("00000000"))
         {    P[o].myans="DOS";
                            P[o].wt=01;
         }   
         else
             ProbeProcess(P, o);
         //else
          //oderProcess(P, o);     
                        totpktcount++;
             
     }
}    

private void udpProcess(KNNTrainPkt[] P, Integer o) {
    //System.out.println("Packets of UDP Type are to be processed");
     for(int y=0;y<20;y++)
     {
       
        if(P[o].protocol.equals(P[y].protocol) && P[o].port.equals(P[y].port) && P[o].src.equals(P[y].src) )
         {    P[o].myans="DOS";
                            P[o].wt=17;
         }
         else if (P[o].seq_no.equals("00000000"))
         {    P[o].myans="DOS";
                            P[o].wt=17;
         }   
         else
             ProbeProcess(P, o);
         //else
          //oderProcess(P, o);     
                        totpktcount++;
                
     }
}

private void tcpProcess(KNNTrainPkt[] P, Integer o) {
    //System.out.println("Packets of TCP Type are to be processed");
     for(int y=0;y<20;y++)
     {
        
         if(P[o].protocol.equals(P[y].protocol)  && P[o].src.equals(P[y].src) && P[o].seq_no.equals(P[y].seq_no))
         {    P[o].myans="DOS";
                            P[o].wt=06;
         }
         else if (P[o].seq_no.equals("00000000"))
         {    P[o].myans="DOS";
                            P[o].wt=06;
         }   
         else
             ProbeProcess(P, o);
         //else
          //oderProcess(P, o);     
                        totpktcount++;
              
     }
}
private void iso_ipProcess(KNNTrainPkt[] P, Integer o) {
    //System.out.println("Packets of TCP Type are to be processed");
     for(int y=0;y<20;y++)
     {
        
         if(P[o].protocol.equals(P[y].protocol)  && P[o].src.equals(P[y].src) && P[o].seq_no.equals(P[y].seq_no))
         {    P[o].myans="DOS";
                            P[o].wt=80;
         }
           
         else
             ProbeProcess(P, o);
         //else
          //oderProcess(P, o);     
                        totpktcount++;
              
     }
}


private void nvpProcess(KNNTrainPkt[] P, Integer o) {
    //System.out.println("Packets of TCP Type are to be processed");
     for(int y=0;y<20;y++)
     {
        
         if(P[o].protocol.equals(P[y].protocol)  && P[o].src.equals(P[y].src))
         {    P[o].myans="DOS";
                            P[o].wt=11;
         }
         else if (P[o].seq_no.equals("00000000"))
         {    P[o].myans="DOS";
                            P[o].wt=11;
         }   
         else
             ProbeProcess(P, o);
         //else
          //oderProcess(P, o);     
                        totpktcount++;
              
     }
}
private void oderProcess(KNNTrainPkt[] P, Integer o) {
    
     for(int y=0;y<20;y++)
     {
              
         
         P[o].myans="NORMAL";         
                  P[o].wt=0;
         
            
             totpktcount++;
     }
}
private void ProbeProcess(KNNTrainPkt[] P, Integer o) {
    for(int y=0;y<20;y++)
     {
         if(P[o].src.equals(P[y].src) && P[0].seq_no.equals(P[y].seq_no)&& P[o].type.equals(P[y].type))
         {    P[o].myans="PROBE";
                            P[o].wt=100;
         }
         else if (P[o].seq_no.equals("00000000"))
          {    P[o].myans="PROBE";
                            P[o].wt=100;
         }   
         else
          oderProcess(P, o); 
        }
    }

  void writeLog(File infile) throws FileNotFoundException, IOException{        
        Long time=System.nanoTime();
        String line;        
        String name=Calendar.DATE+time.toString();
        File f=new File("C:/SMAJ-RTNIDS/KNNLog/Train/"+name+".txt");
        lastfile="C:/SMAJ-RTNIDS/KNNLog/Train/"+name+".txt";
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
        File res=new File("C:/SMAJ-RTNIDS/KNNLog/RESULT/"+name+".txt");
        FileOutputStream fres =new FileOutputStream(res);
        BufferedWriter bres=new BufferedWriter(new OutputStreamWriter(fres));
        bout.newLine();            
        bout.append("NORMAL PACKETS: "+ NORMALPKTS+ "\tDOS ATTACK PACKETS: "+ DOSPKTS + "\tPROBE ATTACK PACKETS: " + PROBEPKTS);
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
        bres.append("TOTAL PACKETS:"+ TOTALPKTS);
        bres.newLine();
        bres.append("----------------------------------------------");
        bres.newLine();        
        bres.append("\tCORRECT\tINCORRECT\tTOTAL");
        bres.newLine();
        bres.append("DOS\t"+ doscorrectcount+ "\t"+  dosincorrectcount+ "\t"+ DOSTOT);
        bres.newLine();
        bres.append("PROBE\t"+ probecorrectcount+ "\t"+  probeincorrectcount+ "\t"+ PROBETOT);
        bres.newLine();
        bres.append("NORMAL\t"+ normalcorrectcount+ "\t"+  normalincorrectcount+ "\t"+ NORMALTOT);
        bres.close();
     }
}
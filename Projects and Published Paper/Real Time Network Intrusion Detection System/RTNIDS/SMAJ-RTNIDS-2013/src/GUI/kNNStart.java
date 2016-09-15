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
import java.util.StringTokenizer;

public class kNNStart {
    public static kNNTrain Train;
    public int doscrt,dosincrt,probecrt,probeincrt,norcrt,norincrt;
    public static kNNTest Test;
    String TrainFile, TestFile,ResultFile,AnalysisFile,AnalyseFile;
    KNNTrainPkt trainPkt[]=new KNNTrainPkt[20];
    kNNTestPkt ktestPkt[]=new kNNTestPkt[20];
    boolean status;
    int i;
    String fname,path;
    File infile;
    FileInputStream in = null;
    String line;
    StringTokenizer st;
    BufferedReader bin;
    int o;
    String ktrpath,ktestpath;
    
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        kNNStart dg=new kNNStart();
       // File f =new File("C:/SMAJ-RTNIDS/TRAINING FILES/DOS1.txt");
       File f =new File("C:/SMAJ-RTNIDS/convertedtstest.txt");
        // dg.genkNNTrain(f);  
        convertdatats cds=new convertdatats();
            cds.accept("D:/Captured Data/DOS2.txt");
       dg.genkNNTest(f);
    }
   
    void initTrainPkt()
    {
        //Initialising the packets to be trained
        for(o=0;o<20;o++){
            trainPkt[o]=new KNNTrainPkt();
        }
    }
    void initTestPkt()
    {
        //Initialising the packets to be trained
        for(o=0;o<20;o++){
            ktestPkt[o]=new kNNTestPkt();
        }
    }
    
    void genkNNAnalyse(File infile) throws FileNotFoundException, IOException{
        int pktcount=0;
       try{
        Train=new kNNTrain();
        //infile=new File("C:/SMAJ-RTNIDS/P1.txt");
        in = new FileInputStream(infile);
        path =infile.getPath();
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        initTrainPkt();
        while(true){
            o=0;
            while(o<20){
                line=bin.readLine();                
                pktcount++;
                if(line==null)
                {                    
                    Train.train(trainPkt, o);            
                    bin.close();
                    pktcount--;
                    return;
                }
                status=trainPkt[o].initpkt(line);
                if(status==true)
                    o++;                  
                            
            }
        System.out.println("COUNT" +o);
        Train.train(trainPkt, o);    

        }
    }
        catch(Exception e)
        {
            System.out.println("Exception Occured!!!" + e);
        }
       finally
       {
           Train.writeLog(infile);
           TrainFile=Train.lastfile;
           genkNNTrainResult(path,Train);
           Analysis a=new Analysis();
           a.Analyse(Train);
           AnalysisFile= a.lastfile;
           //ChartPanel cp =new ChartPanel(Train);
           System.out.println("Finished");
       }
    }
    
    void genkNNTrain(File infile) throws FileNotFoundException, IOException{
        int pktcount=0;
       try{
        Train=new kNNTrain();
        //infile=new File("C:/SMAJ-RTNIDS/P1.txt");
        in = new FileInputStream(infile);
        path =infile.getPath();
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        initTrainPkt();
        while(true){
            o=0;
            while(o<20){
                line=bin.readLine();                
                pktcount++;
                if(line==null)
                {                    
                    Train.train(trainPkt, o);            
                    bin.close();
                    pktcount--;
                    return;
                }
                status=trainPkt[o].initpkt(line);
                if(status==true)
                    o++;                  
                            
            }
        System.out.println("COUNT" +o);
        Train.train(trainPkt, o);    

        }
    }
        catch(Exception e)
        {
            System.out.println("Exception Occured!!!" + e);
        }
       finally
       {
           Train.writeLog(infile);
           TrainFile=Train.lastfile;
           genkNNTrainResult(path,Train);
           //ChartPanel cp =new ChartPanel(Train);
           System.out.println("Finished");
       }
    }
    
    void genkNNTest(File infile) throws FileNotFoundException, IOException{
        int pktcount=0;
       try{
        Test=new kNNTest();
        //infile=new File("C:/SMAJ-RTNIDS/Convertedtstest.txt");
        //CONVERT THE FILE TO TAB SEPARATED FORMAT
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        initTestPkt();
        while(true){
            o=0;
            while(o<20){
                line=bin.readLine();                
                pktcount++;
                if(line==null)
                {                    
                    Test.test(ktestPkt, o);            
                    bin.close();
                    pktcount--;
                    return;
                }
                status=ktestPkt[o].initpkt(line);
                if(status==true)
                    o++;                  
                            
            }
        System.out.println("COUNT" +o);
        Test.test(ktestPkt, o);

        }
    }
        catch(Exception e)
        {
            System.out.println("Exception Occured!!!" + e);
        }
       finally
       {
           Test.writeLog(infile);
           TestFile=Test.lastfile;
           System.out.println("Finished");
       }
    }
    
  
  
  void genkNNTrainResult(String path,kNNTrain kt1) throws FileNotFoundException, IOException{
        KNNTrainPkt pkt=new KNNTrainPkt();
        kNNTrain kt= new kNNTrain();
        Long time=System.nanoTime();
        String name=Calendar.DATE+time.toString();
        File f=new File("C:/SMAJ-RTNIDS/KNNLog/Result/"+name+".txt");
        ResultFile= "C:/SMAJ-RTNIDS/KNNLog/Result/"+name+".txt";
        FileOutputStream fo =new FileOutputStream(f);
        BufferedWriter bout=new BufferedWriter(new OutputStreamWriter(fo));
        Integer dosans=0,probeans=0,normalans=0,cdos,idos,cprobe,iprobe,cnormal,inormal,ctotal,itotal;
        infile=new File(path);
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        bout.append(line);
        
        int totpkt =(kt.NORMALPKTS+ kt.DOSPKTS +kt.PROBEPKTS);
        while(true){
                line=bin.readLine();                
                if(line==null)
                {                    
                    bout.newLine();            
                    bout.append("Total Packet: "+(kt.NORMALPKTS+ kt.DOSPKTS +kt.PROBEPKTS));
                    bout.newLine();
                    bout.append("TCP: "+kt.tcppktcount+"  UDP: "+kt.udppktcount+"  ICMP: "+kt.icmppktcount);
                    bout.newLine();            
                    //kt.Ncount=totpkt-(dtTrain.dosattack+dtTrain.probeattack);
                    bout.append("Total Input Packet: "+totpkt+"    Dos Count "+kt.DOSPKTS+"  Probe: "+kt.PROBEPKTS+"  Normal: "+kt.NORMALPKTS);
                    bout.close();
                    
                    f=new File("C:/SMAJ-RTNIDS/KnnResult.csv");
                    fo =new FileOutputStream(f);
                    bout=new BufferedWriter(new OutputStreamWriter(fo));                                        
                    bout.append("Number,Type,Correct,Incorrect,Total");
                    bout.newLine();
                    Integer cr,icr,tot;
                    cr=kt1.doscorrectcount;
                    icr=kt1.dosincorrectcount;
                    tot=cr+icr;
                    doscrt=cr;
                    dosincrt=icr;
                    bout.append("1.,DOS,"+cr.toString()+","+icr.toString()+","+tot.toString());                    
                    bout.newLine();
                   
                    cr=kt1.probecorrectcount;
                    icr=kt1.probeincorrectcount;
                    tot=cr+icr;
                    probecrt=cr;
                    probeincrt=icr;
                    bout.append("2.,PROBE,"+cr.toString()+","+icr.toString()+","+tot.toString());                    
                    bout.newLine();
                    
                    cr=kt1.normalcorrectcount;
                    icr=kt1.normalincorrectcount;
                    tot=cr+icr;
                    norcrt=cr;
                    norincrt=icr;
                    bout.append("3.,NORMAL,"+cr.toString()+","+icr.toString()+","+tot.toString());                    
                    bout.newLine();
                    
                    cr=kt1.doscorrectcount+kt1.normalcorrectcount+kt1.probecorrectcount;
                    icr=kt1.dosincorrectcount+kt1.normalincorrectcount+kt1.probeincorrectcount;
                    tot=cr+icr;
                    bout.append("4.,TOTAL,"+cr.toString()+","+icr.toString()+","+tot.toString());                    
                    
                    bout.close();
                    bin.close();
                    
                    return;
                    
                }
            }
 
        
        
        }

    
   
  
  void genkNNAnalyseResult(String path,kNNTrain kt1) throws FileNotFoundException, IOException{
        KNNTrainPkt pkt=new KNNTrainPkt();
        kNNTrain kt= new kNNTrain();
        Long time=System.nanoTime();
        String name=Calendar.DATE+time.toString();
        File f=new File("C:/SMAJ-RTNIDS/KNNLog/Analysis/"+name+".txt");
        AnalyseFile= "C:/SMAJ-RTNIDS/KNNLog/Analysis/"+name+".txt";
        FileOutputStream fo =new FileOutputStream(f);
        BufferedWriter bout=new BufferedWriter(new OutputStreamWriter(fo));
        Integer dosans=0,probeans=0,normalans=0,cdos,idos,cprobe,iprobe,cnormal,inormal,ctotal,itotal;
        infile=new File(path);
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        bout.append(line);
        
        int totpkt =(kt.NORMALPKTS+ kt.DOSPKTS +kt.PROBEPKTS);
        while(true){
                line=bin.readLine();                
                if(line==null)
                {   
                    bout.newLine();
                    bout.append("ANALYSIS:-");
                    bout.newLine();            
                    bout.append("Total Packet: "+(kt.NORMALPKTS+ kt.DOSPKTS +kt.PROBEPKTS));
                    bout.newLine();
                    bout.append("TCP: "+kt.tcppktcount+"  UDP: "+kt.udppktcount+"  ICMP: "+kt.icmppktcount);
                    bout.newLine();            
                    //kt.Ncount=totpkt-(dtTrain.dosattack+dtTrain.probeattack);
                    bout.append("Total Input Packet: "+totpkt+"    Dos Count "+kt.DOSPKTS+"  Probe: "+kt.PROBEPKTS+"  Normal: "+kt.NORMALPKTS);
                    bout.newLine(); 
                    
                                                          
                    bout.append("Number,Type,Correct,Incorrect,Total");
                    bout.newLine();
                    Integer cr,icr,tot;
                    cr=kt.doscorrectcount;
                    icr=kt.dosincorrectcount;
                    tot=cr+icr;
                    doscrt=cr;
                    dosincrt=icr;
                    bout.append("1.,DOS,"+cr.toString()+","+icr.toString()+","+tot.toString());                    
                    bout.newLine();
                   
                    cr=kt.probecorrectcount;
                    icr=kt.probeincorrectcount;
                    tot=cr+icr;
                    probecrt=cr;
                    probeincrt=icr;
                    bout.append("2.,PROBE,"+cr.toString()+","+icr.toString()+","+tot.toString());                    
                    bout.newLine();
                    
                    cr=kt.normalcorrectcount;
                    icr=kt.normalincorrectcount;
                    tot=cr+icr;
                    norcrt=cr;
                    norincrt=icr;
                    bout.append("3.,NORMAL,"+cr.toString()+","+icr.toString()+","+tot.toString());                    
                    bout.newLine();
                    
                    cr=kt.doscorrectcount+kt.normalcorrectcount+kt.probecorrectcount;
                    icr=kt.dosincorrectcount+kt.normalincorrectcount+kt.probeincorrectcount;
                    tot=cr+icr;
                    bout.append("4.,TOTAL,"+cr.toString()+","+icr.toString()+","+tot.toString());                    
                    
                    bout.close();
                    bin.close();
                    
                    return;
                    
                }
            }
 
        
        
        }

    
   
}    
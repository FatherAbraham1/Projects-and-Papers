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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class DATAGEN {
    DecTreeTest dtTest;
    DecTree dtf;
    DecTreeAnalyseTest dtAnTest;
    DecTreeTrain dtTrain;    
    AnalyseDecTree dtAnalyse;
    TestPkt testPkt[]=new TestPkt[20];        
    TrainPkt trainPkt[]=new TrainPkt[20];
    boolean status;
    int i;
    SelectFrame sf;
    File infile;
    FileInputStream in = null;
    String line,lastfile,lasttrain,lasttest,lasttestlog;
    StringTokenizer st;
    BufferedReader bin;
    int o;
    
    Integer DetDos,DetProbe,DetNorm,ExDos,ExProbe,ExNorm;

    
    public static void main(String args[]) throws FileNotFoundException, IOException{    
        //DATAGEN dg=new DATAGEN();
        //dg.genDecTreeTrain("C:/SMAJ-RTNIDS/Convertedtstest5.txt");  
    }

    void initTrainPkt(){     
        for(o=0;o<20;o++){
            trainPkt[o]=new TrainPkt();
        }
    }
    //ACTUAL TESTING BEGINS
    void genDecTreeTest() throws FileNotFoundException, IOException{
        int pktcount=0;
        dtTest=new DecTreeTest();
            //TAKE FORMATTED INPUT FILE        
        infile=new File("C:/SMAJ-RTNIDS/Convertedtstest.txt");
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        initTestPkt();
            //PROCESS PACKETS IN GROUPS OF 20
        while(true){
            o=0;
            while(o<20){
                line=bin.readLine();                
                pktcount++;
                if(line==null)
                {                    
                    
                    dtTest.test(testPkt, o);            
                    bin.close();
                    dtTest.dispTcpDos();
                    dtTest.dispUdpDos();
                    dtTest.dispIcmpDos();
                    dtTest.dispProbe();
                    pktcount--;
                    genDecTreeTestResult(pktcount);
                    return;
                }
                status=testPkt[o].initpkt(line);
                if(status==true)
                    o++;                   
            }
            if(o!=0)
            dtTest.test(testPkt, o);            
        }
    }
    
    
    void genDecTreeAnalyse(String path) throws FileNotFoundException, IOException{
        int pktcount=0;
        dtAnalyse=new AnalyseDecTree();
            //TAKE FORMATTED INPUT FILE        
        infile=new File(path);
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        initTrainPkt();
            //PROCESS PACKETS IN GROUPS OF 20
        while(true){
            o=0;
            while(o<20){
                line=bin.readLine();                
                pktcount++;
                if(line==null)
                {                    
                    
                    dtAnalyse.test(trainPkt, o);            
                    bin.close();
                    dtAnalyse.dispTcpDos();
                    dtAnalyse.dispUdpDos();
                    dtAnalyse.dispIcmpDos();
                    dtAnalyse.dispProbe();
                    pktcount--;
                    genDecTreeAnalyseResult(pktcount,path);
                    return;
                }
                status=trainPkt[o].initpkt(line);
                if(status==true)
                    o++;                   
            }
            if(o!=0)
            dtAnalyse.test(trainPkt, o);            
        }
    }
    
    
    void genDecTreeTrain(String path) throws FileNotFoundException, IOException{
        int pktcount=0;
        dtTrain=new DecTreeTrain();
        infile=new File(path);
        in = new FileInputStream(infile);
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
                    dtTrain.train(trainPkt, o);            
                    bin.close();
                    dtTrain.dispTcpDos();
                    dtTrain.dispUdpDos();
                    dtTrain.dispIcmpDos();
                    dtTrain.dispProbe();
                    pktcount--;
                    genDecTreeTrainResult(pktcount,path);
                    return;
                }
                status=trainPkt[o].initpkt(line);
                
                if(status==true)                
                {
                    trainPkt[o].printpkt();
                    o++;                   
                }
            }
            dtTrain.train(trainPkt, o);            
        }
        
    }
    
    
    void genDecTreeAnalyseTest(String path,Integer x,Integer y,Integer z) throws FileNotFoundException, IOException{
        int pktcount=0;
        dtAnTest=new DecTreeAnalyseTest();
        infile=new File(path);
        in = new FileInputStream(infile);
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
                    dtAnTest.analyseTest(trainPkt, o);            
                    bin.close();
                    dtAnTest.dispTcpDos();
                    dtAnTest.dispUdpDos();
                    dtAnTest.dispIcmpDos();
                    dtAnTest.dispProbe();
                    pktcount--;
                    genDecTreeAnalyseTestResult(pktcount,path,x,y,z);
                    return;
                }
                status=trainPkt[o].initpkt(line);
                
                if(status==true)                
                {
                    trainPkt[o].printpkt();
                    o++;                   
                }
            }
            dtAnTest.analyseTest(trainPkt, o);            
        }
        
    }
    
    void initTestPkt(){
        for(o=0;o<20;o++){
            testPkt[o]=new TestPkt();
        }
        
    }
    
    void genDecTreeTestResult(int totpkt) throws FileNotFoundException, IOException{
        TestPkt pkt=new TestPkt();
        dtf=new DecTree();
        String name=dtf.getTest();
        
        File f=new File(name+".txt");        
        lasttestlog=name+".txt";
        FileOutputStream fo =new FileOutputStream(f);
        BufferedWriter bout=new BufferedWriter(new OutputStreamWriter(fo));
        
        infile=new File("C:/SMAJ-RTNIDS/Convertedtstest.txt");
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        bout.append(line);
        
        while(true){
                line=bin.readLine();                
                if(line==null)
                {                    
                    bout.newLine();            
                    bout.append("Total Packet: "+dtTest.totpkt+"    TCP: "+dtTest.tottcppktcount+"  UDP: "+dtTest.totudppktcount+"  ICMP: "+dtTest.toticmppktcount+"  Attack: "+dtTest.Acount);
                    bout.newLine();            
                    dtTest.Ncount=totpkt-(dtTest.dosattack+dtTest.probeattack);
                    bout.append("Total Input Packet: "+totpkt+"    Dos Count "+dtTest.dosattack+"  Probe: "+dtTest.probeattack+"  Normal: "+dtTest.Ncount);
                    
                    
                    bout.newLine();            
                    bout.append("****************************************************************************************************");
                    bout.newLine();            
                    bout.append("Number  Type    Detected");
                    bout.newLine();            
                    bout.append("1.      DOS      "+dtTest.dosattack.toString());                    
                    bout.newLine();            
                    bout.append("2.      Probe    "+dtTest.probeattack.toString());
                    bout.newLine();            
                    bout.append("3.      Normal  "+dtTest.Ncount.toString());
                    bout.newLine();            
                    bout.append("4.      Total(Processed)   "+dtTest.totpkt.toString());
                    bout.newLine();            
                    bout.append("5.      Total(Input)   "+totpkt);
                    DetDos=dtTest.dosattack;
                    DetProbe=dtTest.probeattack;
                    DetNorm=dtTest.Ncount;
                    
                    bout.close();
                    dtf=new DecTree();
                    name=dtf.getResult();
                    f=new File(name+".csv");
                    fo =new FileOutputStream(f);
                    bout=new BufferedWriter(new OutputStreamWriter(fo));                                        
                    bout.append("Number,Type,Detected");
                    bout.newLine();            
                    bout.append("1.,DOS,"+dtTest.dosattack.toString());                    
                    bout.newLine();            
                    bout.append("2.,Probe,"+dtTest.probeattack.toString());
                    bout.newLine();            
                    bout.append("3.,Normal,"+dtTest.Ncount.toString());
                    bout.newLine();            
                    bout.append("4.,Total,"+dtTest.totpkt.toString());
                    bout.newLine();            
                    bout.append("5.,Total(Input),"+totpkt);
                    
                    lasttest=name+".csv";
                    bout.close();
                    bin.close();
                    
                    return;
                }
                status=pkt.initpkt(line);                
                bout.newLine();                
                if(status==true)
                    bout.append(line+dtTest.testResult(pkt));                    
                else
                    bout.append(line+"NORMAL");                    
            }            
        }        
   void genDecTreeAnalyseResult(int totpkt,String path) throws FileNotFoundException, IOException{
        TrainPkt pkt=new TrainPkt();
        dtf=new DecTree();
        String name=dtf.getAnalyse();
        
        File f=new File(name+".txt");        
        lasttestlog=name+".txt";
        FileOutputStream fo =new FileOutputStream(f);
        BufferedWriter bout=new BufferedWriter(new OutputStreamWriter(fo));
        
        infile=new File(path);
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        bout.append(line);
        
        while(true){
                line=bin.readLine();                
                if(line==null)
                {                    
                    bout.newLine();            
                    bout.append("Total Packet: "+dtAnalyse.totpkt+"    TCP: "+dtAnalyse.tottcppktcount+"  UDP: "+dtAnalyse.totudppktcount+"  ICMP: "+dtAnalyse.toticmppktcount+"  Attack: "+dtAnalyse.Acount);
                    bout.newLine();            
                    dtAnalyse.Ncount=totpkt-(dtAnalyse.dosattack+dtAnalyse.probeattack);
                    bout.append("Total Input Packet: "+totpkt+"    Dos Count "+dtAnalyse.dosattack+"  Probe: "+dtAnalyse.probeattack+"  Normal: "+dtAnalyse.Ncount);
                    
                    
                    bout.newLine();            
                    bout.append("****************************************************************************************************");
                    bout.newLine();            
                    bout.append("Number  Type    Detected");
                    bout.newLine();            
                    bout.append("1.      DOS      "+dtAnalyse.dosattack.toString());                    
                    bout.newLine();            
                    bout.append("2.      Probe    "+dtAnalyse.probeattack.toString());
                    bout.newLine();            
                    bout.append("3.      Normal  "+dtAnalyse.Ncount.toString());
                    bout.newLine();            
                    bout.append("4.      Total(Processed)   "+dtAnalyse.totpkt.toString());
                    bout.newLine();            
                    bout.append("5.      Total(Input)   "+totpkt);
                    DetDos=dtAnalyse.dosattack;
                    DetProbe=dtAnalyse.probeattack;
                    DetNorm=dtAnalyse.Ncount;
                    
                    bout.close();
                    dtf=new DecTree();
                    name=dtf.getResult();
                    f=new File(name+".csv");
                    fo =new FileOutputStream(f);
                    bout=new BufferedWriter(new OutputStreamWriter(fo));                                        
                    bout.append("Number,Type,Detected");
                    bout.newLine();            
                    bout.append("1.,DOS,"+dtAnalyse.dosattack.toString());                    
                    bout.newLine();            
                    bout.append("2.,Probe,"+dtAnalyse.probeattack.toString());
                    bout.newLine();            
                    bout.append("3.,Normal,"+dtAnalyse.Ncount.toString());
                    bout.newLine();            
                    bout.append("4.,Total,"+dtAnalyse.totpkt.toString());
                    bout.newLine();            
                    bout.append("5.,Total(Input),"+totpkt);
                    
                    lasttest=name+".csv";
                    bout.close();
                    bin.close();
                    
                    return;
                }
                status=pkt.initpkt(line);                
                bout.newLine();                
                if(status==true){
                    
                    bout.append(line+dtAnalyse.testResult(pkt));                    
                }
                else{
                    bout.append(line+"NORMAL");                    
                    
                }
            }            
        }        
   
    void genDecTreeAnalyseTestResult(int totpkt,String path,Integer normalans,Integer dosans,Integer probeans) throws FileNotFoundException, IOException{
        TrainPkt pkt=new TrainPkt();
        dtf=new DecTree();
        String name=dtf.getAnalyse();
        File f=new File(name+".txt");
        lastfile=name+".txt";
        FileOutputStream fo =new FileOutputStream(f);
        BufferedWriter bout=new BufferedWriter(new OutputStreamWriter(fo));
        Integer /*dosans=0,probeans=0,normalans=0,*/cdos,idos,cprobe,iprobe,cnormal,inormal,ctotal,itotal;
        infile = new File(path);
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        bout.append(line);
        
        while(true){
                line=bin.readLine();                
                if(line==null)
                {                    
                    bout.newLine();            
                    bout.append("Total Packet: "+dtAnTest.totpkt+"    TCP: "+dtAnTest.tottcppktcount+"  UDP: "+dtAnTest.totudppktcount+"  ICMP: "+dtAnTest.toticmppktcount+"  Attack: "+dtAnTest.Acount);
                    bout.newLine();            
                    dtAnTest.Ncount=totpkt-(dtAnTest.dosattack+dtAnTest.probeattack);
                    bout.append("Total Input Packet: "+totpkt+"    Dos Count "+dtAnTest.dosattack+"  Probe: "+dtAnTest.probeattack+"  Normal: "+dtAnTest.Ncount);
                    bout.newLine();            
                    bout.newLine();            
                    bout.append("********************************************* CORRECT INCORRECT TABLE *********************************************");
                    bout.newLine();            
                    Integer tp=0,fp=0,tn=0,fn=0;
                    Double tnr,tpr,fnr,far,accuracy,precision,sensitivity,specificity;
                    
                    if(dosans>dtAnTest.dosattack){
                        
                        idos=dosans-dtAnTest.dosattack;
                        cdos=dtAnTest.dosattack;
                        tp=cdos;
                        fp=idos;
                    }
                    else{
                        cdos=dosans;
                        idos=dtAnTest.dosattack-dosans;
                    }
                    ExDos=dosans;
                    DetDos=dtAnTest.dosattack;
                    
                    tp=cdos;
                    fp=idos;
                    
                    if(probeans>dtAnTest.probeattack){
                        iprobe=probeans-dtAnTest.probeattack;
                        cprobe=dtAnTest.probeattack;
                    }
                    else{
                        cprobe=probeans;
                        iprobe=dtAnTest.probeattack-probeans;
                    }
                    tp=tp+cprobe;
                    fp=fp+iprobe;
                    ExProbe=probeans;
                    DetProbe=dtAnTest.probeattack;
                    if(normalans>dtAnTest.Ncount)                       
                    {
                        cnormal=dtAnTest.Ncount;
                        inormal=normalans-dtAnTest.Ncount;
                    }
                    else
                    {
                        cnormal=normalans;
                        inormal=dtAnTest.Ncount-normalans;                    
                    }
                    ExNorm=normalans;
                    DetNorm=dtAnTest.Ncount;
                    
                    
                    
                    tn=cnormal;
                    fn=inormal;
                    ctotal=cdos+cprobe+cnormal;
                    itotal=idos+iprobe+inormal;
                    bout.append("Number\tType\tCorrect\tIncorrect");    
                    bout.newLine();           
                    
                    bout.append("1.\tDOS\t"+cdos.toString()+"\t"+idos.toString());                    
                    bout.newLine();           
                    
                    bout.append("2.\tPROBE\t"+cprobe.toString()+"\t"+iprobe.toString());                    
                    bout.newLine();
                    
                    bout.append("3.\tNORMAL\t"+cnormal.toString()+"\t"+inormal.toString());                    
                    bout.newLine();            
                    
                    bout.append("4.\tTOTAL\t"+ctotal.toString()+"\t"+itotal.toString());
                    
                    
                    tnr=tn.doubleValue()/(tn.doubleValue()+fp.doubleValue());
                    tpr=tp.doubleValue()/(tp.doubleValue()+fn.doubleValue());
                    far=fp/(tn.doubleValue()+fp.doubleValue());
                    fnr=fn.doubleValue()/(tp.doubleValue()+fn.doubleValue());
                    specificity=1-far;
                    sensitivity=1-fnr;
                    accuracy=(tp.doubleValue()+tn.doubleValue())/(tp.doubleValue()+tn.doubleValue()+fp.doubleValue()+fn.doubleValue());
                    precision=tp.doubleValue()/(tp.doubleValue()+fp.doubleValue());
                    bout.newLine();
                    
                    bout.append("********************************************* CONFUSION MATRIX *********************************************");
                    bout.newLine();
                    bout.append("\tPOSITIVE\tNEGATIVE");
                    bout.newLine();
                    bout.append("TRUE\t"+tp+"\t\t"+tn);
                    bout.newLine();
                    bout.append("FALSE\t"+fp+"\t\t"+fn);
                    bout.newLine();                   
                    bout.newLine();
                    
                    bout.append("********************************************* RATE CALCULATION *********************************************");
                    bout.newLine();
                    
                    bout.append("True Positive Rate: \t"+tpr);
                    bout.newLine();
                    bout.append("True Negative Rate: \t"+tnr);
                    bout.newLine();
                    bout.append("False Alarm Rate: \t"+far);
                    bout.newLine();
                    bout.append("False Negative Rate: \t"+fnr);
                    bout.newLine();
                    bout.append("Specificity: \t\t"+specificity);
                    bout.newLine();
                    bout.append("Sensitivity: \t\t"+sensitivity);
                    bout.newLine();
                    bout.append("Accuracy: \t\t"+accuracy);
                    bout.newLine();
                    bout.append("Precision: \t\t"+precision);                                                            
                    
                    bout.close();
                    f=new File("C:/SMAJ-RTNIDS/DT Result.csv");
                    fo =new FileOutputStream(f);
                    bout=new BufferedWriter(new OutputStreamWriter(fo));                                        
                    bout.append("Number,Type,DT Actual,DT Detected,DT Correct,DT Incorrect,DT Total");
                    bout.newLine();            
                    Integer temp=cdos+idos;
                    bout.append("1.,DOS,"+dosans+","+dtAnTest.dosattack+","+cdos.toString()+","+idos.toString()+","+temp.toString());                    
                    temp=cprobe+iprobe;
                    bout.newLine();           
                    bout.append("2.,PROBE,"+probeans+","+dtAnTest.probeattack.toString()+","+cprobe.toString()+","+iprobe.toString()+","+temp.toString());                    
                    temp=cnormal+inormal;
                    bout.newLine();
                    bout.append("3.,NORMAL,"+normalans.toString()+","+dtAnTest.Ncount.toString()+","+cnormal.toString()+","+inormal.toString()+","+temp.toString());                    
                    bout.newLine();      
                    temp=ctotal+itotal;
                    normalans+=dosans+probeans;
                    dosans=dtAnTest.dosattack+dtAnTest.probeattack+dtAnTest.Ncount;
                    bout.append("4.,Total,"+normalans.toString()+","+dosans.toString()+","+ctotal.toString()+","+itotal.toString()+","+temp.toString());
                    
                    bout.close();
                    
                    
                    bin.close();
                    
                    return;
                }
                status=pkt.initpkt(line);                
                bout.newLine();                
                if(status==true)
                {/*
                    if(pkt.ans.equals("NORMAL"))
                            normalans++;
                    if(pkt.ans.equals("DOS"))
                           dosans++;
                    if(pkt.ans.equals("PROBE"))
                            probeans++;
                    */
                    bout.append(line+'\t'+dtAnTest.analyseTestResult(pkt));                    
                }else
                    bout.append(line+'\t'+"NORMAL");                    
            }
            
        }        
    
    void genDecTreeTrainResult(int totpkt,String path) throws FileNotFoundException, IOException{
        TrainPkt pkt=new TrainPkt();
        String ans;
        dtf=new DecTree();
        String name=dtf.getTrain();
        File f=new File(name+".txt");
        FileOutputStream fo =new FileOutputStream(f);
        BufferedWriter bout=new BufferedWriter(new OutputStreamWriter(fo));
        File info=new File("C:/SMAJ-RTNIDS/Decision Tree/Train Log/Info Gain.txt");
        Integer dosans=0,probeans=0,normalans=0;        
        PrintWriter bout1 = new PrintWriter(new BufferedWriter(new FileWriter(info,true)));
                
        infile=new File(path);
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        bout.append(line);
        int dos=0,probe=0,norm=0;
        while(true){
                line=bin.readLine();
                if(line==null)
                {
                    bout.newLine();            
                    bout.append("Total Packet: "+dtTrain.totpkt+"    TCP: "+dtTrain.tottcppktcount+"  UDP: "+dtTrain.totudppktcount+"  ICMP: "+dtTrain.toticmppktcount+"  Attack: "+dtTrain.Acount);
                    bout.newLine();            
                    //dtTrain.Ncount=totpkt-(dtTrain.dosattack+dtTrain.probeattack);
                    dtTrain.Ncount=totpkt-(dosans+probeans);
                    
                    bout.append("Total Input Packet: "+totpkt+"    Dos Count "+dosans+"  Probe: "+probeans+"  Normal: "+normalans);
                    bout.newLine();            
                    bout.newLine();            
                    bout.append("****************************************************************************************************");
                    bout.newLine();            
                    DetDos=dosans;
                    DetProbe=probeans;
                    DetNorm=normalans;
                    
                    
                    if(dosans>dtTrain.dosattack){

                    }
                    else{
                    
                    }
                    
                    if(probeans>dtTrain.probeattack){
                    
                    }
                    else{
                    
                    }
                    if(normalans>dtTrain.Ncount)                       
                    {
                    
                    }
                    else
                    {
                    
                    }
                    
                    bout.close();
                    lasttrain=name+".txt";
                    bin.close();
                    
                    return;
                }
                status=pkt.initpkt(line);
                if(status==true)
                {
                    if(pkt.ans.equals("NORMAL"))
                            normalans++;
                    if(pkt.ans.equals("DOS"))
                           dosans++;
                    if(pkt.ans.equals("PROBE"))
                            probeans++;    
                    ans=dtTrain.trainResult(pkt);
                    if(ans.equals("DOS"))
                        dos++;
                    if(ans.equals("PROBE"))
                        probe++;
                    if(ans.equals("NORMAL"))
                        norm++;
                    
                    if(pkt.ans.equals(ans))
                    {
                        bout.newLine();
                        bout.append(line+"\t"+dtTrain.trainResult(pkt));                    
                    }
                    else
                    {
                        
                        bout.newLine();
                        bout.append(line+"\t"+ans);                    
                        bout1.append(line);
                        bout1.println();
                    }
                        
                }else
                {
                    bout.append(line+"\t"+"NORMAL");                    
                    normalans++;
                }
            }
            
        }    
    /*For direct analysis through training file as input*/
    
    void genDecTreeAnalyseTest(String path) throws FileNotFoundException, IOException{
        int pktcount=0;
        dtAnTest=new DecTreeAnalyseTest();
        infile=new File(path);
        in = new FileInputStream(infile);
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
                    dtAnTest.analyseTest(trainPkt, o);            
                    bin.close();
                    dtAnTest.dispTcpDos();
                    dtAnTest.dispUdpDos();
                    dtAnTest.dispIcmpDos();
                    dtAnTest.dispProbe();
                    pktcount--;
                    genDecTreeAnalyseTestResult(pktcount,path);
                    return;
                }
                status=trainPkt[o].initpkt(line);
                
                if(status==true)                
                {
                    trainPkt[o].printpkt();
                    o++;                   
                }
            }
            dtAnTest.analyseTest(trainPkt, o);            
        }
        
    }
    void genDecTreeAnalyseTestResult(int totpkt,String path) throws FileNotFoundException, IOException{
        TrainPkt pkt=new TrainPkt();
        dtf=new DecTree();
        
        String name=dtf.getAnalyse()+".txt";
        lastfile=name;
        File f=new File(name);
        FileOutputStream fo =new FileOutputStream(f);
        BufferedWriter bout=new BufferedWriter(new OutputStreamWriter(fo));
        Integer dosans=0,probeans=0,normalans=0,cdos,idos,cprobe,iprobe,cnormal,inormal,ctotal,itotal;
        infile=new File(path);
        in = new FileInputStream(infile);
        bin = new BufferedReader(new InputStreamReader(in) );
        line=bin.readLine();
        bout.append(line);
        
        while(true){
                line=bin.readLine();                
                if(line==null)
                {                    
                    bout.newLine();            
                    bout.append("Total Packet: "+dtAnTest.totpkt+"    TCP: "+dtAnTest.tottcppktcount+"  UDP: "+dtAnTest.totudppktcount+"  ICMP: "+dtAnTest.toticmppktcount+"  Attack: "+dtAnTest.Acount);
                    bout.newLine();            
                    dtAnTest.Ncount=totpkt-(dtAnTest.dosattack+dtAnTest.probeattack);
                    bout.append("Total Input Packet: "+totpkt+"    Dos Count "+dtAnTest.dosattack+"  Probe: "+dtAnTest.probeattack+"  Normal: "+dtAnTest.Ncount);
                    bout.newLine();            
                    bout.newLine();            
                    bout.append("****************************************************************************************************");
                    bout.newLine();            
                    Integer tp=0,fp=0,tn=0,fn=0;
                    Double tnr,tpr,fnr,far,accuracy,precision,sensitivity,specificity;
                    
                    /*
                    
                    */
                    if(dosans>dtAnTest.dosattack){
                        
                        idos=dosans-dtAnTest.dosattack;
                        cdos=dtAnTest.dosattack;
                        tp=cdos;
                        fp=idos;
                    }
                    else{
                        cdos=dosans;
                        idos=dtAnTest.dosattack-dosans;
                    }
                    ExDos=dosans;
                    DetDos=dtAnTest.dosattack;
                    tp=cdos;
                    fp=idos;
                    
                    if(probeans>dtAnTest.probeattack){
                        iprobe=probeans-dtAnTest.probeattack;
                        cprobe=dtAnTest.probeattack;
                    }
                    else{
                        cprobe=probeans;
                        iprobe=dtAnTest.probeattack-probeans;
                    }
                    
                    ExProbe=probeans;
                    DetProbe=dtAnTest.probeattack;
                    tp=tp+cprobe;
                    fp=fp+iprobe;
                    if(normalans>dtAnTest.Ncount)                       
                    {
                        cnormal=dtAnTest.Ncount;
                        inormal=normalans-dtAnTest.Ncount;
                    }
                    else
                    {
                        cnormal=normalans;
                        inormal=dtAnTest.Ncount-normalans;                    
                    }
                    
                    ExNorm=normalans;
                    DetNorm=dtAnTest.Ncount;
                    bout.append("********************************************* CORRECT INCORRECT TABLE *********************************************");
                    bout.newLine();            
                    tn=cnormal;
                    fn=inormal;
                    
                    ctotal=cdos+cprobe+cnormal;
                    itotal=idos+iprobe+inormal;
                    bout.append("Number\tType\tCorrect\tIncorrect");    
                    bout.newLine();           
                    
                    bout.append("1.\tDOS\t"+cdos.toString()+"\t"+idos.toString());                    
                    bout.newLine();           
                    
                    bout.append("2.\tPROBE\t"+cprobe.toString()+"\t"+iprobe.toString());                    
                    bout.newLine();
                    
                    bout.append("3.\tNORMAL\t"+cnormal.toString()+"\t"+inormal.toString());                    
                    bout.newLine();            
                    
                    bout.append("4.\tTOTAL\t"+ctotal.toString()+"\t"+itotal.toString());
                    
                    tnr=tn.doubleValue()/(tn.doubleValue()+fp.doubleValue());
                    tpr=tp.doubleValue()/(tp.doubleValue()+fn.doubleValue());
                    far=fp/(tn.doubleValue()+fp.doubleValue());
                    fnr=fn.doubleValue()/(tp.doubleValue()+fn.doubleValue());
                    specificity=1-far;
                    sensitivity=1-fnr;
                    accuracy=(tp.doubleValue()+tn.doubleValue())/(tp.doubleValue()+tn.doubleValue()+fp.doubleValue()+fn.doubleValue());
                    precision=tp.doubleValue()/(tp.doubleValue()+fp.doubleValue());
                    bout.newLine();
                    
                    bout.append("********************************************* CONFUSION MATRIX *********************************************");
                    bout.newLine();
                    bout.append("\tPOSITIVE\tNEGATIVE");
                    bout.newLine();
                    bout.append("TRUE\t"+tp+"\t\t"+tn);
                    bout.newLine();
                    bout.append("FALSE\t"+fp+"\t\t"+fn);
                    bout.newLine();                   
                    bout.newLine();
                    
                    bout.append("********************************************* RATE CALCULATION *********************************************");
                    bout.newLine();
                    
                    bout.append("True Positive Rate: \t\t"+tpr);
                    bout.newLine();
                    bout.append("True Negative Rate: \t"+tnr);
                    bout.newLine();
                    bout.append("False Alarm Rate:   \t\t"+far);
                    bout.newLine();
                    bout.append("False Negative Rate:\t"+fnr);
                    bout.newLine();
                    bout.append("Specificity: \t\t"+specificity);
                    bout.newLine();
                    bout.append("Sensitivity: \t\t"+sensitivity);
                    bout.newLine();
                    bout.append("Accuracy: \t\t"+accuracy);
                    bout.newLine();
                    bout.append("Precision: \t\t"+precision);                                                            
                    
                    bout.close();
                    f=new File("C:/SMAJ-RTNIDS/DT Result.csv");
                    fo =new FileOutputStream(f);
                    bout=new BufferedWriter(new OutputStreamWriter(fo));                                        
                    bout.append("Number,Type,DT Actual,DT Detected,DT Correct,DT Incorrect,DT Total");
                    bout.newLine();            
                    Integer temp=cdos+idos;
                    bout.append("1.,DOS,"+dosans+","+dtAnTest.dosattack+","+cdos.toString()+","+idos.toString()+","+temp.toString());                    
                    temp=cprobe+iprobe;
                    bout.newLine();           
                    bout.append("2.,PROBE,"+probeans+","+dtAnTest.probeattack.toString()+","+cprobe.toString()+","+iprobe.toString()+","+temp.toString());                    
                    temp=cnormal+inormal;
                    bout.newLine();
                    bout.append("3.,NORMAL,"+normalans.toString()+","+dtAnTest.Ncount.toString()+","+cnormal.toString()+","+inormal.toString()+","+temp.toString());                    
                    bout.newLine();      
                    temp=ctotal+itotal;
                    normalans+=dosans+probeans;
                    dosans=dtAnTest.dosattack+dtAnTest.probeattack+dtAnTest.Ncount;
                    bout.append("4.,Total,"+normalans.toString()+","+dosans.toString()+","+ctotal.toString()+","+itotal.toString()+","+temp.toString());
                    bout.close();
                    
                    return;
                }
                status=pkt.initpkt(line);                
                bout.newLine();                
                if(status==true)
                {
                    if(pkt.ans.equals("NORMAL"))
                            normalans++;
                    if(pkt.ans.equals("DOS"))
                           dosans++;
                    if(pkt.ans.equals("PROBE"))
                            probeans++;
                    
                    bout.append(line+'\t'+dtAnTest.analyseTestResult(pkt));                    
                }else
                    bout.append(line+'\t'+"NORMAL");                    
            }
            
        }
    
    /*Ends analysis through trainging file*/
}
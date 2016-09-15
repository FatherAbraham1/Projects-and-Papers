package GUI;

import java.io.IOException;

public class DecTreeAnalyseTest {    
    Integer dosattack=0,probeattack=0,tcppktcount=0,udppktcount=0,icmppktcount=0,tottcppktcount=0,totudppktcount=0,toticmppktcount=0,tdoscount=0,probecount=0,udoscount=0,idoscount=0,tottdoscount=0,totprobecount=0,totudoscount=0,totidoscount=0,Acount=0,Ncount=0,prevtime=0,posttime=0,j,prev,post,k,TP,TN,FP,FN,Sensitivity,Specificity;
    AttackerDosTAnalyseTest a,Atcpdos[],Audpdos[],Aicmpdos[],TotAtcpdos,TotAudpdos,TotAicmpdos;
    AttackerProbeAnalyseTest b,Aprobe[],TotAprobe;
    Integer o,attackval=0,normalval,temp=0,totpkt=0,pkt=0;
    
    
    void analyseTest(TrainPkt P[],int i) throws IOException{    
        
        pkt=i;totpkt+=i;probecount=0;
        tcppktcount=0;udppktcount=0;icmppktcount=0;tdoscount=0;udoscount=0;Acount=0;Ncount=0;idoscount=0;prevtime=0;posttime=0;
        Atcpdos=new AttackerDosTAnalyseTest[i];
        Audpdos=new AttackerDosTAnalyseTest[i];
        Aicmpdos=new AttackerDosTAnalyseTest[i];
        Aprobe=new AttackerProbeAnalyseTest[i];
    
        for(j=0;j<i;j++)
        {
            Atcpdos[j]=new AttackerDosTAnalyseTest();
            Audpdos[j]=new AttackerDosTAnalyseTest();
            Aicmpdos[j]=new AttackerDosTAnalyseTest();
            Aprobe[j]=new AttackerProbeAnalyseTest();
        }
        attackval=0;
        temp=0;
        processProbe(P,i);
        
        for(o=0;o<i;o++)
        {
            attackval=0;
            if(P[o].protocol.equals("06"))
            {
                if(tdoscount==0)
                {
                    Atcpdos[0].name=P[o].src;                    
                    tdoscount++;
                    tcppktcount++;         
                }
                else
                    processTcpDos(P,o);
            }
            else if(P[o].protocol.equals("17"))
            {
                if(udoscount==0)
                {
                    Audpdos[0].name=P[o].src;                    
                    udoscount++;
                    udppktcount++;
                }
                else
                    processUdpDos(P,o);
            }
            else if(P[o].protocol.equals("01"))
            {
                if(idoscount==0)
                {
                    Aicmpdos[0].name=P[o].src;                    
                    idoscount++;
                    icmppktcount++;
                }
                else
                    processIcmpDos(P,o);
            }
        }
        
        chkTcpDos();
        chkUdpDos();
        chkIcmpDos();
        chkProbe();
        
        tottcppktcount+=tcppktcount;
        totudppktcount+=udppktcount;
        toticmppktcount+=icmppktcount;        
    }
        
    void processTcpDos(TrainPkt P[],int o){
        attackval=0;
        tcppktcount++;
        for(j=0;j<tdoscount;j++){
            if(Atcpdos[j].name.equals(P[o].src))
                break;            
        }
        if(j==tdoscount){
            Atcpdos[tdoscount++].name=P[o].src;            
        }
        else if(j<tdoscount){
            temp=o-1;
            while(temp>=0)
            {
                if(P[o].src.equals(P[temp].src))
                    break;
                temp--;
            }
            if(temp>=0){
                prevtime= Integer.valueOf(P[o].time.substring(7))- Integer.valueOf(P[temp].time.substring(7));
                if(prevtime<0.001)
                    attackval++;                    
            }
            if(o<pkt-1)
            {
                temp=o+1;
                while(temp<pkt)
                {
                    if(P[o].src.equals(P[temp].src))
                        break;
                    temp++;
                }
                if(temp<pkt){
                    posttime=Integer.valueOf(P[temp].time.substring(7))- Integer.valueOf(P[o].time.substring(7));                    
                    if(posttime<0.001)
                        attackval++;
                }
            }
            if(attackval==2)
            {
                if(prevtime<0.0001)
                    attackval++;
                if(posttime<0.0001)
                    attackval++;
            }
            
            Atcpdos[j].attackval+=attackval;                        
        }                    
    }

    void processUdpDos(TrainPkt P[],int o){
        attackval=0;
        udppktcount++;
        for(j=0;j<udoscount;j++){
            if(Audpdos[j].name.equals(P[o].src))
                break;            
        }
        if(j==udoscount){
            Audpdos[udoscount++].name=P[o].src;            
        }
        else{
            temp=o-1;
            while(temp>=0)
            {
                if(P[o].src.equals(P[temp].src))
                    break;
                temp--;
            }
            if(temp>=0){
                prevtime= Integer.valueOf(P[o].time.substring(7))- Integer.valueOf(P[temp].time.substring(7));
                if(prevtime<0.001)
                    attackval++;                    
            }
            if(o<pkt-1)
            {
                temp=o+1;
                while(temp<pkt)
                {
                    if(P[o].src.equals(P[temp].src))
                        break;
                    temp++;
                }
                if(temp<pkt){
                    posttime=Integer.valueOf(P[temp].time.substring(7))- Integer.valueOf(P[o].time.substring(7));                    
                    if(posttime<0.001)
                        attackval++;
                }
            }
            if(attackval==2)
            {
                if(prevtime<0.0001)
                    attackval++;
                if(posttime<0.0001)
                    attackval++;
            }
            
            Audpdos[j].attackval+=attackval;                        
        }                    
    }

    
    void processIcmpDos(TrainPkt P[],int o){
        attackval=0;
        icmppktcount++;
        for(j=0;j<idoscount;j++){
            if(Aicmpdos[j].name.equals(P[o].src))
                break;            
        }
        if(j==idoscount){
            Aicmpdos[idoscount++].name=P[o].src;            
        }
        else{
            temp=o-1;
            while(temp>=0)
            {
                if(P[o].src.equals(P[temp].src))
                    break;
                temp--;
            }
            if(temp>=0){
                prevtime= Integer.valueOf(P[o].time.substring(7))- Integer.valueOf(P[temp].time.substring(7));
                if(prevtime<0.001)
                    attackval++;                    
            }
            if(o<pkt-1)
            {
                temp=o+1;
                while(temp<pkt)
                {
                    if(P[o].src.equals(P[temp].src))
                        break;
                    temp++;
                }
                if(temp<pkt){
                    posttime=Integer.valueOf(P[temp].time.substring(7))- Integer.valueOf(P[o].time.substring(7));                    
                    if(posttime<0.001)
                        attackval++;
                }
            }
            if(attackval==2)
            {
                if(prevtime<0.0001)
                    attackval++;
                if(posttime<0.0001)
                    attackval++;
            }
            
            Aicmpdos[j].attackval+=attackval;                        
        }                    
    }    
    
    void chkTcpDos() throws IOException{
        int flag,x;
                
        if(tottdoscount==0)
            {
                TotAtcpdos=new AttackerDosTAnalyseTest();
                TotAtcpdos.name=Atcpdos[0].name;
                tottdoscount++;
            }
            
        for(j=0;j<tdoscount;j++)
        {
            flag=0;
            for(a=TotAtcpdos;a!=null;a=a.next)
                System.out.println(a.attacker+"    "+a.name+"    "+a.attackval);                       
            a=TotAtcpdos;            
            for(k=0;k<tottdoscount&&a!=null;k++){
                
                if(a.name.equals(Atcpdos[j].name))
                {
                    a.attackval+=Atcpdos[j].attackval;
                    flag=1;
                }
                
                a=a.next;
                if(flag==1)
                    break;
            }
            
            if(flag==0)
            {
                a=TotAtcpdos;
                while(a.next!=null)
                    a=a.next;
                
                a.next=new AttackerDosTAnalyseTest();
                a=a.next;
                a.name=Atcpdos[j].name;
                a.attackval=Atcpdos[j].attackval;
                tottdoscount++;
            }
        } 
    }
    
    void chkUdpDos()throws IOException{
        int flag,x;
                
        if(totudoscount==0)
            {
                TotAudpdos=new AttackerDosTAnalyseTest();
                TotAudpdos.name=Audpdos[0].name;
                TotAudpdos.next=null;
                totudoscount++;
            }
            
        for(j=0;j<udoscount;j++)
        {
            flag=0;
            a=TotAudpdos;
            
            for(k=0;k<totudoscount&&a!=null;k++){
                if(a.name.equals(Audpdos[j].name))
                {
                    a.attackval+=Audpdos[j].attackval;
                    flag=1;
                }
                
                a=a.next;
                if(flag==1)
                    break;
            }
            
            if(flag==0)
            {
                a=TotAudpdos;
                while(a.next!=null)
                    a=a.next;
                
                a.next=new AttackerDosTAnalyseTest();
                a=a.next;
                a.name=Audpdos[j].name;
                a.attackval=Audpdos[j].attackval;
                totudoscount++;
            }
        } 
    }
    
    void chkIcmpDos()throws IOException{
        int flag,x;
                
        if(totidoscount==0)
            {
                TotAicmpdos=new AttackerDosTAnalyseTest();
                TotAicmpdos.name=Aicmpdos[0].name;
                TotAicmpdos.next=null;
                totidoscount++;
            }
        
        for(j=0;j<idoscount;j++)
        {
            flag=0;
            a=TotAicmpdos;
            for(k=0;k<totidoscount&&a!=null;k++){
                if(a.name.equals(Aicmpdos[j].name))
                {
                    a.attackval+=Aicmpdos[j].attackval;
                    flag=1;
                }
                
                a=a.next;
                if(flag==1)
                    break;
            }
            
            if(flag==0)
            {
                a=TotAicmpdos;
                while(a.next!=null)
                    a=a.next;
                
                a.next=new AttackerDosTAnalyseTest();
                a=a.next;
                a.name=Aicmpdos[j].name;
                a.attackval=Aicmpdos[j].attackval;
                totidoscount++;
            }
        } 
    }
    
    void dispTcpDos(){
        a=TotAtcpdos;
        while(a!=null){
            a.attacker=false;
            if(a.attackval>10)
            {
                alarm(2,a);
                Acount++;
                a.attacker=true;
                System.out.println("Source: "+a.name+"    Attackval:"+a.attackval);                
            }
            a=a.next;
        }
    
    }
    
    void dispUdpDos(){
        a=TotAudpdos;
        while(a!=null){
            a.attacker=false;
            if(a.attackval>10)
            {
                alarm(2,a);
                Acount++;
                a.attacker=true;
                System.out.println("Source: "+a.name+"    Attackval:"+a.attackval);                
            }
            a=a.next;
        }
    
    }
    
    void dispIcmpDos(){
        a=TotAicmpdos;
        while(a!=null){
            a.attacker=false;
            if(a.attackval>10)
            {
                alarm(2,a);
                Acount++;
                a.attacker=true;
                System.out.println("Source: "+a.name+"    Attackval:"+a.attackval);                
            }
            a=a.next;
        }
    
    }
    
    
    void chkProbe(){    
        int flag,x;
                
        if(totprobecount==0)
        {
            TotAprobe=new AttackerProbeAnalyseTest();
            TotAprobe.name=Aprobe[0].name;
            TotAprobe.port=Aprobe[0].port;
            
            TotAprobe.next=null;
            totprobecount++;
        }
            
        for(j=0;j<probecount;j++)
        {
            flag=0;
            b=TotAprobe;
            
            for(k=0;k<totprobecount&&b!=null;k++){
                if(b.name.equals(Aprobe[j].name)&&b.port.equals(Aprobe[j].port))
                {
                    b.attackval+=Aprobe[j].attackval;
                    flag=1;
                }
                
                b=b.next;
                if(flag==1)
                    break;
            }
            
            if(flag==0)
            {
                b=TotAprobe;
                while(b.next!=null)
                    b=b.next;
                
                b.next=new AttackerProbeAnalyseTest();
                b=b.next;
                b.name=Aprobe[j].name;
                b.port=Aprobe[j].port;
                b.attackval=Aprobe[j].attackval;
                totprobecount++;
            }
        }
    }
    
    void dispProbe(){
        int totav=0,av=0;
        b=TotAprobe;
        AttackerProbeAnalyseTest max=b,tot=b; 
            
        for(j=0;j<totprobecount&&b!=null;j++)
        {
            b.attacker=false;
            if(b.attackval>max.attackval)
                max=b;            
            av+=b.attackval;                
            b=b.next;                        
        }
        if(av>0)
            totav=av/2;
        if(max.attackval>totav)
        {
            Acount++;        
            alarm(max);
            max.attacker=true;
            System.out.println("Total Packets: "+totpkt+"   Total Attackval: "+tot.attackval);
        }    
        /*if(pkt>15)            
            if(probecount<5)                
            {
                switch(tprobecount)
                {
                    case 1:
                        alarm(1);
                        break;
                    case 2:
                        alarm(1);
                        break;
                    case 3:
                        alarm(1);                        
                        break;
                    case 4:
                        alarm(1);
                        break;
                    case 5:
                        alarm(1);
                        break;                        
                }                      
            }*/
        
    }
    
    void processProbe(TrainPkt P[],int m){
    //process for probe
        int flag;
        if(m>0){
        Aprobe[0].name=P[0].src;
        Aprobe[0].port=P[0].port;
        probecount=1;       
                       
        for(k=1;k<m;k++)
        {
            flag=0;
            for(j=0;j<probecount;j++)
            {
                if(Aprobe[j].name.equals(P[k].src)&&Aprobe[j].port.equals(P[k].port))
                {
                    Aprobe[j].attackval++;
                    flag=1;
                }
                
                if(flag==1)
                    break;
            }
            if(j==probecount){
                Aprobe[probecount].name=P[k].src;
                Aprobe[probecount++].port=P[k].port;
            }                                
        }
        }
    }
    
    void alarm(int ch,AttackerDosTAnalyseTest at){
        System.err.println("\bALARM!!");        
        if(ch==2)
            System.err.println("DOS attack!!!");
        System.err.println("Source: "+at.name);
        System.err.println("Attackvalue: "+at.attackval);      
    }
    void alarm(AttackerProbeAnalyseTest at){
        System.err.println("\bALARM!!");        
        System.err.println("Probe attack!!!");
        System.err.println("Source: "+at.name);
        System.err.println("Port: "+at.port);        
        System.err.println("Attackvalue: "+at.attackval);      
    }
    
    void analysis(){
    
    }    
    
    String analyseTestResult(TrainPkt P){
            P.status="NORMAL";
                        
            if(P.protocol.equals("06"))
            {             
                a=TotAtcpdos;
                while(a!=null)
                {
                    if(a.attacker==true&&P.src.equals(a.name))
                    {
                        dosattack++;
                        P.status="DOS";
                    }
                    a=a.next;
                    
                }
            }
            else if(P.protocol.equals("17"))
            {             
                a=TotAudpdos;
                while(a!=null)
                {
                    if(a.attacker==true&&P.src.equals(a.name))
                    {
                        dosattack++;
                        P.status="DOS";
                    }
                    a=a.next;
                    
                }                
            }
            else if(P.protocol.equals("01"))
            {             
                a=TotAicmpdos;
                while(a!=null)
                {
                    if(a.attacker==true&&P.src.equals(a.name))
                    {
                        dosattack++;
                        P.status="DOS";
                    }
                    a=a.next;
                    
                }                            
            }

            if(P.status.equals("NORMAL"))
            {
                b=TotAprobe;
            
                while(b!=null){
                    if(b.attacker==true&&P.src.equals(b.name)&&P.port.equals(b.port))
                    {
                        probeattack++;
                        P.status="PROBE";
                    }
                    b=b.next;
                }
            }            
            //}            
            return(P.status);            
        }                  
}

class AttackerDosTAnalyseTest{
    public String name=null;
    public Integer attackval=0;
    public AttackerDosTAnalyseTest next=null;
    public boolean attacker=false;
} 

class AttackerProbeAnalyseTest{
    public String name=null,port=null;
    public Integer attackval=0;
    public AttackerProbeAnalyseTest next=null;
    public boolean attacker=false;
}


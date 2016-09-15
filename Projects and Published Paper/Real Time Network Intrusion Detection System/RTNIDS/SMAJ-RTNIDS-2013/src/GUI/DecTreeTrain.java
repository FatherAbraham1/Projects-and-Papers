/*
 * SMAJ-RTNIDS 2012-2013
 */

package GUI;

import java.io.IOException;

public class DecTreeTrain {    
    Integer dosattack=0,probeattack=0,tcppktcount=0,udppktcount=0,icmppktcount=0,tottcppktcount=0,totudppktcount=0,toticmppktcount=0,tdoscount=0,probecount=0,udoscount=0,idoscount=0,tottdoscount=0,totprobecount=0,totudoscount=0,totidoscount=0,Acount=0,Ncount=0,prevtime=0,posttime=0,j,prev,post,k;
    AttackerDosTrain a,Atcpdos[],Audpdos[],Aicmpdos[],TotAtcpdos,TotAudpdos,TotAicmpdos;
    AttackerProbeTrain b,Aprobe[],TotAprobe;
    Integer o,attackval=0,normalval,temp=0,totpkt=0,pkt=0,dosattackval=0;
    
    
    void train(TrainPkt P[],int i) throws IOException{           
        pkt=i;totpkt+=i;probecount=0;
        tcppktcount=0;udppktcount=0;icmppktcount=0;tdoscount=0;udoscount=0;Acount=0;Ncount=0;idoscount=0;prevtime=0;posttime=0;
        Atcpdos=new AttackerDosTrain[i];
        Audpdos=new AttackerDosTrain[i];
        Aicmpdos=new AttackerDosTrain[i];
        Aprobe=new AttackerProbeTrain[i];
    
        for(j=0;j<i;j++)
        {
            Atcpdos[j]=new AttackerDosTrain();
            Audpdos[j]=new AttackerDosTrain();
            Aicmpdos[j]=new AttackerDosTrain();
            Aprobe[j]=new AttackerProbeTrain();
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
                dosattackval=dosattackval+computeDosAttackval(P,o,temp);
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
                    dosattackval=dosattackval+computeDosAttackval(P,o,temp);

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
            Atcpdos[j].dosattackval+=dosattackval;

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
                dosattackval=dosattackval+computeDosAttackval(P,o,temp);

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
                    dosattackval=dosattackval+computeDosAttackval(P,o,temp);

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
            Audpdos[j].dosattackval+=dosattackval;


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
                dosattackval=dosattackval+computeDosAttackval(P,o,temp);

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
                    dosattackval=dosattackval+computeDosAttackval(P,o,temp);

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
            Aicmpdos[j].dosattackval+=attackval;

        }                    
    }       
    
    private Integer computeDosAttackval(TrainPkt[] P, int o, Integer temp) {
        int val=0;
            if((P[o].seq_no).equals(P[temp].seq_no))
                        val++;
                if((P[o].port).equals(P[temp].port))
                        val++;
                if((P[o].id).equals(P[temp].id))
                        val++;
            return val;
    }
    
    void chkTcpDos() throws IOException{
        int flag,x;
                
        if(tottdoscount==0)
            {
                TotAtcpdos=new AttackerDosTrain();
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
                    a.dosattackval+=Atcpdos[j].dosattackval;
                
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
                
                a.next=new AttackerDosTrain();
                a=a.next;
                a.name=Atcpdos[j].name;
                a.attackval=Atcpdos[j].attackval;
                a.dosattackval+=Atcpdos[j].dosattackval;
                
                tottdoscount++;
            }
        } 
    }
    
    void chkUdpDos()throws IOException{
        int flag,x;
                
        if(totudoscount==0)
            {
                TotAudpdos=new AttackerDosTrain();
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
                    a.dosattackval+=Audpdos[j].dosattackval;
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
                
                a.next=new AttackerDosTrain();
                a=a.next;
                a.name=Audpdos[j].name;
                a.attackval=Audpdos[j].attackval;
                a.dosattackval+=Audpdos[j].dosattackval;
                totudoscount++;
            }
        } 
    }
    
    void chkIcmpDos()throws IOException{
        int flag,x;
                
        if(totidoscount==0)
            {
                TotAicmpdos=new AttackerDosTrain();
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
                    a.dosattackval+=Aicmpdos[j].dosattackval;
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
                
                a.next=new AttackerDosTrain();
                a=a.next;
                a.name=Aicmpdos[j].name;
                a.attackval=Aicmpdos[j].attackval;
                a.dosattackval+=Aicmpdos[j].dosattackval;
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
            else if(a.dosattackval>totpkt)
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
            else if(a.dosattackval>totpkt)
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
            }else if(a.dosattackval>totpkt)
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
            TotAprobe=new AttackerProbeTrain();
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
                
                b.next=new AttackerProbeTrain();
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
        AttackerProbeTrain max=b,tot=b; 
            
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
    }
    
    void processProbe(TrainPkt P[],int m){
    //process for probe
        if(m>0){
        int flag;
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
    
    void alarm(int ch,AttackerDosTrain at){
        System.err.println("\bALARM!!");        
        if(ch==2)
            System.err.println("DOS attack!!!");
        System.err.println("Source: "+at.name);
        System.err.println("Attackvalue: "+at.attackval);      
    }
    void alarm(AttackerProbeTrain at){
        System.err.println("\bALARM!!");        
        System.err.println("Probe attack!!!");
        System.err.println("Source: "+at.name);
        System.err.println("Port: "+at.port);        
        System.err.println("Attackvalue: "+at.attackval);      
    }       
    
    String trainResult(TrainPkt P){
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
class AttackerDosTrain{
    public String name=null;
    public Integer attackval=0;
    public AttackerDosTrain next=null;
    public boolean attacker=false;
    public Integer dosattackval=0;
} 

class AttackerProbeTrain{
    public String name=null,port=null;
    public Integer attackval=0;
    public AttackerProbeTrain next=null;
    public boolean attacker=false;
}


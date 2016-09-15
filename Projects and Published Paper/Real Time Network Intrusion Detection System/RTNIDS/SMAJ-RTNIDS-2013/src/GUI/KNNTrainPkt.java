/*
 * SMAJ-RTNIDS 2012-2013
 */
package GUI;

import java.util.StringTokenizer;

class KNNTrainPkt{
    public String status,time,src,protocol,type,id,seq_no,port,ans,myans;
    int size,wt;
    
    public void printpkt(){
        System.out.println(time+"\t"+src+"\t"+type+"\t"+id+"\t"+protocol+"\t"+port+"\t"+seq_no+"\t"+ans+"\t"+wt);
    }

    public boolean initpkt(String line){
            StringTokenizer st;
            st=new StringTokenizer(line);
            size=st.countTokens();
            if(size<21)
                return false;
            time=st.nextToken();
            src=st.nextToken();
            src=st.nextToken();
            type=st.nextToken();
            id=st.nextToken();
            id=st.nextToken();
            id=st.nextToken();
            id=st.nextToken();
            protocol=st.nextToken();
            protocol=st.nextToken();
            protocol=st.nextToken();
            protocol=st.nextToken();
            port=st.nextToken();
            port=st.nextToken();
            port=st.nextToken();
            port=st.nextToken();
            port=st.nextToken();
            seq_no=st.nextToken();
            seq_no=st.nextToken();
            size=size-20;
            while(size!=0)
            {                
                size--;
                ans=st.nextToken();            
             
            }
            ans=st.nextToken();
            //printpkt();
            return true;
    }
}

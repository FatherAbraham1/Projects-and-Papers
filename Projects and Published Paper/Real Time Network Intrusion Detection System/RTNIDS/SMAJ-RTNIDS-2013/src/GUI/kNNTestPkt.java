/*
 * SMAJ-RTNIDS 2012-2013
 */

package GUI;

import java.util.StringTokenizer;

public class kNNTestPkt{
    public String status,time,src,protocol,type,id,seq_no,port,ans;
    int size,wt;
    
    public void printpkt(){
        System.out.println(time+"\t"+src+"\t"+type+"\t"+id+"\t"+protocol+"\t"+port+"\t"+seq_no);
    }
    public boolean initpkt(String line){
        
        time="";
        src="";
        type="";
        id="";
        protocol="";
        port="";
        seq_no="";
        
        StringTokenizer st;
        st=new StringTokenizer(line);
        size=st.countTokens();
        if(size<20)
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
           
        //printpkt();
        return true;
    }
}

/*
 * SMAJ-RTNIDS 2012-2013
 */

package GUI;

import java.util.StringTokenizer;

public class TestPkt{
    public String status,time,src,protocol,type,id,seq_no,port;
    int size;
    
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
        try{
        StringTokenizer st;
        st=new StringTokenizer(line);
        size=st.countTokens();
        if(size<20)
            return false;
        if(st.hasMoreTokens())
        time=st.nextToken();
        if(st.hasMoreTokens())        
        src=st.nextToken();
        if(st.hasMoreTokens())        
        src=st.nextToken();
        if(st.hasMoreTokens())
        type=st.nextToken();
        if(st.hasMoreTokens())
        id=st.nextToken();
        if(st.hasMoreTokens())
        id=st.nextToken();
        if(st.hasMoreTokens())
        id=st.nextToken();
        if(st.hasMoreTokens())
        id=st.nextToken();
        if(st.hasMoreTokens())
        protocol=st.nextToken();
        if(st.hasMoreTokens())
        protocol=st.nextToken();
        if(st.hasMoreTokens())
        protocol=st.nextToken();
        if(st.hasMoreTokens())
        protocol=st.nextToken();
        if(st.hasMoreTokens())
        port=st.nextToken();
        if(st.hasMoreTokens())
        port=st.nextToken();
        if(st.hasMoreTokens())
        port=st.nextToken();
        if(st.hasMoreTokens())
        port=st.nextToken();
        if(st.hasMoreTokens())
        port=st.nextToken();
        if(st.hasMoreTokens())
        seq_no=st.nextToken();
        if(st.hasMoreTokens())
        seq_no=st.nextToken();
            printpkt();
        if(st.hasMoreTokens())
        
            return true;
        }catch(Exception e){
            System.out.println("Packet cant be processed!!");
        }        
        return false;
    }

    private boolean validate() {
        if(id!=null&&port!=null&&protocol!=null&&src!=null&&status!=null&&time!=null&&type!=null)
            return true;
        else 
            return false;            
    }
}

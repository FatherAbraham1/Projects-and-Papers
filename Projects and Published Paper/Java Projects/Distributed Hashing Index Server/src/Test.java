import com.aos.client.ClientUtil;


public class Test {
public static void main(String[] args) {
	ClientUtil client=new ClientUtil();
	client.fileServerProperties();
	System.out.println(System.currentTimeMillis());
}

static void testRegister(ClientUtil client){
	String fileName=".txt";
	long start=System.currentTimeMillis();
	
	for(int i=0;i<10000;i++)
		client.register(i+"KB.txt");
	long end=System.currentTimeMillis();
	System.out.println("Start time "+start+" End Time : "+end+" Total time "+(end-start));
}
static void testSearh(ClientUtil client){
	String fileName=".txt";
	long start=System.currentTimeMillis();
	
	for(int i=0;i<10000;i++)
		client.search(i+"KB.txt");
	long end=System.currentTimeMillis();
	System.out.println("Start time "+start+" End Time : "+end+" Total time "+(end-start));
}
static void testObtain(ClientUtil client){
	String fileName=".txt";
	long start=System.currentTimeMillis();
	
	for(int i=0;i<10000;i++)
		client.obtain(i+"KB.txt");
	long end=System.currentTimeMillis();
	System.out.println("Start time "+start+" End Time : "+end+" Total time "+(end-start));
}

}

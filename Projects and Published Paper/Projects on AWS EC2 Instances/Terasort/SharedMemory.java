import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SharedMemory extends Thread {
        static BufferedReader br;
        static int chunks = 0;
        static String path="/mount/raid/shmemtemp/";//"/mount/raid/"
        static String tempPath=path+"temp-";
        static String inputFile=path+"data.txt";
        static String outputFile=path+"sortedData.txt";

        static String s;
        String line;
        static int chunkSize = 530000;
        static BufferedReader[] readers;
        static BufferedWriter bw;
        static long start, end;
        static Map<String, Integer> map;
        static int threadsCount=4;
        public static void main(String[] args) throws Exception {
                int k;
                map = new TreeMap<String, Integer>();
		if (args.length > 0) {
    		try {
		        threadsCount = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e) {
			threadsCount=4;
		    }
		}
                try {

		
                     	br = new BufferedReader(new FileReader(new File(inputFile)));
                        SharedMemory threads[] = new SharedMemory[threadsCount];
                        for (int thread = 0; thread < threadsCount; thread++)
                                threads[thread] = new SharedMemory();

                        start = new Date().getTime();
                        for (int thread = 0; thread < threadsCount; thread++)
                                threads[thread].start();

                        for (int thread = 0; thread < threadsCount; thread++)
                                threads[thread].join();

                        br.close();
                        br = null;

                        readers = new BufferedReader[chunks + 1];
                        for (int chunk = 0; chunk <= chunks; chunk++) {
                                readers[chunk] = new BufferedReader(new FileReader(new File(tempPath + chunk
                                                + ".txt")));
                                map.put(readers[chunk].readLine(), chunk);
                        }
                        bw = new BufferedWriter(new FileWriter(new File(outputFile)));
                        while (!map.isEmpty()) {
                                s = map.keySet().iterator().next();
                                k = map.get(s);
                                bw.write(s + " \n");
                                map.remove(s);
                                s = readers[k].readLine();
                                if (s != null) {
                                        map.put(s, k);
                                }
                        }
                        bw.close();
                        for (int j = 0; j < readers.length; j++) {
                                readers[j].close();
                                new File(tempPath + j + ".txt").delete();
                        }

                        end = new Date().getTime();
                        System.out.println("No. of threads: " + threadsCount + "\tDuration: "
                                        + (end - start) / 1000 + " sec");
                        } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (InterruptedException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }

	public void run() {
                startSharedMemory();
        }

	synchronized public static void startSharedMemory() {
                Set<String> set = new TreeSet<String>();
                String line = null;
               try {
                     	line = br.readLine();
                        while (line != null) {
                                set.add(line);
                                if (set.size() == chunkSize) {
                                        FileWriter fw = new FileWriter(new File(tempPath + chunks + ".txt"));
                                        for (String x : set)
                                                fw.write(x+"\n");
                                        fw.close();
                                        chunks++;
                                        set = new TreeSet<String>();
                                }
                                line = br.readLine();
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        try {
                             	if (set.size() > 0) {
                                        FileWriter fw = new FileWriter(new File(tempPath + chunks + ".txt"));
                                        for (String x : set)
                                                fw.write(x+"\n");
                                        fw.close();
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }
}





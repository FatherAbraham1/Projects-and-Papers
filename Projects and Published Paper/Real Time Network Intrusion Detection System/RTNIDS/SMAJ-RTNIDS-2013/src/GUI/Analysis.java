/*
 * SMAJ-RTNIDS 2012-2013
 */
package GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

/**
 *
 * @author Mrinal Bhalerao
 */
public class Analysis {
    int a,b,c,d;
        
     double TP, TN, FP, FN;
     double acc;
     double TPR,FAR,FNR,accuracy,precision;
    String lastfile;
        
    
    void Analyse(kNNTrain k) throws FileNotFoundException, IOException{
        //kNNTrain Train= new kNNTrain();
        
        
        /*
         * a=correct prediction of NEGATIVE     b=incorrect prediction of POSITIVE
         * c=incorrect prediction of NEGATIVE   d=correct prediction of POSITIVE
         */
         
        /*
        a= k.normalcorrectcount;
        b= k.dosincorrectcount+ k.probeincorrectcount;
        c= k.normalincorrectcount;
        d=k.probecorrectcount+ k.doscorrectcount;
        
        System.out.println("\nCONFUSION MATRIX");
        System.out.print("a:"+a);
        System.out.print("\tb:"+b);
        System.out.print("\nc:"+c);
        System.out.print("\td:"+d);
        */
       
        /*
         * Accuracy = (a+d)/(a+b+c+d)
         */
       // acc=(a+d)/(a+b+c+d);
        //System.out.print("\nACCURACY: "+ acc);
    
        /*
         * The recall or true positive rate (TP) is the proportion of positive cases that were correctly identified
         */
        
        TP= k.doscorrectcount+ k.probecorrectcount;
        
        /*
         *The false positive rate (FP) is the proportion of negatives cases that were incorrectly classified as positive 
         */
        
        FP = k.normalincorrectcount;
        
        /*
         * The true negative rate (TN) is defined as the proportion of negatives cases that were classified correctly
         */
        TN= k.normalcorrectcount;
        
        /*
         *The false negative rate (FN) is the proportion of positives cases that were incorrectly classified as negative
         */
        FN= k.dosincorrectcount+ k.probeincorrectcount;
        
        System.out.print("\nTRUE POSITIVE : "+TP);
        System.out.print("\nTRUE NEGATIVE : "+TN);
        System.out.print("\nFALSE POSITIVE : "+FP);
        System.out.print("\nFALSE POSITIVE : "+FN);
               
        /*
         * precision (P) is the proportion of the predicted positive cases that were correct
         */
        /*int Precision = d/(b+d);
        System.out.println("PRECISION: "+Precision);*/
        
        TPR = TP /(TP+FN);
        FAR = FP/(TN+FP);
        FNR = FN/ (TP+FN);
        accuracy=(TN + TP)/(TN + TP + FN + FP);
        
        precision  = TP/(TP + FP);
        
        System.out.println("\n\nTRUE POSITIVE RATE: "+TPR);
        System.out.println("FALSE ALARM RATE: "+FAR);
        System.out.println("FALSE NEGATIVE RATE: "+FNR);
        System.out.println("ACCURACY: "+ accuracy);
        System.out.println("PRECISION: "+ precision);
        
        Long time=System.nanoTime();
        String line;        
        String name=Calendar.DATE+time.toString();
        File res=new File("C:/SMAJ-RTNIDS/KNNLog/RESULT/"+name+".txt");
        lastfile="C:/SMAJ-RTNIDS/KNNLog/Result/"+name+".txt";
        FileOutputStream fres =new FileOutputStream(res);
        BufferedWriter bres=new BufferedWriter(new OutputStreamWriter(fres));
        bres.append("True Positive Rate: "+TPR);
        bres.close();
    }
} 
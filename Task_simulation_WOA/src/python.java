
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author punitg
 */
public class python {
    public static void main(String a[]){
try{


Process p = Runtime.getRuntime().exec("python untitled0.py");
BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

System.out.println("value is : "+in.readLine()+in.readLine());
}catch(Exception e){}
}
}

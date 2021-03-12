/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cloudbus.cloudsim.examples;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.HostDynamicWorkload;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;

/**
 *
 * @author IT
 */
public class Output {
 private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
						indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
			}
		}

	}


 
    public static void printCloudletList(List<Cloudlet> list, List<Datacenter> d ,List<Vm> v,String path) throws IOException {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
    
        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++)
        {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);
          
            Vm vm=v.get(cloudlet.getVmId());
           
        }
        printCloudletListcsv(list, d, v,path);
    }

      


public static void printCloudletListcsv(List<Cloudlet> list, List<Datacenter> d ,List<Vm> v,String path) throws IOException {

      FileWriter fw = new FileWriter(path);
        PrintWriter pw = new PrintWriter(fw);

        int size = list.size();
        Cloudlet cloudlet;

       pw.print("CloudletID,STATUS,DatacenterID,VMID,Time,Start Time,Finish Time");
        
        DecimalFormat dft = new DecimalFormat("###.##");

        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            pw.print("\n"+cloudlet.getCloudletId() + ",");

Vm vm=v.get(cloudlet.getVmId());
if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
            Datacenter da2=null;
for(int j=0; j<=d.size();j++) 
{
    Datacenter da=d.get(j);
DatacenterCharacteristics da1=da.characteristics ;
if(da1.id==cloudlet.getResourceId())
{da2=da;
 break;
}
}
  pw.append("SUCCESS"+",");
                pw.append(cloudlet.getResourceId() + "," + cloudlet.getVmId()
                        + "," + dft.format(cloudlet.getActualCPUTime())
                        + "," + dft.format(cloudlet.getExecStartTime()) + ","+ dft.format(cloudlet.getFinishTime()));
            }
        }


          pw.flush();

        //Close the Print Writer
        pw.close();

        //Close the File Writer
        fw.close();
    }


public static void printsimulation(List<Cloudlet> list, List<Datacenter> d ,List<Vm> v,String algo,long  sim) throws IOException {

      FileWriter fw = new FileWriter(algo+"_vm_"+v.size()+"task_"+list.size()+".csv");
      PrintWriter pw = new PrintWriter(fw);
      
      FileWriter fw2 = new FileWriter(algo+"_vm_"+v.size()+"task_"+list.size()+".txt");
      PrintWriter pw2 = new PrintWriter(fw2);
      
      FileWriter fw1 = new FileWriter("simulation.csv",true);
      BufferedWriter pw1= new BufferedWriter(fw1);
                
      
      pw2.write(Log.s);
        int size = list.size();
        Cloudlet cloudlet;
        double totaltime=0;
        int size1=v.size();
        float finish=0;
        float astart=0,afinish=0,autilization=0;
        float utilization[]=new float[size1];
        Arrays.fill(utilization, 0);
        
        
       pw.print("CloudletID,STATUS,DatacenterID,VMID,Time,Start Time,Finish Time");
        
        DecimalFormat dft = new DecimalFormat("###.##");

        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            pw.print("\n"+cloudlet.getCloudletId() + ",");

            Vm vm=v.get(cloudlet.getVmId());
            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                        Datacenter da2=null;
            for(int j=0; j<=d.size();j++) 
            {
                Datacenter da=d.get(j);
            DatacenterCharacteristics da1=da.characteristics ;
            if(da1.id==cloudlet.getResourceId())
            {da2=da;
             break;
            }
            }
            pw.append("SUCCESS"+",");
            pw.append(cloudlet.getResourceId() + "," + cloudlet.getVmId()
                        + "," + dft.format(cloudlet.getActualCPUTime())
                        + "," + dft.format(cloudlet.getExecStartTime()) + ","+ dft.format(cloudlet.getFinishTime()));
            totaltime=totaltime+cloudlet.getActualCPUTime();
            astart= (float) (astart+cloudlet.getExecStartTime());
            afinish=(float) (afinish+cloudlet.getFinishTime());

           if(utilization[cloudlet.vmId]<cloudlet.getFinishTime())
           utilization[cloudlet.vmId]=(float) cloudlet.getFinishTime();
           finish=  (float) cloudlet.getFinishTime();
            }
        }
        pw1.newLine();
        DateTimeFormatter dt= DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime n= LocalDateTime.now();
        pw1.append(dt.format(n)+","+d.size()+","+v.size()+","+list.size()+","+algo+","+(astart/list.size())+","+(afinish/list.size())+","+finish+","+totaltime+","+sim);
        
//        Log.printLine("total time:--"+totaltime);
//        Log.printLine("Execution time time:--"+finish);
//        Log.printLine("Average Start:--"+astart/list.size());
//        Log.printLine("Average Finish:--"+afinish/list.size());
        String s="";
        for(int i=0;i<utilization.length;i++)
        {
            //Log.printLine("Utilization VMid:"+i+"--"+100*(utilization[i]/finish));
                autilization=autilization+100*(utilization[i]/finish);
                 s=s+","+100*(utilization[i]/finish);
        }
        //Log.printLine("Average Utilization:--"+autilization/v.size());
        pw1.append(","+autilization/v.size());
        pw1.append(s);
        
        
        pw.flush();
        pw1.flush();

        //Close the Print Writer
        pw.close();
        pw1.close();
        //Close the File Writer
        fw.close();
        fw1.close();
    }



public static void printloadtList(List<Cloudlet> list, List<Datacenter> d ,List<Vm> v) throws IOException 
{
       
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
    for(int i=0;i<d.size();i++)
 {
     List<Host> h=d.get(i).getHostList();
  
        Log.print(h.size());
    
    for(int j=0;j<h.size();j++)
    {
    Log.print(h.get(j)+"\n");
    
    }
     
  }
       
     
    }


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.change;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
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
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("CloudletID" + indent + "STATUS" + indent
        + "DatacenterID" + indent + "VMID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time"+indent+"D0"+indent+"D1");
        indent = "    ";
        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++)
        {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);
            cost co=new cost();
            Vm vm=v.get(cloudlet.getVmId());
            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
            Log.print("SUCCESS");
            Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId()
            + indent + indent + indent + dft.format(cloudlet.getActualCPUTime())
            + indent + indent + dft.format(cloudlet.getExecStartTime()) + indent + indent + indent + dft.format(cloudlet.getFinishTime())+"-"+vm.Davailableram +indent+vm.Dusedram+indent+vm.Davailablemips +indent+vm.DusedMips+indent+ co.costvm(d, vm));
            }
        }
        printCloudletListcsv(list, d, v,path);
    }

      public static void printCloudletList1(List<Cloudlet> list, List<Datacenter> d ,List<Vm> v,String path) throws IOException {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("CloudletID" + indent + "STATUS" + indent
                + "DatacenterID" + indent + "VMID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time"+indent+"Cost");
indent = "    ";
        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);
cost co=new cost();
Vm vm=v.get(cloudlet.getVmId());
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


if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                Log.print("SUCCESS");

                Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId()
                        + indent + indent + indent + dft.format(cloudlet.getActualCPUTime())
                        + indent + indent + dft.format(cloudlet.getExecStartTime()) + indent + indent + indent + dft.format(cloudlet.getFinishTime()) +indent+ co.cost(da2, vm));
            }
        }
        printCloudletListcsv(list, d, v,path);
    }


public static void printCloudletListcsv(List<Cloudlet> list, List<Datacenter> d ,List<Vm> v,String path) throws IOException {

      FileWriter fw = new FileWriter(path);
        PrintWriter pw = new PrintWriter(fw);

        int size = list.size();
        Cloudlet cloudlet;

       pw.print("CloudletID,STATUS,DatacenterID,VMID,Time,Start Time,Finish Time,allocated cost");
         for(int i=0;i<d.size();i++)
       {
       pw.append(","+d.get(i).getName());
       }
        DecimalFormat dft = new DecimalFormat("###.##");

        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            pw.print("\n"+cloudlet.getCloudletId() + ",");
cost co=new cost();
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
                        + "," + dft.format(cloudlet.getExecStartTime()) + ","+ dft.format(cloudlet.getFinishTime()) +","+co.cost(da2, vm)+ co.costvmcsv(d, vm));
            }
        }


          pw.flush();

        //Close the Print Writer
        pw.close();

        //Close the File Writer
        fw.close();
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

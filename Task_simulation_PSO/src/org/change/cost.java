package org.change;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.List;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;

/**
 *
 * @author IT
 */
public class cost {



    public String costvm(List<Datacenter> d,Vm v)
    {String costs="";
String indent = "    ";
 for(int i=0;i<d.size();i++)
 {
     double cost = cost(d.get(i),v);
     costs=costs+indent+cost;
  }

  return costs;
    }
    

    public String costvmcsv(List<Datacenter> d,Vm v)
    {String costs="";
String indent = ",";
 for(int i=0;i<d.size();i++)
 {
     DatacenterCharacteristics dch=d.get(i).characteristics;
 
      double cost = dch.costPerStorage*v.size+dch.costPerRam*v.ram+dch.costPerBw*v.bw+dch.costPerMips*(v.mips*v.numberOfPes);
  
     costs=costs+indent+cost;
  }

return costs;
    }
        
    
     public double cost(Datacenter d,Vm v)
    {
String indent = ",";

     DatacenterCharacteristics dch=d.characteristics;

     double cost = dch.costPerStorage*v.size+dch.costPerRam*v.ram+dch.costPerBw*v.bw+dch.costPerMips*(v.mips*v.numberOfPes);
    return cost;
  }


    
}

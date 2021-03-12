/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//import org.change.Output;

import org.cloudbus.cloudsim.Cloudlet;
//import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
//import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.change.cost;
import org.change.outprints;
import org.change.roundrobim.HostList;
import org.change.roundrobim.RoundRobinDatacenterBroker;
import org.change.roundrobim.RoundRobinVmAllocationPolicy;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
//import org.cloudbus.cloudsim.NetworkTopology;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 * An example showing how to create simulation entities
 * (a DatacenterBroker in this example) in run-time using
 * a global manager entity (GlobalBroker).
 */
public class CloudSim_Roundrobin {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmList. */
	private static List<Vm> vmList;

	private static List<Vm> createVM(int userId, int vms, int idShift) {
		//Creates a container to store VMs. This list is passed to the broker later
		LinkedList<Vm> list = new LinkedList<Vm>();

		//VM Parameters
		long size = 10000; //image size (MB)
		int ram = 512; //vm memory (MB)
		int mips = 250;
		long bw = 1000;
		int pesNumber = 1; //number of cpus
		String vmm = "Xen"; //VMM name

		//create VMs
		Vm[] vm = new Vm[vms];

		for(int i=0;i<vms;i++)
                {int p=i%4;
                    if(p==1)
                {
		vm[i] = new Vm(idShift + i, userId, 250, pesNumber, ram, bw, size,5 ,vmm, new CloudletSchedulerSpaceShared());
		list.add(vm[i]);
                }
                else if(p==2)
                {vm[i] = new Vm(idShift + i, userId, 512, pesNumber, ram, bw, size,10, vmm, new CloudletSchedulerSpaceShared());
			list.add(vm[i]);
                }else if(p==3)
                {vm[i] = new Vm(idShift + i, userId, 1025, pesNumber, ram, bw, size,5, vmm, new CloudletSchedulerSpaceShared());
			list.add(vm[i]);
                }
                else 
                {vm[i] = new Vm(idShift + i, userId, 512, pesNumber, ram, bw, size,10 ,vmm, new CloudletSchedulerSpaceShared());
			list.add(vm[i]);
                }
                
                }

		return list;
	}


	private static List<Cloudlet> createCloudlet(int userId, int cloudlets, int idShift){
		// Creates a container to store Cloudlets
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

		//cloudlet parameters
		long length = 40000;
		long fileSize = 300;
		long outputSize = 300;
		int pesNumber = 1;
		UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet[] cloudlet = new Cloudlet[cloudlets];

		for(int i=0;i<cloudlets;i++)
                {int p=i%4;
                    
                    if(p==1)
                {
			cloudlet[i] = new Cloudlet(idShift + i, 4000, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			
                        // setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
                }
                else if(p==2)
                {cloudlet[i] = new Cloudlet(idShift + i, 2000, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			
                        // setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
                }else if(p==3)
                {cloudlet[i] = new Cloudlet(idShift + i, 300, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			
                        // setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
                }else
                {
                cloudlet[i] = new Cloudlet(idShift + i, 200, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			
                        // setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
                }
                
                }

		return list;
	}


	////////////////////////// STATIC METHODS ///////////////////////

	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {
		Log.printLine("Starting CloudSim PSO.../n");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);
                        long startTime = System.currentTimeMillis();
			//GlobalBroker globalBroker = new GlobalBroker("GlobalBroker");

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
			Datacenter datacenter1 = createDatacenter("Datacenter_1");
                        Datacenter datacenter2 = createDatacenter("Datacenter_2");
                        Datacenter datacenter3 = createDatacenter("Datacenter_3");
                        Datacenter datacenter4 = createDatacenter("Datacenter_4");
                        Datacenter datacenter5 = createDatacenter("Datacenter_5");
                        Datacenter datacenter6 = createDatacenter("Datacenter_6");
                        Datacenter datacenter7 = createDatacenter("Datacenter_7");
                        Datacenter datacenter8 = createDatacenter("Datacenter_8");
                        Datacenter datacenter9 = createDatacenter("Datacenter_9");
                        List<Datacenter> d = new ArrayList<Datacenter>();
                        d.add(datacenter0);
                        d.add(datacenter1);
                        d.add(datacenter2);
                        d.add(datacenter3);
                        d.add(datacenter4);
                        d.add(datacenter5);
                        d.add(datacenter6);
                        d.add(datacenter7);
                        d.add(datacenter8);
                        d.add(datacenter9);
			//Third step: Create Broker
			DatacenterBroker broker =  new DatacenterBroker("broker");
			int brokerId = broker.getId();
                        int i=10;
			//Fourth step: Create VMs and Cloudlets and send them to broker
			vmList = createVM(brokerId, 40, 0); //creating 30 vms
			cloudletList = createCloudlet(brokerId, i, 0); // creating 30 cloudlets

			broker.submitVmList(vmList);
			broker.submitCloudletList(cloudletList);

                        
                        //maps CloudSim entities to BRITE entities
		        //NetworkTopology.addLink(datacenter0.getId(),broker.getId(),10.0,10);
                        //NetworkTopology.addLink(datacenter1.getId(),broker.getId(),10.0,50);
			// Fifth step: Starts the simulation
			CloudSim.startSimulation();
                        long stopTime = System.currentTimeMillis();
                        long elapsedTime = stopTime - startTime;
                        Log.printLine(elapsedTime);
			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();
			//newList.addAll(globalBroker.getBroker().getCloudletReceivedList());

			CloudSim.stopSimulation();
                        

			//printCloudletList(newList);
                         // printCloudletList(newList , d,vmList);
                         //  Output o=new Output();

                  // o.printCloudletList(newList , d,vmList,"E:/WriteTest1.csv");
                      outprints o1=new outprints();
                          printCloudletList(newList);
                      
                        o1.printCloudlet_network(newList , d,vmList,"bat_"+i+".csv");
                        Log.printLine("\n PSO Simulation Ends!");
		        Log l=new Log();
			//BufferedWriter out = new BufferedWriter(new FileWriter("PSO_"+i+".txt"));
			//out.write(l.s);		
			//out.close();
			//Print the debt of each user to each datacenter
			datacenter0.printDebts();
			datacenter1.printDebts();
               	
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	private static Datacenter createDatacenter(String name) throws Exception{

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store one or more
		//    Machines
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores. Therefore, should
		//    create a list to store these PEs before creating
		//    a Machine.
		List<Pe> peList1 = new ArrayList<Pe>();

		int mips = 1000*2;

		// 3. Create PEs and add these into the list.
		//for a quad-core machine, a list of 4 PEs is required:
		peList1.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
		peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

		//Another list, for a dual-core machine
		List<Pe> peList2 = new ArrayList<Pe>();

		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
		peList2.add(new Pe(1, new PeProvisionerSimple(mips)));

		//4. Create Hosts with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 16384*2; //host memory (MB)
                
		long storage = 1000000*2; //host storage
		int bw = 10000*2;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList1,
    				new VmSchedulerSpaceShared(peList1)
    			)
    		); // This is our first machine

		hostId++;

		/*hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerSpaceShared(peList2)
    			)
    		); // Second machine
*/
		// 5. Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.1;	// the cost of using storage in this resource
		double costPerBw = 0.1;			// the cost of using bw in this resource

                  if(name.equals("Datacenter_1"))
                    { cost = 3.0;  
                    }
                  else if(name.equals("Datacenter_2"))
                    {
                      cost = 2.0;  
                    }
                  else if(name.equals("Datacenter_3"))
                    {
                      cost = 3.5;  
                    }
                  else if(name.equals("Datacenter_4"))
                    {
                      cost = 2.5;  
                    }
                  else if(name.equals("Datacenter_5"))
                    {
                      cost = 2.5;  
                    }
                  else if(name.equals("Datacenter_6"))
                    {
                      cost = 3.0;  
                    }
                  else if(name.equals("Datacenter_7"))
                    {
                      cost = 2.5;  
                    }
                  else if(name.equals("Datacenter_8"))
                    {
                      cost = 3.5;  
                    }
                  else if(name.equals("Datacenter_9"))
                    {
                      cost = 3.0;  
                    }

                LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


		     RoundRobinVmAllocationPolicy vm_policy = new RoundRobinVmAllocationPolicy(hostList);
           return new Datacenter(name, characteristics, vm_policy, storageList, 0);


	//	return datacenter;
	}

	//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	//to the specific rules of the simulated scenario
	private static DatacenterBroker createBroker(String name){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	/**
	 * Prints the Cloudlet objects
	 * @param list  list of Cloudlets
	 */
	private static void printCloudletList(List<Cloudlet> list ) {
		int size = list.size();
		Cloudlet cloudlet;
                double totaltime=0;
		String indent = "    ";
		Log.printLine();
		Log.printLine("===================================== OUTPUT ===========================================");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.vmId +
						indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
						indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
			totaltime=totaltime+cloudlet.getActualCPUTime();
                        }
		}
                Log.print(" \n total time:----------------------------------------"+totaltime );

	}
   private static void printCloudletList(List<Cloudlet> list, List<Datacenter> d ,List<Vm> v) {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        Log.printLine();
        Log.printLine("================================= OUTPUT =======================================");
        Log.printLine("CloudletID" + indent + "STATUS" + indent
                + "DatacenterID" + indent + "VMID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time"+indent+"Datacenter0-cost");
indent = "    ";
        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);
cost co=new cost();
Vm vm=v.get(cloudlet.getVmId());
if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                Log.print("SUCCESS");

                Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId()
                        + indent + indent + indent + dft.format(cloudlet.getActualCPUTime())
                        + indent + indent + dft.format(cloudlet.getExecStartTime()) + indent + indent + indent + dft.format(cloudlet.getFinishTime()) +indent+ co.costvm(d, vm));
            }
        }
    }

	


 public static class VmAllocationPolicyMinimum extends org.cloudbus.cloudsim.VmAllocationPolicy {

        private Map<String, Host> vm_table = new HashMap<String, Host>();
        private final HostList hosts;
        private Datacenter datacenter;

        public VmAllocationPolicyMinimum(List<? extends Host> list) {
            super(list);
            hosts = new HostList(list);
        }

        public void setDatacenter(Datacenter datacenter) {
            this.datacenter = datacenter;
        }

        public Datacenter getDatacenter() {
            return datacenter;
        }

        @Override
        public boolean allocateHostForVm(Vm vm) {

            if (this.vm_table.containsKey(vm.getUid())) {
                return true;
            }

            boolean vm_allocated = false;
            int tries = 0;
            do {
                Host host = this.hosts.getWithMinimumNumberOfPesEquals(vm.getNumberOfPes());
                vm_allocated = this.allocateHostForVm(vm, host);
            } while (!vm_allocated && tries++ < hosts.size());

            return vm_allocated;
        }

        @Override
        public boolean allocateHostForVm(Vm vm, Host host) {
            if (host != null && host.vmCreate(vm)) {
                vm_table.put(vm.getUid(), host);
                Log.formatLine("%.4f: VM #" + vm.getId() + " has been allocated to the host#" + host.getId()
                        + " datacenter #" + host.getDatacenter().getId() + "(" + host.getDatacenter().getName() + ") #",
                        CloudSim.clock());
                return true;
            }
            return false;
        }

        @Override
        public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
            return null;
        }

        @Override
        public void deallocateHostForVm(Vm vm) {
            Host host = this.vm_table.remove(vm.getUid());
            if (host != null) {
                host.vmDestroy(vm);
            }
        }

        @Override
        public Host getHost(Vm vm) {
            return this.vm_table.get(vm.getUid());
        }

        @Override
        public Host getHost(int vmId, int userId) {
            return this.vm_table.get(Vm.getUid(userId, vmId));
        }
    }

    private static DatacenterBroker createFederatedBroker(String name) throws Exception {
        return new RoundRobinDatacenterBroker(name);
    }


}

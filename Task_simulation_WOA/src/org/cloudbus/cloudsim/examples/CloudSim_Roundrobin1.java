package org.cloudbus.cloudsim.examples;

/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */




import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.change.roundrobim.RoundRobinDatacenterBroker;


import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
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
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;

import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.NetworkTopology;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;

import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.util.WorkloadFileReader;
import org.cloudbus.cloudsim.util.WorkloadModel;

/**
 * An example showing how to create simulation entities
 * (a DatacenterBroker in this example) in run-time using
 * a globar manager entity (GlobalBroker).
 */
public class CloudSim_Roundrobin1 {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;
        
    private static List<Cloudlet> cloudletList1= new ArrayList<Cloudlet>();

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
                {if(i<1)
                {
		vm[i] = new Vm(idShift + i, userId, 250, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
		list.add(vm[i]);
                }
                else if(i>0 && i<2)
                {vm[i] = new Vm(idShift + i, userId, 500, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
			list.add(vm[i]);
                }else 
                {vm[i] = new Vm(idShift + i, userId, 700, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
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
                {int j=i%4;
                    if(j==0)
                {
			cloudlet[i] = new Cloudlet(idShift + i, 4000, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			
                        // setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
                }
                else if(j==1)
                {cloudlet[i] = new Cloudlet(idShift + i, 200, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			
                        // setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
                }else if(j==3)
                {cloudlet[i] = new Cloudlet(idShift + i, 3000, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			
                        // setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
                }else
                {
                cloudlet[i] = new Cloudlet(idShift + i, 400, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			
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
		Log.printLine("Starting CloudSimExample...");
                    long startTime = System.currentTimeMillis();
		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			//GlobalBroker globalBroker = new GlobalBroker("GlobalBroker");

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
			Datacenter datacenter1 = createDatacenter("Datacenter_1");
                        Datacenter datacenter2 = createDatacenter("Datacenter_2");
                        Datacenter datacenter3 = createDatacenter("Datacenter_3");
                        Datacenter datacenter4 = createDatacenter("Datacenter_4");
                        Datacenter datacenter5 = createDatacenter("Datacenter_5");
                        List<Datacenter> d = new ArrayList<Datacenter>();
                        d.add(datacenter0);
                        d.add(datacenter1);
			//Third step: Create Broker
			//DatacenterBroker broker = createBroker("s");
                        DatacenterBroker broker = new DatacenterBroker("Broker");
			int brokerId = broker.getId();
                        vmList = createVM(brokerId, 10, 0); //creating 30 vms
			 // Fifth step: Read Cloudlets from workload file in the swf format
                       // Fifth step: Read Cloudlets from workload file in the swf format
                        WorkloadFileReader workloadFileReader = new WorkloadFileReader("src\\workload\\swf\\SDSC-BLUE-2000-4.swf", 1);
                        cloudletList = workloadFileReader.generateWorkload().subList(0, 100);
	    
                        for (Cloudlet cloudlet : cloudletList) {
		
                        //System.out.println(cloudlet.cloudletLength+"-"+cloudlet.getCloudletId());
                	
			long fileSize = 300;
			long outputSize = 300;
			UtilizationModel utilizationModel = new UtilizationModelFull();
                        int pesNumber = 1;
			Cloudlet cloudlet1 = new Cloudlet(cloudlet.getCloudletId(), cloudlet.cloudletLength, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
                      
                        cloudlet1.setUserId(brokerId);
                        cloudletList1.add(cloudlet1);
            }

	    // submit cloudlet list to the broker
                         broker.submitCloudletList(cloudletList1);
                        broker.submitVmList(vmList);
                       // broker.submitCloudletList(cloudletList);
                        //maps CloudSim entities to BRITE entities
		        //NetworkTopology.addLink(datacenter0.getId(),broker.getId(),10.0,10);
                        //NetworkTopology.addLink(datacenter1.getId(),broker.getId(),10.0,50);
			// Fifth step: Starts the simulation
			CloudSim.startSimulation();

			//Fourth step: Create VMs and Cloudlets and send them to broker
			//vmList = createVM(brokerId, 6, 0); //creating 30 vms
			//cloudletList = createCloudlet(brokerId, 500, 0); // creating 30 cloudlets

			
			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();
			//newList.addAll(globalBroker.getBroker().getCloudletReceivedList());

			CloudSim.stopSimulation();
                        long endTime   = System.currentTimeMillis();
                        long totalTime = endTime - startTime;
                            printCloudletList(newList,vmList);
			//printCloudletList(newList);
                         // printCloudletList(newList , d,vmList);
                           Output o=new Output();
                           o.printsimulation(newList, d, vmList, "bat",totalTime);
                          o.printCloudletList(newList , d,vmList,"WriteTest1.csv");
                         // o.printloadtList(newList, d, vmList);
                          
                        //o1.printCloudlet_cost(newList , d,vmList,"E:/round_cost.csv");
                         //o1.printCloudletList(newList , d,vmList,"E:/round_list.csv");
                         //o1.printCloudlet_load(newList , d,vmList,"E:/round_load.csv");
                         //o1.printCloudlet_network(newList , d,vmList,"E:/round_network.csv");
			//Print the debt of each user to each datacenter
			datacenter0.printDebts();
			datacenter1.printDebts();
                        System.out.println("Simulation time:"+totalTime);
               	        Log.printLine("CloudSimExample8 finished!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}
                
	public static void simulation(int dataceter, int vm, int task,String algo) {
		Log.printLine("Starting CloudSimExample...");
                    long startTime = System.currentTimeMillis();
		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			//GlobalBroker globalBroker = new GlobalBroker("GlobalBroker");

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			List<Datacenter> d = new ArrayList<Datacenter>();
                        for(int i=0;i<dataceter;i++)
                        {
                        Datacenter datacenter0 = createDatacenter("Datacenter_"+i);
			 d.add(datacenter0);
                        }
                        
			//Third step: Create Broker
			//DatacenterBroker broker = createBroker("s");
                        DatacenterBroker broker = new DatacenterBroker("Broker");
			int brokerId = broker.getId();
                        vmList = createVM(brokerId, vm, 0); //creating 30 vms
			 // Fifth step: Read Cloudlets from workload file in the swf format
                       // Fifth step: Read Cloudlets from workload file in the swf format
                        WorkloadFileReader workloadFileReader = new WorkloadFileReader("src\\workload\\swf\\SDSC-BLUE-2000-4.swf", 1);
                        cloudletList = workloadFileReader.generateWorkload().subList(0, task);
	    
                        for (Cloudlet cloudlet : cloudletList) {
		
                        //System.out.println(cloudlet.cloudletLength+"-"+cloudlet.getCloudletId());
                	
			long fileSize = 300;
			long outputSize = 300;
			UtilizationModel utilizationModel = new UtilizationModelFull();
                        int pesNumber = 1;
			Cloudlet cloudlet1 = new Cloudlet(cloudlet.getCloudletId(), cloudlet.cloudletLength, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
                      
                        cloudlet1.setUserId(brokerId);
                        cloudletList1.add(cloudlet1);
            }

	    // submit cloudlet list to the broker
                         broker.submitCloudletList(cloudletList1);
                        broker.submitVmList(vmList);
                       // broker.submitCloudletList(cloudletList);
                        //maps CloudSim entities to BRITE entities
		        //NetworkTopology.addLink(datacenter0.getId(),broker.getId(),10.0,10);
                        //NetworkTopology.addLink(datacenter1.getId(),broker.getId(),10.0,50);
			// Fifth step: Starts the simulation
			CloudSim.startSimulation();
	
			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();
			//newList.addAll(globalBroker.getBroker().getCloudletReceivedList());

			CloudSim.stopSimulation();
                        long endTime   = System.currentTimeMillis();
                        long totalTime = endTime - startTime;
                        Output o=new Output();
                        o.printsimulation(newList, d, vmList, algo,totalTime);
                        printCloudletList(newList,vmList);
                        Log.printLine("Simulation time:"+totalTime);
                        Log.printLine("CloudSimExample8 finished!");
                        
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
                    { costPerMem = 0.9;
                    }
                    else
                    {
                        costPerMem = 2;
                        costPerStorage = 0.5;
                    }

                LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


		    Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return datacenter;
	}

	//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	//to the specific rules of the simulated scenario
	private static DatacenterBroker createBroker(String name){

		DatacenterBroker broker = null;
		try {
			broker = new RoundRobinDatacenterBroker(name);
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
	private static void printCloudletList(List<Cloudlet> list,List<Vm> vm ) {
		int size = list.size();
		Cloudlet cloudlet;
                double totaltime=0;
		String indent = "    ";
                int size1=vm.size();
                float finish=0;
		float astart=0,afinish=0,autilization=0;
                float utilization[]=new float[size1];
                Arrays.fill(utilization, 0);
                Log.printLine();
                Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) 
                {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.vmId +
						indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) +
						indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime()));
			totaltime=totaltime+cloudlet.getActualCPUTime();
                        astart= (float) (astart+cloudlet.getExecStartTime());
                        afinish=(float) (afinish+cloudlet.getFinishTime());
                        
                       if(utilization[cloudlet.vmId]<cloudlet.getFinishTime())
                       utilization[cloudlet.vmId]=(float) cloudlet.getFinishTime();
                       finish=  (float) cloudlet.getFinishTime();
                        }
		}
                
                Log.printLine("total time:--"+totaltime);
                Log.printLine("Execution time time:--"+finish);
                Log.printLine("Average Start:--"+astart/list.size());
                Log.printLine("Average Finish:--"+afinish/list.size());
                for(int i=0;i<utilization.length;i++)
                {
                  Log.printLine("Utilization VMid:"+i+"--"+100*(utilization[i]/finish));
                        autilization=autilization+100*(utilization[i]/finish);
                }
                Log.printLine("Average Utilization:--"+autilization/vm.size());
	}
        
         private static void printCloudletList(List<Cloudlet> list, List<Datacenter> d ,List<Vm> v) {
        int size = list.size();
        Cloudlet cloudlet;

        String indent = "    ";
        Log.printLine();
        Log.printLine("========== OUTPUT ==========");
        Log.printLine("CloudletID" + indent + "STATUS" + indent
                + "DatacenterID" + indent + "VMID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time"+indent+"Datacenter0-cost");
indent = "    ";
        DecimalFormat dft = new DecimalFormat("###.##");
        for (int i = 0; i < size; i++) {
            cloudlet = list.get(i);
            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

Vm vm=v.get(cloudlet.getVmId());
if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS) {
                Log.print("SUCCESS");

                Log.printLine(indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId()
                        + indent + indent + indent + dft.format(cloudlet.getActualCPUTime())
                        + indent + indent + dft.format(cloudlet.getExecStartTime()) + indent + indent + indent + dft.format(cloudlet.getFinishTime()) );
            }
        }
    }

	



}

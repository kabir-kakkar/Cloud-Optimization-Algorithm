/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.power;

import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEvent;

/**
 * A broker for the power package.
 * 
 * If you are using any algorithms, policies or workload included in the power package please cite
 * the following paper:
 * 
 * Anton Beloglazov, and Rajkumar Buyya, "Optimal Online Deterministic Algorithms and Adaptive
 * Heuristics for Energy and Performance Efficient Dynamic Consolidation of Virtual Machines in
 * Cloud Data Centers", Concurrency and Computation: Practice and Experience, ISSN: 1532-0626, Wiley
 * Press, New York, USA, 2011, DOI: 10.1002/cpe.1867
 * 
 * @author Anton Beloglazov
 * @since CloudSim Toolkit 2.0
 */
public class PowerDatacenterBroker extends DatacenterBroker {

	/**
	 * Instantiates a new power datacenter broker.
	 * 
	 * @param name the name
	 * @throws Exception the exception
	 */
	public PowerDatacenterBroker(String name) throws Exception {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.cloudbus.cloudsim.DatacenterBroker#processVmCreate(org.cloudbus.cloudsim.core.SimEvent)
	 */
	@Override
	public  void processVmCreate(SimEvent ev) {
		int[] data = (int[]) ev.getData();
		int result = data[2];

		if (result != CloudSimTags.TRUE) {
			int datacenterId = data[0];
			int vmId = data[1];
			Log.printLine(CloudSim.clock() + ": " + getName() + ": Creation of VM #" + vmId
					+ " failed in Datacenter #" + datacenterId);
			System.exit(0);
		}
		super.processVmCreate(ev);
	}

}

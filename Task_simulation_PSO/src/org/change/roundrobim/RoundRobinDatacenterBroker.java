package org.change.roundrobim;

import java.util.List;

import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.core.SimEvent;

public class RoundRobinDatacenterBroker extends DatacenterBroker {

    public RoundRobinDatacenterBroker(String name) throws Exception {
        super(name);
    }

    @Override
    public void processResourceCharacteristics(SimEvent ev) {
        DatacenterCharacteristics characteristics = (DatacenterCharacteristics) ev.getData();
        getDatacenterCharacteristicsList().put(characteristics.getId(), characteristics);

        if (getDatacenterCharacteristicsList().size() == getDatacenterIdsList().size()) {
            createVmsInDatacenter(getDatacenterIdsList());
        }
    }

    public void createVmsInDatacenter(List<Integer> datacenterIds) {

        int requestedVms = 0;
        int i = 0;
        for (Vm vm : getVmList()) {
         //   Log.printLine("vm cost:"+vm.mips+":"+vm.ram+":"+vm.size+":vmId-"+vm.uid +"\n");
            int datacenterId = datacenterIds.get(i++ % datacenterIds.size());
            DatacenterCharacteristics d=datacenterCharacteristicsList.get(datacenterId);
            Log.printLine(d.getCostPerBw()+"--"+d.getCostPerMem());
            String datacenterName = CloudSim.getEntityName(datacenterId);
            if (!getVmsToDatacentersMap().containsKey(vm.getId())) {
                Log.printLine(CloudSim.clock() + ": " + getName() + ": Trying to Create VM #" + vm.getId() + " in " + datacenterName);
                sendNow(datacenterId, CloudSimTags.VM_CREATE_ACK, vm);
                requestedVms++;
            }
        }

        setVmsRequested(requestedVms);
        setVmsAcks(0);
    }
}
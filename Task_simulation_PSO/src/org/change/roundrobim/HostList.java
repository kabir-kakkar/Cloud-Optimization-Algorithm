package org.change.roundrobim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Host;

public final class HostList implements Iterable<Host> {

    private final List<Host> _list = new LinkedList<Host>();

    public HostList(List<? extends Host> hosts) {
        this._list.addAll(hosts);
    }

    public boolean add(Host host) {
        return this._list.add(host);
    }

    public boolean remove(Host host2Remove) {
        return this._list.remove(host2Remove);
    }

    @Override
    public Iterator<Host> iterator() {
        return get().iterator();
    }

    public List<Host> get() {
        return Collections.unmodifiableList(this._list);
    }

    public Host getWithMinimumNumberOfPesEquals(int numberOfPes) {
        List<Host> hosts = this.orderedAscByAvailablePes().get();

        for (int i = 0; i < hosts.size(); i++) {
            if (hosts.get(i).getNumberOfFreePes() >= numberOfPes) {
                return hosts.get(i);
            }
        }
        return null;
    }

    public int size() {
        return this._list.size();
    }

    public HostList orderedAscByAvailablePes() {
        List<Host> list = new ArrayList<Host>(this._list);

        Collections.sort(list, new Comparator<Host>() {
            @Override
            public int compare(Host o1, Host o2) {
                return Integer.valueOf(o1.getNumberOfFreePes()).compareTo(o2.getNumberOfFreePes());
            }
        });
        return new HostList(list);
    }
}
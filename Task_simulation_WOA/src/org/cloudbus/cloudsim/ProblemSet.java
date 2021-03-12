package org.cloudbus.cloudsim;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// this is the problem to be solved
// to find an x and a y that minimize the function below:
// f(x, y) = (2.8125 - x + x * y^4)^2 + (2.25 - x + x * y^2)^2 + (1.5 - x + x*y)^2
// where 1 <= x <= 4, and -1 <= y <= 1

// you can modify the function depends on your needs
// if your problem space is greater than 2-dimensional space
// you need to introduce a new variable (other than x and y)
import java.util.List;
import org.cloudbus.cloudsim.*;

public class ProblemSet {
	public static final double LOC_X_LOW = 1;
	public static double LOC_X_HIGH = 50;
//	public static final double LOC_Y_LOW = -1;
//	public static final double LOC_Y_HIGH = 1;
	public static final double VEL_LOW = 0;
	public static final double VEL_HIGH = 5;
	public static List<Vm> vmcreatedlist;
	public static final double ERR_TOLERANCE = 1E-20; // the smaller the tolerance, the more accurate the result, 
	                                                  // but the number of iteration is increased
	
	public static double evaluate(Location location) {
		double result = 0;
		double vmid = location.getLoc()[0]; // the "x" part of the location
		//double y = location.getLoc()[1]; // the "y" part of the location
		double exec=location.cloudlet.getCloudletLength()/vmcreatedlist.get((int) vmid).mips;
		Vm v=vmcreatedlist.get((int) vmid);
                DatacenterCharacteristics d=v.host.datacenter.characteristics;
                
                double execost=d.costPerSecond; 
                double cost=exec*execost;
                //Log.printLine(vmcreatedlist.get((int) vmid).getHost().datacenter.getName());
                //result = (cost *0.5)+(exec*0.5);
		result=exec;
		return result;
	}
}

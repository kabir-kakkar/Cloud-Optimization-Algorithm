package org.cloudbus.cloudsim;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// this is the heart of the PSO program
// the code is for 2-dimensional space problem
// but you can easily modify it to solve higher dimensional space problem

import java.util.List;
import java.util.Random;
import java.util.Vector;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Vm;

public class PSOProcess implements PSOConstants {
	private Vector<Particle> swarm = new Vector<Particle>();
	private double[] pBest = new double[SWARM_SIZE];
	private Vector<Location> pBestLocation = new Vector<Location>();
	private double gBest;
	private Location gBestLocation;
	private double[] fitnessValueList = new double[SWARM_SIZE];
	
	Random generator = new Random();
	
	public int findVM(Cloudlet c,List<Vm> vmcreatedlist) 
        {       ProblemSet.LOC_X_HIGH=vmcreatedlist.size()-1;
                ProblemSet.vmcreatedlist=vmcreatedlist;
                initializeSwarm(c);
		updateFitnessList();
		
		for(int i=0; i<SWARM_SIZE; i++) {
			pBest[i] = fitnessValueList[i];
			pBestLocation.add(swarm.get(i).getLocation());
		}
		
		int t = 0;
		double w;
		double err = 9999;
		
		while(t < MAX_ITERATION && err > ProblemSet.ERR_TOLERANCE) {
			// step 1 - update pBest
			for(int i=0; i<SWARM_SIZE; i++) {
				if(fitnessValueList[i] < pBest[i]) {
					pBest[i] = fitnessValueList[i];
					pBestLocation.set(i, swarm.get(i).getLocation());
				}
			}
				
			// step 2 - update gBest
			int bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
                        Log.printLine("local Best : " +fitnessValueList[bestParticleIndex] );
			if(t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
				gBest = fitnessValueList[bestParticleIndex];
				
                                gBestLocation = swarm.get(bestParticleIndex).getLocation();
                                Log.printLine(" global Best : " +gBest );
                        }
			
			w = W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);
			
			for(int i=0; i<SWARM_SIZE; i++) {
				double r1 = generator.nextDouble();
				double r2 = generator.nextDouble();
				
				Particle p = swarm.get(i);
				
				// step 3 - update velocity
				double[] newVel = new double[PROBLEM_DIMENSION];
				newVel[0] = ProblemSet.VEL_LOW + (int)(Math.random() * ProblemSet.VEL_HIGH); ;
//				newVel[1] = (w * p.getVelocity().getPos()[1]) + 
//							(r1 * C1) * (pBestLocation.get(i).getLoc()[1] - p.getLocation().getLoc()[1]) +
//							(r2 * C2) * (gBestLocation.getLoc()[1] - p.getLocation().getLoc()[1]);
				Velocity vel = new Velocity(newVel);
				p.setVelocity(vel);
				
				// step 4 - update location
				double[] newLoc = new double[PROBLEM_DIMENSION];
				newLoc[0] = p.getLocation().getLoc()[0] + newVel[0];
                                
                                if(newLoc[0]>ProblemSet.LOC_X_HIGH)
                                {newLoc[0]=newLoc[0]%ProblemSet.LOC_X_HIGH;
                                }
				//newLoc[1] = p.getLocation().getLoc()[1] + newVel[1];
				//Location loc = new Location(newLoc);
				p.location.setLoc(newLoc);
                                //p.setLocation(loc);
                                //Log.printLine("LOc " +  newLoc[0]+ ": ");
			}
			
			err = ProblemSet.evaluate(gBestLocation) - 0; // minimizing the functions means it's getting closer to 0
			
			
			Log.printLine("ITERATION " + t + ": ");
			Log.printLine("     VMID: " + gBestLocation.getLoc()[0]);
			//Log.printLine("     Best Y: " + gBestLocation.getLoc()[1]);
			Log.printLine("    Fitness: " + ProblemSet.evaluate(gBestLocation));
			
			t++;
			updateFitnessList();
		}
		
		Log.printLine("\nSolution found at iteration " + (t - 1) + ", the solutions is:");
		Log.printLine("     VM ID: " + gBestLocation.getLoc()[0]);
		
	return (int) gBestLocation.getLoc()[0];
        }
	
	public void initializeSwarm(Cloudlet c) {
		Particle p;
		for(int i=0; i<SWARM_SIZE; i++) {
			p = new Particle();
			
			// randomize location inside a space defined in Problem Set
			double[] loc = new double[PROBLEM_DIMENSION];
			loc[0] = ProblemSet.LOC_X_LOW +(int)(Math.random() * ProblemSet.LOC_X_HIGH); 
                        			//loc[1] = ProblemSet.LOC_Y_LOW + generator.nextDouble() * (ProblemSet.LOC_Y_HIGH - ProblemSet.LOC_Y_LOW);
			Location location = new Location(loc);
                        location.cloudlet=c;
			//Log.printLine(location.cloudlet.cloudletLength);
			// randomize velocity in the range defined in Problem Set
			double[] vel = new double[PROBLEM_DIMENSION];
			vel[0] = ProblemSet.VEL_LOW + (int)(Math.random() * ProblemSet.VEL_HIGH); 
                        
			//vel[1] = ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);
			Velocity velocity = new Velocity(vel);
			
			p.setLocation(location);
			p.setVelocity(velocity);
			swarm.add(p);
		}
	}
	
	public void updateFitnessList() {
		for(int i=0; i<SWARM_SIZE; i++) {
			fitnessValueList[i] = swarm.get(i).getFitnessValue();
                       // Log.printLine("iniBest" + fitnessValueList[i]+ ": ");
		}
	}
}

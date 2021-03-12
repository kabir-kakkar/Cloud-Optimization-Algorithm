library("rjson")
library(metaheuristicOpt)
data=result <- fromJSON(txt="C:\\Users\\KABIR KAKKAR\\Desktop\\Major Project 8th Sem\\Task Simulation\\Task_simulation_PSO\\data.json")
vm=unlist(data["VM"])
vmcount=data["vmcount"]
tcount=data["tcount"]
task=unlist(data["task"])
##################################
## Optimizing the sphere function
tot=0
print(vmcount[[1]])
start=vector("numeric", vmcount[[1]])
# define sphere function as objective function
sphere <- function(x){
  
  for (i in seq(1,as.numeric(tcount[1],by=1))){
    start[x[i]]=start[x[i]]+(as.numeric(task[[i]])/as.numeric(vm[x[i]]))
  }
  tot=max(start)
  #print(tot)
  return(tot)
}

## Define parameter
Vmax <- 2
ci <- 1.5
cg <- 1.5
w <- 0.7
numVar <-as.numeric(tcount[1])
rangeVar <- matrix(c(1,as.numeric(vmcount[1])), nrow=2)
#print(rangeVar)

## calculate the optimum solution using Particle Swarm Optimization Algorithm
resultPSO <- PSO(sphere, optimType="MIN", numVar, numPopulation=20,
                 maxIter=500, rangeVar, Vmax, ci, cg, w)

randomVM<- sapply(resultPSO,round)
## calculate the optimum value using sphere function
optimum.value <- sphere(randomVM)

print(randomVM)
print(optimum.value)
print(start)

library(jsonlite)
list1 <- vector(mode="list", length=0) 
list1[["name"]] <- c(as.character(randomVM))
exportJSON <- toJSON(list1)
write(exportJSON, "C:\\Users\\KABIR KAKKAR\\Desktop\\Major Project 8th Sem\\Task Simulation\\Task_simulation_PSO\\sample.json")
import random
from BatAlgorithm1 import *
import json 
  

def Fun(D, sol):
    list1 = [0] * data['vmcount'][0]
    val = 0.0
    for i in range(D):
        #print(round(sol[i])-1)
        list1[round(sol[i]-1)] = list1[round(sol[i]-1)] + data['task'][i]/data['VM'][round(sol[i]-1)]
    
    return max(list1)

# For reproducive results
#random.seed(5)
f = open('data.json',) 
data = json.load(f)  
size=data['tcount'][0]
vmcount= data['vmcount'][0]

for i in range(1):
    
    Algorithm = BatAlgorithm(size, 100, 500, 0.5, 0.5, 0.0, 2.0, 1.0,vmcount, Fun)
    sol=Algorithm.move_bat()
    s=[round(num-1) for num in sol.best]
    print(sol.f_min)
    print(s)
    for i in range(0, len(s)): 
      s[i] = str(s[i])
    # a Python object (dict):
    data1 = {
      "name": s
     
    }
    
    # convert into JSON:
   # y = json.dumps(data1)    
        
   
    
    with open("sample.json", "w") as outfile: 
        json.dump(data1, outfile,ensure_ascii=False, indent=4)
       

    
  


    
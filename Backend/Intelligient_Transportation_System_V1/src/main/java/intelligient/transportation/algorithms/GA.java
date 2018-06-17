package intelligient.transportation.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GA implements Solution {
	public ArrayList<Chromosome> population = new ArrayList<Chromosome>();
	   public ArrayList<Chromosome> Offsprings = new ArrayList<Chromosome>();
	   public ArrayList<point> requests = new ArrayList<point>();
	   public int popSize;
	   public int maxGeneration;
	   public ArrayList<Double> fitness;
	   
	   
	  
	   public void InitializePopulation(){
	     
	       for(int i=0 ; i<popSize ; i++){
	      
	            Chromosome c = new Chromosome();
	            c.genes.addAll(requests);
	            Collections.shuffle(c.genes);
	           population.add(c);
	       }
	  }
	   
	   public ArrayList<Double> EvaluateFitness() {
	       ArrayList<Double> fitnessList = new ArrayList<Double>();
	       for(int i=0 ; i<popSize ; i++){
	           Chromosome c=population.get(i);
	           Double totalDistance=0.0;
	          for(int j=1 ; j<c.genes.size() ; j++){
	              totalDistance+=(Haversine.HaversineInM(c.genes.get(j-1).latitude,c.genes.get(j-1).longitude, c.genes.get(j).latitude,c.genes.get(j).longitude));
	          }
	          fitnessList.add(1.0/totalDistance);
	         
	       }
	        return fitnessList;
	    }
	   public int Select(){
	      double Max=0,r,sum=0;
	       for(int i=0 ; i<fitness.size(); i++){
	         Max+=fitness.get(i);
	       }
	       r = (Math.random() * (Max));
	       for(int i=0 ; i<fitness.size() ; i++){
	           sum+=fitness.get(i);
	           if(r<=sum)
	               return i;
	       
	       }
	     return -1;
	   }
	   
	   public boolean ParentAvailableInChild(int parentRequestId , point child[],int R1,int R2 ){
		    for(int i=R1 ; i<R2 ; i++)
		         if(child[i].requestId==parentRequestId)
		              return true;
		     
		     return false;
		   }
		   public point[] OrderOneCrossover(Chromosome p1 , Chromosome p2,int R1,int R2,int size){
		       point child[]=new point[size];
		       
		       for(int i=R1 ;i<R2 ; i++)
		           child[i]=p1.genes.get(i);
		       
		       int j =R2%size,k=R2%size;
		       while(child[k]==null){
		          if(!ParentAvailableInChild(p2.genes.get(j).requestId, child, R1, R2)){
		             child[k]=p2.genes.get(j);
		             k=(k+1)%size;
		          }
		          j=(j+1)%size;
		       }
		       return child;
		       
		 
		   }
		   public ArrayList<Chromosome> CrossOver(Chromosome p1 , Chromosome p2 , int chromosomeSize){
		     //  ArrayList<Integer> L2 = new ArrayList<>(L1.subList(0, 2));
		       ArrayList<Chromosome> offsp = new   ArrayList<Chromosome>();
		       double pr=Math.random();
		       Chromosome o1=new Chromosome();
		       Chromosome o2=new Chromosome();
		       if(pr<=0.7){
		        int size =requests.size();
		        Random rand = new Random(); 
		        int R1 = rand.nextInt(size);
		        rand = new Random(); 
		        int R2 =  rand.nextInt((size+1)-R1) + (R1);

		       
		          point child1[]= OrderOneCrossover(p1,p2,R1,R2,size);
		          point child2[]= OrderOneCrossover(p2,p1,R1,R2,size);
		          
		          o1.genes=new ArrayList<point>(Arrays.asList(child1));
		          o2.genes=new ArrayList<point>(Arrays.asList(child2));
		         offsp.add(o1);
		         offsp.add(o2);
		       }else{
		         o1=p1;
		         o2=p2;
		         offsp.add(o1);
		         offsp.add(o2);
		         
		       
		       }
		       
		       return offsp;
		   }
		   // Centre inverse mutation (CIM) 
		 public Chromosome Mutation(Chromosome c){
		       double pm;
		        pm=Math.random();
		         if(pm<=0.01){
		            Random rand = new Random(); 
		              int position1 = rand.nextInt(requests.size());
		              int position2 = rand.nextInt(requests.size());
		                Collections.swap(c.genes, position1, position2);   
		        }   
		      return c;
		   
		   }
		 
		 public void Replacement(){
		       population =new ArrayList<Chromosome>();
		       for(int i=0 ; i<Offsprings.size() ; i++){
		           population.add(Offsprings.get(i));
		       }
		   }
		 
		 
		 public Chromosome RunCanonicalAlgorithm(ArrayList<point>requests){
		       this.requests=requests;
		       popSize=50;
		       maxGeneration=100;
		       int chromosomeSize=requests.size();
		       InitializePopulation();
		       fitness = EvaluateFitness();

		       //Generation LOOP
		      for(int j = 0 ; j<maxGeneration ; j++){
		        fitness = EvaluateFitness();
		      
		        System.out.println("\n\n");
		       for(int i=0 ; i<Math.ceil(popSize/2.0) ;i++){
		             int ParentIndex=Select();
		             //if(ParentIndex==-1){stop=true;break;}
		             int ParentIndex2=Select();
		            ArrayList<Chromosome> offsp= CrossOver(population.get(ParentIndex) , population.get(ParentIndex2) ,chromosomeSize);
		            Chromosome MutatedOffspring1=Mutation(offsp.get(0));
		            Chromosome MutatedOffspring2=Mutation(offsp.get(1));

		            Offsprings.add(MutatedOffspring1);
		            Offsprings.add(MutatedOffspring2);
		           
		       }
		   
		       Replacement();
		     }
		      fitness = EvaluateFitness();
		     
		    double mx=-1;int index=-1;
		    for(int i=0 ; i<fitness.size() ; i++)
		         if(fitness.get(i)>mx)
		            {mx=fitness.get(i);index=i;}
		       
		       return population.get(index);
		   }

		@Override
		public ArrayList<point> construct(ArrayList<point> requests) {
			Chromosome c=this.RunCanonicalAlgorithm(requests);
			ArrayList<point> Orderedrequests=new ArrayList<point>();
			for(int i=0 ; i<c.genes.size();i++)
				Orderedrequests.add(c.genes.get(i));
			return Orderedrequests;
		}
		 
		 
}

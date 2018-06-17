package intelligient.transportation.algorithms;

import java.util.ArrayList;

public class Cluster {
	
	
	 public ArrayList<ArrayList<point>> algorithm(ArrayList<point> requests,int numberOfClusters){
         int PNO=requests.size();
         ArrayList<ArrayList<point>> clusters = new ArrayList<ArrayList<point>>();
         for(int i= 0 ; i<requests.size() ;i++){
          ArrayList<point>cluster = new ArrayList<point>();
          cluster.add(requests.get(i));
          clusters.add(cluster);
         }
         
    //   return   clusters;
          while(PNO>numberOfClusters)
          {
        	
          double dist[][]=new double[PNO][PNO];
          double min=100000;
          int m1=0,m2=0;
          for(int i=0 ; i<clusters.size() ; i++){
           for(int j=0 ; j<clusters.size() ;j++){
             double minDist = 1000000;
             for(int k=0 ; k<clusters.get(i).size() ; k++){
               for(int w=0 ; w<clusters.get(j).size() ; w++){
                 double distance = Haversine.HaversineInKM(clusters.get(i).get(k).latitude,clusters.get(i).get(k).longitude,
                		                              clusters.get(j).get(w).latitude,clusters.get(j).get(w).longitude);
                 if(distance<minDist){
                   minDist=distance;
                 }
                 
               }
             }
             
             dist[i][j]=minDist;
             if(dist[i][j]<min && i!=j){
              min=dist[i][j];
              m1=i;m2=j;
             }
           }
          }
         
             for(int i =0 ; i<clusters.get(m2).size();i++){
                 clusters.get(m1).add(clusters.get(m2).get(i));
             }
             clusters.remove(m2);
       
           PNO--;
     }
          
       return clusters;
     }
}

package intelligient.transportation.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import com.google.maps.errors.ApiException;

public class Cluster {
	
	
	 public ArrayList<ArrayList<point>> algorithm(ArrayList<point> requests,int numberOfClusters,long[][] distanceMatrix) throws ApiException, InterruptedException, IOException{
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
          double min=Double.MAX_VALUE;
          int m1=0,m2=0;
          for(int i=0 ; i<clusters.size() ; i++){
           for(int j=0 ; j<clusters.size() ;j++){
             double minDist = Double.MAX_VALUE;
             for(int k=0 ; k<clusters.get(i).size() ; k++){
               for(int w=0 ; w<clusters.get(j).size() ; w++){
            	  /* String origin =String.valueOf(clusters.get(i).get(k).latitude)+","+String.valueOf(clusters.get(i).get(k).longitude);
            	   String destination =String.valueOf(clusters.get(j).get(w).latitude)+","+String.valueOf(clusters.get(j).get(w).longitude);*/
                 double distance = distanceMatrix[clusters.get(i).get(k).index][clusters.get(j).get(w).index];
                 System.out.println("Distance = "+distance);
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

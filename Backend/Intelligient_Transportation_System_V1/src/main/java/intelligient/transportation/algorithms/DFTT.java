package intelligient.transportation.algorithms;

import java.io.IOException;
import java.util.ArrayList;
import com.google.maps.errors.ApiException;

import intelligient.transportation.algorithms.Graph.Edge;

public class DFTT implements Solution {

	
	@Override
	public ArrayList<point> construct(ArrayList<point> requests, long[][] distanceMatrix)
			throws ApiException, InterruptedException, IOException {

        long [][] DistanceMatrix = new long[requests.size()][requests.size()];
        DistanceMatrix = getRouteDistanceMatrix(distanceMatrix, requests);
        
        int V = requests.size();
        int E = V + 1;
        Graph graph = new Graph(V, E);
        int idx = 0;
        
        for (int i = 0; i < requests.size(); i++ ){
        	for (int j = i ; j < requests.size(); j++){
        		if (i != j){
	        		graph.edge[idx].src = requests.get(i).index;
	                graph.edge[idx].dest = requests.get(j).index;
	                long distance = DistanceMatrix[graph.edge[idx].src][graph.edge[idx].dest];
	                graph.edge[idx++].weight = (int) distance;   	
        		}
        	}
        }
         
        Edge [] mst = graph.KruskalMST();
        ArrayList<Integer> best = graph.getBestRoute(mst);
		ArrayList<point> orderedRoute = new ArrayList<>();
		for(int i = 0 ; i < best.size(); i++){
			for(int j = 0; j < best.size(); j++){
				if (best.get(i) == requests.get(j).index){
					orderedRoute.add(requests.get(j));
					break;
				}
			}
		}
		return orderedRoute;
	}

   public long [][] getRouteDistanceMatrix(long [][] allRoutesDistanceMatrix, ArrayList<point> llpoints){
		int numberOfPoints = llpoints.size();
		long [][] routeDistanceMatrix = new long [numberOfPoints][numberOfPoints] ;
    	
    	for(int i=0;i<numberOfPoints ; i++)
    	{
    		for(int j=0;j<numberOfPoints;j++)
    		{
    			
    			routeDistanceMatrix[i][j] = 
    					allRoutesDistanceMatrix[llpoints.get(i).index][llpoints.get(j).index];
    			//getDistanceFromLatLonInKm(points.get(i).latitude, points.get(i).longitude,
    							//points.get(j).latitude, points.get(i).longitude);

				System.out.print(routeDistanceMatrix[i][j]+" ");
    		}

			System.out.println();
    	}
    	System.out.println();
    	return routeDistanceMatrix;
	}
  
}
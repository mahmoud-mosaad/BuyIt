package intelligient.transportation.algorithms;
import java.util.ArrayList;
public class NN implements Solution {

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

    void IntiallVisited(int array[], int size) {
        for (int i = 0; i < size; i++) {
            array[i] = -1;

        }
    }

    boolean AllVisted(int array[], int size) {
        boolean flage = false;
        for (int i = 0; i < size; i++) {
            if (array[i] == -1) {
                flage = false;
            } else {
                flage = true;
            }

        }
        return flage;
    }

    public ArrayList<point> GetRoot(ArrayList<point> MyRoot,long [][]allRoutesDistanceMatrix ) {
        int index = 0, nextIndex = 0;
        ArrayList<point> root = new ArrayList<point>();

        double minDistance = Integer.MAX_VALUE, ActualDistance;
        int numberOfPoints = MyRoot.size();
        int[] visited = new int[numberOfPoints];
        IntiallVisited(visited, numberOfPoints);

        long [][] distanceMatrix = new long[numberOfPoints][numberOfPoints];
        distanceMatrix = getRouteDistanceMatrix(allRoutesDistanceMatrix,MyRoot);
        visited[0] = 0;
        root.add(MyRoot.get(0));
        while (!AllVisted(visited, numberOfPoints)) {
            for (int i = index; i <numberOfPoints; i++) {
                minDistance = Integer.MAX_VALUE;
                for (int j = 0; j < numberOfPoints; j++) {
                        if (distanceMatrix[i][j] < minDistance && i != j) {
                            if (visited[j] == -1) {
                                minDistance = distanceMatrix[i][j];
                                System.out.println("distanceMatrix= " + distanceMatrix[i][j] + "  " + "  minDistance=" + minDistance);
                                nextIndex = j;  
                            } 
                        }
                    
                }
                if (visited[nextIndex] == -1) {
                        visited[nextIndex] = 0;
                        root.add(MyRoot.get(nextIndex));
                        index = nextIndex;
                   
                    }
                
                

            }

        
        }
        return root;
    }
    

    @Override
    public ArrayList<point> construct(ArrayList<point> requests,long [][]allRoutesDistanceMatrix ) {
        return GetRoot(requests,allRoutesDistanceMatrix );
    }

}

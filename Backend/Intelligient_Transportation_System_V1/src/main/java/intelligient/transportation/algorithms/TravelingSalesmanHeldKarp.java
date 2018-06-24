package intelligient.transportation.algorithms;

import java.util.*;

public class TravelingSalesmanHeldKarp implements Solution{

    private static int INFINITY = 100000000;
    ArrayList<Integer> bestRouteOrder;
    public TravelingSalesmanHeldKarp(){
    	 bestRouteOrder = new ArrayList<Integer>(); 
    
    }
    
    public ArrayList<point> construct(ArrayList<point> llpoints, long[][]distanceMatrix){
    	
    	double minCost = minCost(getRouteDistanceMatrix(distanceMatrix, llpoints));
    	
		ArrayList<point> orderedPoints = new ArrayList<point>();
		
		for (int i=0;i<this.bestRouteOrder.size()-1; i++){
		    int idx = this.bestRouteOrder.get(i);
			//System.out.print(idx+" ");
		    orderedPoints.add(llpoints.get(idx));
		}
		
		//System.out.println();

		
		return orderedPoints;
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
	
	
    
    //public static double[][]
    public double [][] getDistanceMatrix(ArrayList<point> points){
    	int numberOfPoints = points.size();
    	double [][] distanceMatrix = new double [numberOfPoints][numberOfPoints] ;
    	
    	for(int i=0;i<numberOfPoints ; i++)
    	{
    		for(int j=0;j<numberOfPoints;j++)
    		{
    			if(i==j)
    			{
    				distanceMatrix[i][j] = 0;
    				//System.out.print(distanceMatrix[i][j]+" ");
    				continue;
    			}
    			
    			distanceMatrix[i][j] = 
    					getDistanceFromLatLonInKm(points.get(i).latitude, points.get(i).longitude,
    							points.get(j).latitude, points.get(i).longitude);

				//System.out.print(distanceMatrix[i][j]+" ");
    		}

			//System.out.println();
    	}
    	//System.out.println();
    	return distanceMatrix;
    }
    
    
    
    //calculte distance btw 2 points using Haversine formula
    public int getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
    	double R = 6371; // Radius of the earth in km
    	double dLat = deg2rad(lat2-lat1);  // deg2rad below
    	double dLon = deg2rad(lon2-lon1); 
    	double a = 
    	    Math.sin(dLat/2) * Math.sin(dLat/2) +
    	    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
    	    Math.sin(dLon/2) * Math.sin(dLon/2)
    	    ; 
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
    	double d = R * c; // Distance in km
    	  return (int) Math.round(d);
    	}

	public double deg2rad(double deg) {
	  return deg * (Math.PI/180);
	}
    
    
    
    private static class Index {
        int currentVertex;
        Set<Integer> vertexSet;
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Index index = (Index) o;

            if (currentVertex != index.currentVertex) return false;
            return !(vertexSet != null ? !vertexSet.equals(index.vertexSet) : index.vertexSet != null);
        }

        @Override
        public int hashCode() {
            int result = currentVertex;
            result = 31 * result + (vertexSet != null ? vertexSet.hashCode() : 0);
            return result;
        }

        private static Index createIndex(int vertex, Set<Integer> vertexSet) {
            Index i = new Index();
            i.currentVertex = vertex;
            i.vertexSet = vertexSet;
            return i;
        }
    }

    private static class SetSizeComparator implements Comparator<Set<Integer>>{
        @Override
        public int compare(Set<Integer> o1, Set<Integer> o2) {
            return o1.size() - o2.size();
        }
    }

    public double minCost(long[][] distance) {

        //stores intermediate values in map
        Map<Index, Double> minCostDP = new HashMap<Index, Double>();
        Map<Index, Integer> parent = new HashMap<Index, Integer>();

        List<Set<Integer>> allSets = generateCombination(distance.length - 1);

        for(Set<Integer> set : allSets) {
            for(int currentVertex = 1; currentVertex < distance.length; currentVertex++) {
                if(set.contains(currentVertex)) {
                    continue;
                }
                Index index = Index.createIndex(currentVertex, set);
                double minCost = INFINITY;
                int minPrevVertex = 0;
                //to avoid ConcurrentModificationException copy set into another set while iterating
                Set<Integer> copySet = new HashSet<Integer>(set);
                for(int prevVertex : set) {
                	double cost = distance[prevVertex][currentVertex] + getCost(copySet, prevVertex, minCostDP);
                    if(cost < minCost) {
                        minCost = cost;
                        minPrevVertex = prevVertex;
                    }
                }
                //this happens for empty subset
                if(set.size() == 0) {
                    minCost = distance[0][currentVertex];
                }
                minCostDP.put(index, minCost);
                parent.put(index, minPrevVertex);
            }
        }

        Set<Integer> set = new HashSet<Integer>();
        for(int i=1; i < distance.length; i++) {
            set.add(i);
        }
        double min = Integer.MAX_VALUE;
        int prevVertex = -1;
        //to avoid ConcurrentModificationException copy set into another set while iterating
        Set<Integer> copySet = new HashSet<Integer>(set);
        for(int k : set) {
        	double cost = distance[k][0] + getCost(copySet, k, minCostDP);
            if(cost < min) {
                min = cost;
                prevVertex = k;
            }
        }

        parent.put(Index.createIndex(0, set), prevVertex);
        printTour(parent, distance.length);
        return min;
    }

    private void printTour(Map<Index, Integer> parent, int totalVertices) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i=0; i < totalVertices; i++) {
            set.add(i);
        }
        Integer start = 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        while(true) {
            stack.push(start);
            set.remove(start);
            start = parent.get(Index.createIndex(start, set));
            if(start == null) {
                break;
            }
        }
        //StringJoiner joiner = new StringJoiner("->");
        //stack.forEach(v -> joiner.add(String.valueOf(v)));
        //System.out.println("\nTSP tour");
        //System.out.println(joiner.toString());
        while(!stack.isEmpty())
        {
            int temp = stack.pop();
            this.bestRouteOrder.add(temp);
        }
        //stack.forEach(v -> this.bestRouteOrder.add(v));
        
    }

    private double getCost(Set<Integer> set, int prevVertex, Map<Index, Double> minCostDP) {
        set.remove(prevVertex);
        Index index = Index.createIndex(prevVertex, set);
        double cost = minCostDP.get(index);
        set.add(prevVertex);
        return cost;
    }

    private List<Set<Integer>> generateCombination(int n) {
        int input[] = new int[n];
        for(int i = 0; i < input.length; i++) {
            input[i] = i+1;
        }
        List<Set<Integer>> allSets = new ArrayList<Set<Integer>>();
        int result[] = new int[input.length];
        generateCombination(input, 0, 0, allSets, result);
        Collections.sort(allSets, new SetSizeComparator());
        return allSets;
    }

    private void generateCombination(int input[], int start, int pos, List<Set<Integer>> allSets, int result[]) {
        if(pos == input.length) {
            return;
        }
        Set<Integer> set = createSet(result, pos);
        allSets.add(set);
        for(int i=start; i < input.length; i++) {
            result[pos] = input[i];
            generateCombination(input, i+1, pos+1, allSets, result);
        }
    }

    private static Set<Integer> createSet(int input[], int pos) {
        if(pos == 0) {
            return new HashSet<Integer>();
        }
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < pos; i++) {
            set.add(input[i]);
        }
        return set;
    }
}





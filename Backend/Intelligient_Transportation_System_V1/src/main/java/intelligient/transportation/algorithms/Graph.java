package intelligient.transportation.algorithms;

import java.util.*;
 
class Graph
{

	class Edge implements Comparable<Edge>
    {
        int src, dest, weight;
 
        public int compareTo(Edge compareEdge)
        {
            return this.weight-compareEdge.weight;
        }
    };
 
    class subset
    {
        int parent, rank;
    };
 
    int V, E;
    Edge edge[];
 
    Graph(int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }
 
    int find(subset subsets[], int i)
    {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
 
        return subsets[i].parent;
    }
 
    void Union(subset subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
 
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
 
        else
        {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
 
    Edge [] KruskalMST()
    {
        Edge result[] = new Edge[V];  
        int e = 0; 
        int i = 0; 
        for (i=0; i<V; ++i)
            result[i] = new Edge();
 
        Arrays.sort(edge);
 
        subset subsets[] = new subset[V];
        for(i=0; i<V; ++i)
            subsets[i]=new subset();
 
        for (int v = 0; v < V; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
 
        i = 0;  
 
        while (e < V - 1)
        {
            Edge next_edge = new Edge();
            next_edge = edge[i++];
 
            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);
 
            if (x != y)
            {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
 
        System.out.println("Following are the edges in the constructed MST");
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src+" -- " + 
                   result[i].dest + " == " + result[i].weight);
        return result;
    }
    
    ArrayList<Integer> getBestRoute(Edge [] edges){
        ArrayList<Integer> bestRoute = new ArrayList<Integer>();
        int beg = edges[0].src, end = edges[0].dest;
        bestRoute.add(beg);
        bestRoute.add(end);
        for(int i=1; i < edges.length-1; i++){
            for(int j = 1; j < edges.length-1; j++){
                if ( edges[j].src == end && edges[j].src != -1){
                    beg = edges[j].src;
                    end = edges[j].dest;
                    bestRoute.add(end);
                    edges[j].src = -1;
                    edges[j].dest = -1;
                } else if ( edges[j].dest == end && edges[j].dest != -1){
                    beg = edges[j].dest;
                    end = edges[j].src;
                    bestRoute.add(end);
                    edges[j].src = -1;
                    edges[j].dest = -1;
                }
            }
        }
        for( int i = bestRoute.size()-2; i >= 0; i-- ){
            bestRoute.add(bestRoute.get(i));
        }
        return bestRoute;
    }
 
}

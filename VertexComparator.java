/*
* File: VertexComparator.java
* Author: Riley Karp
* Date: 11/28/2016
*/

import java.util.Comparator;

public class VertexComparator implements Comparator<Vertex> {

	//returns positive number if the cost value of v1 is smaller than the cost value of v2
    public int compare( Vertex v1, Vertex v2 ) {
    	int c1 = v1.getCost();
    	int c2 = v2.getCost();
        if( c1 < c2 ) {
        	return 1;
        }
        else if( c1 > c2 ) {
        	return -1;
        }
        return 0;
    }
}
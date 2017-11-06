/*
* File: KVComparator.java
* Author: Riley Karp
* Date: 11/20/2016
*/

import java.util.Comparator;

public class KVComparator implements Comparator<KeyValuePair<String,Integer>> {

	//returns negative number if the value of p1 is smaller than the value of p2
    public int compare( KeyValuePair<String,Integer> p1, KeyValuePair<String,Integer> p2 ) {
        return p1.getValue().compareTo( p2.getValue() );
    }
}
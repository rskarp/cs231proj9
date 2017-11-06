/*
* File: KeyValuePair.java
* Author: Riley Karp
* Date: 11/01/2016
*/

public class KeyValuePair<Key,Value> {

	private Key k;
	private Value v;
	
	//creates KeyValuePair object with the given key and value
	public KeyValuePair( Key k, Value v ) {
		this.k = k;
		this.v = v;
	}
	
	//returns the key
	public Key getKey() {
		return this.k;
	}
	
	//returns the value
	public Value getValue() {
		return this.v;
	}
	
	//sets the value to the specified value
	public void setValue( Value v ) {
		this.v = v;
	}
	
	//returns a string in the form (key,value)
	public String toString() {
		return "(" + k + "," + v + ")";
	}
	
	//tests methods of the KeyValuePair class
	public static void main( String args[] ) {
		KeyValuePair<String,Integer> pair = new KeyValuePair<String,Integer>("dog",1);
		System.out.println( "Key: " + pair.getKey() + ", Value: " + pair.getValue() );
		pair.setValue( 2 );
		System.out.println( "After resetting value:\n" + pair.toString() );
	}
}
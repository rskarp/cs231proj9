/*
* File: Agent.java
* Author: Riley Karp
* Date: 11/04/2016
*/

import java.awt.Graphics;

public class Agent {

	private int row;
	private int col;
	
	//creates an Agent object at specified x,y position
	public Agent( int row, int col ) {
		this.col = col;
		this.row = row;
	}
	
	//returns row location
	public int getRow() {
		return row;
	}
	
	//returns column location
	public int getCol() {
		return col;
	}
	
	///sets row location
	public void setRow( int newRow ) {
		row = newRow;
	}
	
	//sets column location
	public void setCol( int newCol ) {
		col = newCol;
	}
	
	//returns a string with the agent's (row,col) position
	public String toString() {
		return "(" + this.getRow() + ", " + this.getCol() + ")";
	}
	
	//does nothing
	public void draw( Graphics g, int gridScale ) {
	}
	
	//tests all methods of Agent class
	public static void main( String[] args ) {
		Agent a = new Agent( 2, 4 );
		System.out.println( a.toString() );
		a.setRow( 6);
		a.setCol( 8 );
		System.out.println( a.toString() );
	}
}
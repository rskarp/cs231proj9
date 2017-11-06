/*
* File: PQHeap.java
* Author: Riley Karp
* Date: 11/21/2016
*/

import java.util.Comparator;
import java.util.ArrayList;

public class PQHeap<T> {

	private ArrayList<T> heap;
	private int size;
	private Comparator<T> comp;
	
	//creates an empty PQHeap object
	public PQHeap( Comparator<T> comparator ) {
		heap = new ArrayList<T>();
		size = 0;
		comp = comparator;
	}
	
	//returns the size of the heap
	public int size() {
		return this.size;
	}
	
	//used by public add method to reorganize objects into correct order
	private void reorganizeAdd( T obj, int objIDX ) {
		int parent = (objIDX - 1 )/2;
		if( comp.compare( obj, heap.get(parent) ) > 0 ) {
			heap.set( objIDX, heap.get(parent) );
			heap.set( parent, obj );
			reorganizeAdd( obj, parent );
		}
	}
	
	//adds the specified value to the heap
	public void add( T obj ) {
		heap.add( obj );
		size++;
		if( size > 1 ) {
			this.reorganizeAdd( obj, heap.size()-1 );
		}
	}
	
	//used by public remove method to reorganize objects into correct order
	private void reorganizeRemove( int curIDX ) {
		if( curIDX < size ) {
			int left = (2*curIDX) + 1;
			int right = (2*curIDX) + 2;
			T obj = heap.get(curIDX);
			if( right < heap.size() ) {
				if( (comp.compare( heap.get(left), heap.get(right) ) < 0) &&
					(comp.compare( obj, heap.get(right) ) < 0) ) {
					heap.set( curIDX, heap.get(right) );
					heap.set( right, obj );
					reorganizeRemove( right );
					reorganizeRemove( left );
				}
				else if( (comp.compare( heap.get(left), heap.get(right) ) > 0) &&
					(comp.compare( obj, heap.get(left) ) < 0) ) {
					heap.set( curIDX, heap.get(left) );
					heap.set( left, obj );
					reorganizeRemove( left );
					reorganizeRemove( right );
				}
			}
			else if( (left < heap.size()) && (right >= heap.size()) ) {
				if( comp.compare( obj, heap.get(left) ) < 0 ) {
					heap.set( curIDX, heap.get(left) );
					heap.set( left, obj );
					reorganizeRemove( left );
				}
			}
		}
	}
	
	//removes and returns the highest priority element from heap
	public T remove() {
		if( size > 0 ) {
			size--;
			T val = heap.remove( 0 );
			if( heap.size() > 1 ) {
				int lastIDX = heap.size() - 1;
				this.add( heap.remove(lastIDX) );
				size--;
				this.reorganizeRemove( 0 );
			}
			return val;
		}
		else {
			return null;
		}
	}
	
	//removes the specified item from the heap; returns true if it's there, false otherwise
	public boolean removeItem( T item ) {
		return heap.remove( item );
	}
	
	//prints the heap in the order that it's stored in the ArrayList
	public void printHeap() {
		System.out.println( heap );
	}
	
	//tests methods of the heap
	public static void main( String args[] ) {
		PQHeap<Integer> h = new PQHeap<Integer>(new TestIntComparator());
		System.out.println( "Testing Add" );
		for( int i = 0; i < 10; i++ ) {
			h.add(i);
			System.out.println( "Size: " + h.size() );
			h.printHeap();
		}
		
		System.out.println( "\nTesting Remove" );
		for( int i = 0; i < 11; i++ ) {
			System.out.println( "Removed: " + h.remove() );
			System.out.println( "Size: " + h.size() );
			h.printHeap();
		}
	}
}
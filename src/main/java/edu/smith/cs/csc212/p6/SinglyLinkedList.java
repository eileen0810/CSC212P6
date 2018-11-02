package edu.smith.cs.csc212.p6;

import java.util.Iterator;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;

public class SinglyLinkedList<T> implements P6List<T>, Iterable<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		T before = start.value;
		start = start.next;
		return before;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		
		if (start.next == null ) {
			return this.removeFront();
		}
		for (Node<T> current= start; current!= null; current = current.next) {
			if(current.next.next == null) {
				T deleteMe = current.next.value;
				current.next = null;
				return deleteMe;
			}
		}
		return null;
	}
		
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		if (start.next == null ) {
			return this.removeFront();
		}
		
		int at = 0;
		for (Node<T> current = start; current != null; current = current.next) {
			
			if (at == (index-1)) {
				T deleteMe = current.next.value;
				current.next = current.next.next;
				return deleteMe;
			}
			at++;
		}
		return null;
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		Node<T> lastInList = null;
		for (Node<T> current = this.start; current != null; current = current.next) {
			lastInList = current;
		}
		if (lastInList != null) {	
			lastInList.next = new Node<T>(item, null);
		}else {
			this.start = new Node<T>(item,start);
		}
	}

	@Override
	public void addIndex(T item, int index) {
		int at = 0;
		Node<T> last = null;
		for (Node<T> current = start; current != null; current = current.next) {
			if (index == 0) {
				throw new BadIndexError();
			}
			if (at == index) {
				last.next = new Node<T>(item, current);
			}
			last = current;
			at++;
		}
	}

	@Override
	public T getFront() {
		return start.value;
	}

	@Override
	public T getBack() {
		//Node<T> last = null;
		for (Node<T> current = this.start; current != null; current = current.next) {
			if (current.next == null) {
				T last = current.value;
				return last;
			}
		} 
		return null;
	}

	@Override
	public T getIndex(int index) {
		int at = 0;
		for (Node<T> current = start; current != null; current = current.next) {
			if (at == index) {
				return current.value;
			}
			at++;
		}
		throw new BadIndexError();
	}
	
	@Override 
	public int size() { 
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		if (start != null) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method to throw the right error for an empty state.
	 */
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * I'm providing this class so that SinglyLinkedList can be used in a for loop
	 * for {@linkplain ChunkyLinkedList}. This Iterator type is what java uses for
	 * {@code for (T x : list) { }} lops.
	 * 
	 * @author jfoley
	 *
	 * @param <T>
	 */
	private static class Iter<T> implements Iterator<T> {
		/**
		 * This is the value that walks through the list.
		 */
		Node<T> current;

		/**
		 * This constructor details where to start, given a list.
		 * @param list - the SinglyLinkedList to iterate or loop over.
		 */
		public Iter(SinglyLinkedList<T> list) {
			this.current = list.start;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T found = current.value;
			current = current.next;
			return found;
		}
	}
	
	/**
	 * Implement iterator() so that {@code SinglyLinkedList} can be used in a for loop.
	 * @return an object that understands "next()" and "hasNext()".
	 */
	public Iterator<T> iterator() {
		return new Iter<>(this);
	}
}

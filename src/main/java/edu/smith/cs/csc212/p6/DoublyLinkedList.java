package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;



public class DoublyLinkedList<T> implements P6List<T> {
	private Node<T> start;
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

	@Override
	public T removeFront() {
		checkNotEmpty();
		T before = start.value;
		start = start.after;
		return before;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		T deleteMe = end.value;
		end= end.before;
		end.after = null;
		return deleteMe;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		
	}

	@Override
	public void addFront(T item) {
		Node <T>justAdded = new Node<T>(item);
		if (this.start == null) {
			this.start = justAdded;
			this.end = justAdded;
		}else {
			this.start.before = justAdded;
			justAdded.after = start;
		}
		this.start = justAdded;
	}

	@Override
	public void addBack(T item) {
		Node <T>justAdded = new Node<T>(item);
		if (this.end == null) {
			this.start = justAdded;
			this.end = justAdded;
		}else {
			this.end.after = justAdded;
		}
		this.end = justAdded;
	}

	@Override
	public void addIndex(T item, int index) {
		int at = 0;
		Node<T> last = null;
		for (Node<T> current = start; current != null; current = current.after) {
			Node<T> justAdded = new Node<T>(item);
			if (at == index) {	
				last.after = justAdded;
				justAdded.before = last;
				current.before = justAdded; 
				justAdded.after = current;
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
		return end.value;
	}
	
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		
		int at = 0;
		for (Node<T> current = start; current != null; current = current.after) {
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
		for (Node<T> current= this.start; current != null; current = current.after) {
			count ++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		if (start == null) {
			return true;
		}
		return false;
	}
	
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}

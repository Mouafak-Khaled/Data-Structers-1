package code;

import java.util.Iterator;

/* 
 * ASSIGNMENT 2
 * AUTHOR:  <Insert Student Name>
 * Class : ArrayDeque
 *
 * You are not allowed to use Java containers!
 * You must implement the Array Deque yourself
 *
 * MODIFY 
 * 
 * */

import given.iDeque;

/*
 * You must have a circular implementation. 
 * 
 * WARNING: Modulo operator (%) might create issues with negative numbers.
 * Use Math.floorMod instead if you are having issues
 */
public class ArrayDeque<E> implements iDeque<E> {

	private E[] A; // Do not change this name!
	private int size;
	private int front;
	private int tail;

	/*
	 * ADD FIELDS IF NEEDED
	 */

	public ArrayDeque() {
		this(1000);
	}

	public ArrayDeque(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException();
		A = createNewArrayWithSize(initialCapacity);
		this.size = 0;
		front = -1;
		tail = -1;

	}

	// This is given to you for your convenience since creating arrays of generics
	// is not straightforward in Java
	@SuppressWarnings({ "unchecked" })
	private E[] createNewArrayWithSize(int size) {
		return (E[]) new Object[size];
	}

	// Modify this such that the dequeue prints from front to back!
	// Hint, after you implement the iterator, use that!
	public String toString() {

		if (size == 0)
			return "";

		StringBuilder sb = new StringBuilder(1000);
		sb.append("[");
		Iterator<E> iter = iterator();
		while (iter.hasNext()) {
			E e = iter.next();
			if (e == null)
				continue;
			sb.append(e);
			if (!iter.hasNext())
				sb.append("]");
			else
				sb.append(", ");
		}
		return sb.toString();
	}

	/*
	 * ADD METHODS IF NEEDED
	 */

	/*
	 * Below are the interface methods to be overriden
	 */

	// a method that return true whenever the array A is full.
	public boolean isFull() {

		return size == A.length;
	}

	// a method to double the size of the array whenever it is full.
	public void resize() {

		E[] newArray = createNewArrayWithSize(2 * A.length);

		for (int i = 0; i < A.length; i++)
			newArray[i] = A[(i + front) % A.length];

		A = newArray;
		front = 0;
		tail = size - 1;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void addFront(E o) {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			
			front = tail = 0;
			A[front] = o;
			
		} else if (isFull()) {
			
			resize();
			front = A.length - 1;
			A[front] = o;
			
		} else if (front == 0) {
			
			front = A.length - 1;
			A[front] = o;
			
		} else {
			
			front--;
			A[front] = o;
		}
		
		size++;
	}

	@Override
	public E removeFront() {
		// TODO Auto-generated method stub

		if (isEmpty()) {
			
			return null;
			
		} else if (size == 1) {
			
			E temp = A[front];
			A[front] = null;
			front = tail = -1;
			size--;
			return temp;
			
		} else {
			
			E temp = A[front];
			A[front] = null;
			front++;
			
			if (front == A.length)
				front = 0;
			
			size--;
			return temp;
		}

	}

	@Override
	public E front() {
		// TODO Auto-generated method stub
		if (isEmpty())
			return null;

		return A[front];
	}

	@Override
	public void addBehind(E o) {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			
			tail = front = 0;
			A[tail] = o;
			
		} else if (isFull()) {
			
			resize();
			tail++;
			A[tail] = o;
			
		} else {
			
			tail++;
			
			if (tail == A.length)
				tail = 0;
			
			A[tail] = o;
		}
		
		size++;
	}

	@Override
	public E removeBehind() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			
			return null;
			
		} else if (size == 1) {
			
			E temp = A[tail];
			A[tail] = null;
			tail = -1;
			front = -1;
			size--;
			return temp;
			
		} else {
			
			E temp = A[tail];
			A[tail] = null;
			tail--;
			
			if(tail == -1)
				tail = A.length - 1;
			
			size--;
			return temp;
		}
	}

	@Override
	public E behind() {
		if (isEmpty())
			return null;
		return A[tail];
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		size = 0;
		front = tail = -1;
		A = createNewArrayWithSize(1000);

	}

	// Must print from front to back
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		// Hint: Fill in the ArrayDequeIterator given below and return a new instance of
		// it

		return new ArrayDequeIterator();

	}

	private final class ArrayDequeIterator implements Iterator<E> {

		private int index;
		private int increment = 0;

		/*
		 * 
		 * ADD A CONSTRUCTOR IF NEEDED Note that you can freely access everything about
		 * the outer class!
		 * 
		 */

		public ArrayDequeIterator() {

			this.index = front;

		}

		@Override
		public boolean hasNext() {
			if (increment == size) {
				
				return false;
			} else {
				
				return index >= front || index <= tail;
			}
		}

		@Override
		public E next() {

			if (isEmpty())
				return null;

			E element = A[index];
			index++;
			index = index % A.length;
			increment++;

			return element;

		}

	}
}

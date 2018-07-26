// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those who
// do.
// -- Bengi Sevil (bsevil18)
package spacecolonies;

import queue.EmptyQueueException;
import queue.QueueInterface;

/**
 * This data structure implements QueueInterface with a circular
 * array implementation. It provides default queue behavior, such
 * as enqueue, dequeue, getFront, and isEmpty.
 * 
 * @author Bengi Sevil
 * @version 06.20.2018
 *
 * @param <T>
 *            Template.
 */
public class ArrayQueue<T> implements QueueInterface<T> {
    private T[] queue;
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * MAX_CAPACITY represents the maximum number of items
     * the array can hold. It cannot be expanded beyond this
     * number.
     */
    public static final int MAX_CAPACITY = 100;
    private int enqueueIndex;
    private int dequeueIndex;
    private int size;


    /**
     * Default constructor. Creates a new ArrayQueue object
     * of type T and initializes all private fields.
     */
    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        T[] tempQueue = (T[])new Object[DEFAULT_CAPACITY + 1];
        queue = tempQueue;
        dequeueIndex = 0;
        enqueueIndex = DEFAULT_CAPACITY;
        size = 0;
    }


    /**
     * Returns the length of the queue.
     * 
     * @return length of queue.
     */
    public int getLength() {
        return queue.length;
    }


    /**
     * Returns the current number of elements in the queue.
     * 
     * @return size of the queue.
     */
    public int getSize() {
        return size;
    }


    /**
     * Checks if the queue is full.
     * 
     * @return {@code true} if queue is full.
     *         {@code false} otherwise.
     */
    private boolean isFull() {
        return (dequeueIndex == ((enqueueIndex + 2) % queue.length));
    }


    /**
     * This is a helper method that increments the index of the
     * circular array.
     * 
     * @param index
     *            The index to be incremented.
     * @return the incremented index.
     */
    private int incrementIndex(int index) {
        return ((index + 1) % queue.length);
    }


    /**
     * Returns the array representation of the queue with the startIndex
     * of 0. The client can access the elements of the queue without
     * interference with the elements of the queue if desired.
     * 
     * @return the array representation of the queue.
     * @throws EmptyQueueException
     *             if the queue is empty.
     */
    @SuppressWarnings("unchecked")
    public Object[] toArray() {
        T[] arr = (T[])new Object[getSize()];
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        else {
            int index = dequeueIndex;
            for (int i = 0; i < getSize(); i++) {
                arr[i] = queue[index];
                index = incrementIndex(index);
            }
            return arr;
        }
    }


    /**
     * Returns the string representation of the queue.
     * Example non-empty queue output:
     * [Jane Doe A:3 M:2 T:1 Wants:Nars, No-Planet Jane Doe A:2 M:5 T:4]
     * Example empty queue output:
     * []
     * 
     * @return string representation of the queue.
     */
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int index = dequeueIndex;
        for (int i = 0; i < getSize(); i++) {
            if (index != dequeueIndex) {
                builder.append(", ");
            }
            builder.append(queue[index].toString());
            index = incrementIndex(index);
        }
        builder.append("]");
        return builder.toString();
    }


    /**
     * Checks if two queues are equal. Two queues are equal if
     * every item in each queue are equal.
     * 
     * @param obj
     *            Object being compared
     * 
     * @return {@code true} if the queues are equal.
     *         {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        ArrayQueue<T> other = (ArrayQueue<T>)obj;
        if (other.getSize() != this.getSize()) {
            return false;
        }
        for (int i = 0; i < getSize(); i++) {
            T myElement = queue[(dequeueIndex + i) % queue.length];
            T otherElement = other.queue[(other.dequeueIndex + i)
                % other.queue.length];
            if (!myElement.equals(otherElement)) {
                return false;
            }
        }
        return true;

    }


    /**
     * Helper method that doubles the capacity of the array when
     * an item needs to be added but it is full. If MAX_CAPACITY is
     * reached, throws an exception.
     * 
     * @throws IllegalStateException
     *             When the queue has reached maximum capacity.
     */
    private void ensureCapacity() {
        // If array is full, double the capacity.
        if (isFull()) {
            if (getSize() <= 50) {
                T[] oldQueue = queue;
                int oldSize = oldQueue.length;
                @SuppressWarnings("unchecked")
                T[] temp = (T[])new Object[oldSize * 2 - 1];
                queue = temp;
                for (int i = 0; i < oldSize - 1; i++) {
                    queue[i] = oldQueue[dequeueIndex];
                    dequeueIndex = (dequeueIndex + 1) % oldSize;
                }
                dequeueIndex = 0;
                enqueueIndex = oldSize - 2;
            }
            else if (getLength() != MAX_CAPACITY + 1) {

                T[] oldQueue = queue;
                int oldSize = queue.length;
                @SuppressWarnings("unchecked")
                T[] temp = (T[])new Object[MAX_CAPACITY + 1];
                queue = temp;
                for (int i = 0; i < oldSize - 1; i++) {
                    queue[i] = oldQueue[dequeueIndex];
                    dequeueIndex = (dequeueIndex + 1) % oldSize;
                }
                dequeueIndex = 0;
                enqueueIndex = oldSize - 2;
            }
            else {
                throw new IllegalStateException();
            }
        }
    }


    /**
     * Clears the queue.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        T[] tempQueue = (T[])new Object[DEFAULT_CAPACITY + 1];
        queue = tempQueue;
        dequeueIndex = 0;
        enqueueIndex = DEFAULT_CAPACITY;
        size = 0;
    }


    /**
     * Removes the front most item from the queue. Returns the
     * removed item; if empty returns null.
     * 
     * @return T
     *         The item removed.
     * @throws EmptyQueueException
     *             if the queue is empty.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        T front = queue[dequeueIndex];
        queue[dequeueIndex] = null;
        dequeueIndex = incrementIndex(dequeueIndex);
        size--;
        return front;
    }


    /**
     * Adds a new item to the queue from the back. Calls ensureCapacity
     * to make sure the queue is not full.
     * 
     * @param newEntry
     *            The new entry to be added.
     */
    @Override
    public void enqueue(T newEntry) {
        ensureCapacity();
        enqueueIndex = incrementIndex(enqueueIndex);
        queue[enqueueIndex] = newEntry;
        size++;
    }


    /**
     * Returns the front item in the queue. If empty,
     * returns null.
     * 
     * @return T
     *         Front element.
     * @throws EmptyQueueException
     *             if the queue is empty.
     */
    @Override
    public T getFront() {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        T front = queue[dequeueIndex];
        return front;
    }


    /**
     * Checks if the queue is empty.
     * 
     * @return {@code true} if the queue is empty.
     *         {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

}

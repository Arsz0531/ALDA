/**
 * Arpad Szell arsz4862
 * Andreas Ährlund-Richter anah4939
 */
/*
 * This file should not be changed in *ANY* way except the package statement
 * that you may change to whatever suits you. Note that you should *NOT* have
 * any package statement in your code when you submit it for marking.
 *
 * The file should not be included when you send in your code. The test
 * program will supply it in the same directory as your code.
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * This is the list interface you should implement. It is a simplified version
 * of <a href="http://docs.oracle.com/javase/8/docs/api/java/util/Queue.html">
 * java.util.Queue</a> where you will find more documentation for most of the
 * methods.
 * <p>
 * Please note that you should implement all the functionality of the queue on
 * your own using a <em>singly</em> linked list. You may, of course, create
 * temporary linked lists in methods, but you are not allowed to use any arrays,
 * ArrayLists, LinkedLists, Vectors, or other collections anywhere in your code.
 * <p>
 * The test code assumes that the class has a single constructor that takes the
 * capacity of the queue as a parameter.
 * <p>
 * Do NOT rename this interface!
 *
 * @author Henrik
 */
public interface ALDAQueue<E> extends Iterable<E> {

    public void add(E element);

    public void addAll(Collection<? extends E> c);

    public E remove();

    public E peek();

    public void clear();

    public int size();

    public boolean isEmpty();

    public boolean isFull();

    /**
     * Set when creating the queue.
     */
    public int totalCapacity();

    public int currentCapacity();

    /**
     * Move all elements equal to e to the end of the queue.
     *
     * @param e
     * @throws NullPointerException if e is null.
     * @return the number of elements moved.
     */
    public int discriminate(E e);

}


class MyQueue<E> implements ALDAQueue<E> {

    private Node head;
    private Node tail;
    private final int totCapacity;


    static private class Node<E> {
        public E data;
        public Node next;

        private Node(E data) {
            this.data = data;
        }
    }//inner node class

    public MyQueue(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Illegal Size");
        this.totCapacity = size;
    }

    @Override
    public String toString() {
        StringBuilder stringy = new StringBuilder();
        stringy.append("[");

        Node currentNode = head;
        for (int i = 0; i < size(); i++) {
            stringy.append(currentNode.data);
            currentNode = currentNode.next;
            if (i != size() - 1) {
                stringy.append(", ");
            }
        }
        stringy.append("]");
        return stringy.toString();

    }

    @Override
    public void add(E element) {
        //we are full
        if (size() == totCapacity)
            throw new java.lang.IllegalStateException();

        if (element == null)
            throw new NullPointerException("Cant add null!");
        Node newNode = new Node(element);
        //helper method
        addExisting(newNode);
    }

    private void addExisting(Node newNode) {
        //we are full
        if (size() == totCapacity)
            throw new java.lang.IllegalStateException();
        if (tail != null)
            tail.next = newNode;
        tail = newNode;
        if (head == null)
            head = newNode;

    }
    @Override
    public void addAll(Collection<? extends E> coll){
        for(E element : coll)
            add(element);
    }

    @Override
    public E remove(){
        if(isEmpty())
            throw new java.util.NoSuchElementException();
        E returnData = (E)head.data;
        if(head == tail)
            tail=null;

        return  returnData;
    }
    @Override
    public E peek(){
        if(isEmpty())
            return null;
        return (E)head.data;
    }
    @Override
    public void clear(){
        head = null;
        tail = null;
    }
    @Override
    public int size(){
        //todo can be done more efficient
        Node currentNode = head;
        int sum = 0;

        while(currentNode != null){
            sum++;
            currentNode=currentNode.next;
        }
        return sum;
    }
    @Override
    public boolean isEmpty(){
        return head == null;
    }
    @Override
    public boolean isFull(){
        return currentCapacity() == 0;
    }
    @Override
    public int totalCapacity(){
        return totCapacity;
    }
    @Override
    public int currentCapacity(){
        return totCapacity - size();
    }
    @Override
    public int discriminate(E element){
        if(element == null)
            throw new NullPointerException("Null not allowed");
        Node currentNode = head;
        Node nodeBehinde = currentNode;
        int movedNodes = 0;
        int size = size();

        for(int i = 0; i < size; i++){
            if(currentNode != null && currentNode.data.equals(element)){
                if(currentNode == tail){
                    movedNodes++;
                }
                else{
                    if(currentNode == head){
                        head = currentNode.next;
                        tail.next = currentNode;
                        tail = currentNode;
                        currentNode = currentNode.next;
                        tail.next = null;
                        movedNodes++;
                    }else{
                        nodeBehinde.next = currentNode.next;
                        tail.next = currentNode;
                        tail = currentNode;
                        currentNode = currentNode.next;
                        tail.next = null;
                        movedNodes++;
                    }
                }
            }else if(currentNode != tail){
                nodeBehinde = currentNode;
                currentNode = currentNode.next;
            }
        }
        return movedNodes;
    }

    class MyALDAQueueIterator implements Iterator<E> {
        Node<E> head, tail;
        Node current;

        public MyALDAQueueIterator(){
            this.head = MyQueue.this.head;
            this.tail = MyQueue.this.tail;
            this.current = this.head;
        }
        @Override
        public boolean hasNext(){
            return (current == null) ? false : true;
        }

        @Override
        public E next(){
            if(hasNext() == false){
                throw new NoSuchElementException("end of list");
            }
            E data = (E)current.data;
            current = current.next;
            return data;
        }
        @Override
        public void remove(){
            //was not a requirement to implement hence why it is empty
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action){
            //was not a requirment to implement hence why it is empty
        }
        }
        @Override
    public Iterator<E> iterator(){
        return new MyALDAQueueIterator();
        }
    }

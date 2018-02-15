package utils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.function.Consumer;

//Sorts items as they are added to the container.
//New items with the same value are added before previous entries of the same value.
public class SortedLinkedList<E> {
    protected LinkedList<E> list = null;
    private Comparator<E> comparator = null;

    public SortedLinkedList(LinkedList<E> container, Comparator<E> comparator){
        this.list = container;
        this.comparator = comparator;
    }

    public SortedLinkedList(Comparator<E> comparator){
        this(new LinkedList<>(), comparator);
    }

    public final ListIterator<E> iterator(){
        return list.listIterator();
    }

    public final void insert(E entry){
        ListIterator<E> _current = list.listIterator();
        E tmp = null;
        boolean found = false;
        while(_current.hasNext()){
            tmp = _current.next();
            if(comparator.compare(tmp, entry) <= 0) {
                _current.previous();
                _current.add(entry);
                found = true;
                break;
            }
        }

        if(!found){
            list.add(entry);
        }
    }

    public void foreach(Consumer<E> sampleConsumer){
        list.forEach(sampleConsumer);
    }

    public boolean contains(final E other){
        return list.contains(other);
    }

    public int size(){
        return list.size();
    }

    public E getAt(int index){
        return list.get(index);
    }
}

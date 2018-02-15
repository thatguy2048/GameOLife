package Genetic;

import utils.SortedLinkedList;

import java.util.Comparator;
import java.util.Iterator;

public class SortedSampleContainer extends SortedLinkedList<Sample> {
    public SortedSampleContainer() {
        super(new Comparator<Sample>() {
            @Override
            public int compare(Sample o1, Sample o2) {
                return o1.compareScore(o2);
            }
        });
    }

    @Override
    public boolean contains(Sample other) {
        Iterator<Sample> itt = list.iterator();
        while(itt.hasNext()){
            if(itt.next().compareValue(other)){
                return true;
            }
        }
        return false;
    }
};

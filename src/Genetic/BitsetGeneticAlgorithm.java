package Genetic;

import java.util.*;
import java.util.function.Consumer;




class SortedSampleContainer{
    protected LinkedList<Sample> samples;

    public SortedSampleContainer(LinkedList<Sample> container){
        samples = container;
    }

    public SortedSampleContainer(){
        this(new LinkedList<>());
    }

    boolean contains(Sample s){
        return samples.contains(s);
    }

    int size(){
        return samples.size();
    }

    Sample get(int index){
        return samples.get(index);
    }

    Sample find(BitSet bs){
        Sample output = null;
        Iterator<Sample> si = samples.iterator();
        while(si.hasNext()){
            output = si.next();
            if(output.value == bs){
                return output;
            }
        }
        return null;
    }

    void insert(Sample s){
        Sample tmp = null;
        boolean found = false;
        ListIterator<Sample> si = samples.listIterator();
        while(si.hasNext()){
            tmp = si.next();
            if(tmp.score < s.score){
                si.previous();
                si.add(s);
                found = true;
                break;
            }
        }
        if(!found) {
            samples.add(s);
        }
    }

    void foreach(Consumer<Sample> sampleConsumer){
        samples.forEach(sampleConsumer);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Samples: ");
        sb.append(samples.size());
        sb.append('\n');
        for (int i = 0; i < samples.size(); i++) {
            sb.append(samples.get(i));
            sb.append('\n');
        }
        return sb.toString();
    }
};

public class BitsetGeneticAlgorithm{
    protected Random rando = new Random();
    protected SortedSampleContainer ssc;
    protected SampleConsumer sampleEvaluator;

    int dnaSize;
    int samplesPerGeneration;
    int splitLength;

    public BitsetGeneticAlgorithm(int dnaSize, int samplesPerGeneration, SampleConsumer sampleEvaluator){
        this.dnaSize = dnaSize;
        this.samplesPerGeneration = samplesPerGeneration;
        this.sampleEvaluator = sampleEvaluator;
        this.splitLength = dnaSize * 10 / 6;

        ssc = new SortedSampleContainer();
    }

    protected Sample createRandomSample(){
        Sample output = new Sample(new BitSet(dnaSize));
        for(int i = 0; i < dnaSize; ++i){
            if(rando.nextBoolean()) {
                output.value.set(i);
            }
        }
        return output;
    }

    public void createInitialPopulation(){
        for(int i = 0; i < samplesPerGeneration; ++i){
            ssc.insert(sampleEvaluator.consume(createRandomSample()));
        }
    }

    public void generateNewPopulation(){
        List<Sample> sg = new LinkedList<Sample>();
        /*
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(0), ssc.get(1), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(1), ssc.get(2), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(0), ssc.get(2), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(2), ssc.get(3), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(0), ssc.get(3), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(3), ssc.get(4), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(0), ssc.get(4), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(4), ssc.get(5), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(0), ssc.get(5), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(5), ssc.get(6), splitLength)), rando, 0.05f, dnaSize));

        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(1), ssc.get(3), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(2), ssc.get(4), splitLength)), rando, 0.05f, dnaSize));
        sg.add(Sample.Mutate(sampleEvaluator.consume(Sample.Mate(ssc.get(3), ssc.get(5), splitLength)), rando, 0.05f, dnaSize));
        */

        for(int i = 0; i < 10; ++i){
            for(int j = i+1; j < 10; ++j) {
                sg.add(sampleEvaluator.consume(Sample.Mutate(Sample.Mate(ssc.get(i), ssc.get(j), splitLength), rando, 0.05f, dnaSize)));
            }

            sg.add(sampleEvaluator.consume(Sample.Mutate(Sample.Mate(ssc.get(i), createRandomSample(), splitLength), rando, 0.05f, dnaSize)));
        }


        sg.forEach(new Consumer<Sample>() {
            @Override
            public void accept(Sample sample) {
                if(ssc.contains(sample)){
                    System.out.println("Already Have Sample: "+sample);
                }else {
                    ssc.insert(sample);
                }
            }
        });
    }

    public void printCurrentGeneration(){
        System.out.println(ssc);
    }

    public final Sample bestSample(){
        return ssc.get(0);
    }

    public static void main(String[] args) {
        Random rando = new Random();
        BitsetGeneticAlgorithm ga = new BitsetGeneticAlgorithm(10, 25, new SampleConsumer() {
            @Override
            public Sample consume(Sample sample) {
                sample.score = rando.nextInt();
                return sample;
            }
        });
        ga.createInitialPopulation();
        ga.printCurrentGeneration();
        ga.generateNewPopulation();
        ga.printCurrentGeneration();
    }

};
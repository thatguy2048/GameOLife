package Genetic;

import utils.SortedLinkedList;

import java.util.*;
import java.util.function.Consumer;


class SortedBitsetSampleContainer extends SortedLinkedList<BitsetSample>{

    public SortedBitsetSampleContainer(){
        super(new Comparator<BitsetSample>() {
            @Override
            public int compare(BitsetSample o1, BitsetSample o2) {
                return Integer.compare(o1.score, o2.score);
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Samples: ");
        sb.append(size());
        sb.append('\n');
        for (int i = 0; i < size(); i++) {
            sb.append(getAt(i));
            sb.append('\n');
        }
        return sb.toString();
    }
};

public class BitsetGeneticAlgorithm{
    protected Random rando = new Random();
    protected SortedBitsetSampleContainer ssc;
    protected BitsetSampleConsumer sampleEvaluator;

    int dnaSize;
    int samplesPerGeneration;
    float splitRatio;
    int splitLength;

    public BitsetGeneticAlgorithm(int dnaSize, int samplesPerGeneration, BitsetSampleConsumer sampleEvaluator){
        this.dnaSize = dnaSize;
        this.samplesPerGeneration = samplesPerGeneration;
        this.sampleEvaluator = sampleEvaluator;
        this.splitRatio = 0.6f;
        this.splitLength = (int)(dnaSize * splitRatio);

        ssc = new SortedBitsetSampleContainer();
    }

    protected BitsetSample createRandomSample(){
        BitsetSample output = new BitsetSample(new BitSet(dnaSize), dnaSize);
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
        List<BitsetSample> sg = new LinkedList<BitsetSample>();

        for(int i = 0; i < 10; ++i){
            for(int j = i+1; j < 10; ++j) {

                sg.add(sampleEvaluator.consume(ssc.getAt(i).mate(ssc.getAt(j), splitRatio).mutate(rando, 0.05f)));
            }
            sg.add(sampleEvaluator.consume(ssc.getAt(i).mate(createRandomSample(), splitRatio).mutate(rando, 0.05f)));
        }


        sg.forEach(new Consumer<BitsetSample>() {
            @Override
            public void accept(BitsetSample sample) {
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

    public final BitsetSample bestSample(){
        return ssc.getAt(0);
    }

    public static void main(String[] args) {
        Random rando = new Random();
        BitsetGeneticAlgorithm ga = new BitsetGeneticAlgorithm(10, 25, new BitsetSampleConsumer() {
            @Override
            public BitsetSample consume(BitsetSample sample) {
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
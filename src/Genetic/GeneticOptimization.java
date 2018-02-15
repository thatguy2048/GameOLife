package Genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GeneticOptimization {
    protected Random rando;
    protected SortedSampleContainer ssc;
    protected SampleEvaluator sampleEvaluator;
    protected CrossoverMethod crossoverMethod;
    protected int samplesPerGeneration;

    public GeneticOptimization(int samplesPerGeneration, SampleEvaluator sampleEvaluator, CrossoverMethod crossoverMethod){
        this.samplesPerGeneration = samplesPerGeneration;
        this.sampleEvaluator = sampleEvaluator;
        this.crossoverMethod = crossoverMethod;

        this.rando = new Random();
        this.ssc = new SortedSampleContainer();
    }

    protected abstract Sample createNewSample();

    protected Sample createRandomSample(){
        return createNewSample().randomize(rando);
    }

    public void createInitialPopulation(){
        for(int i = 0; i < samplesPerGeneration; ++i){
            ssc.insert(sampleEvaluator.evaluate(createRandomSample()));
        }
    }

    public void generateNewPopulation(){
        int x = (samplesPerGeneration+1)/2;
        List<Sample> sg = new ArrayList<>(samplesPerGeneration);

        for(int i = 0; i < x; ++i){
            for(int j = i+1; j < x; ++j) {
                sg.add(ssc.getAt(i).mate(ssc.getAt(j), crossoverMethod).mutate(rando, 0.05f));
            }
            sg.add(ssc.getAt(i).mate(createRandomSample(), crossoverMethod).mutate(rando, 0.05f));
        }

        for (int i = 0; i < sg.size(); i++) {
            if(ssc.contains(sg.get(i))){
                //System.out.println("Found Duplicate Sample");
            }else{
                ssc.insert(sampleEvaluator.evaluate(sg.get(i)));
            }
        }
    }

    public final Sample bestSample(){
        return ssc.getAt(0);
    }
}

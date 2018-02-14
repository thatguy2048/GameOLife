package Genetic;

import sun.awt.SunHints;

import java.util.Random;

public abstract class Sample<ValueType, ScoreType> {
    protected ValueType value;
    protected ScoreType score;

    public Sample(ValueType value, ScoreType score) {
        this.value = value;
        this.score = score;
    }

    public Sample(ValueType value){
        this(value, null);
    }

    public Sample(){
        this(null, null);
    }

    public ValueType getValue() {
        return value;
    }

    public ScoreType getScore() {
        return score;
    }

    public void setValue(ValueType value) {
        this.value = value;
    }

    public void setScore(ScoreType score) {
        this.score = score;
    }

    public abstract Sample<ValueType, ScoreType> clone();
    public abstract Sample<ValueType, ScoreType> randomize(Random random);
    public abstract Sample<ValueType, ScoreType> mate(Sample<ValueType,ScoreType> other, float dividePercentage);
    public abstract Sample<ValueType, ScoreType> mutate(Random rand, float mutateChance);
}

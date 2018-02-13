package Genetic;

import java.util.function.Consumer;

public abstract class SampleConsumer implements Consumer<Sample> {
    @Override
    public void accept(Sample sample) {
        consume(sample);
    }

    public abstract Sample consume(Sample sample);
}

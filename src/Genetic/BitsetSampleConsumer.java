package Genetic;

import java.util.function.Consumer;

public abstract class BitsetSampleConsumer implements Consumer<BitsetSample> {
    @Override
    public void accept(BitsetSample sample) {
        consume(sample);
    }

    public abstract BitsetSample consume(BitsetSample sample);
}

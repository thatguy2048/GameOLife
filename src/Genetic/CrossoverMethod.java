package Genetic;

public abstract class CrossoverMethod<ValueType> {
    public abstract ValueType crossover(ValueType a, ValueType b);
}

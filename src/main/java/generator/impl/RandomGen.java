package generator.impl;

import generator.IRandomGen;
import generator.exceptions.EmptyArrayInputException;
import generator.exceptions.InfiniteProbabilityException;
import generator.exceptions.LengthMismatchException;
import generator.exceptions.NotANumberProbabilityException;
import generator.exceptions.NotInRangeProbabilityException;
import generator.exceptions.NullArrayInputException;
import generator.exceptions.ZeroProbabilityException;

import java.util.Random;

/**
 * Implementation of random integer generator.
 * A value is generated from seed numbers and corresponding probabilities.
 * Number and probability can be duplicated.
 */
public class RandomGen implements IRandomGen {
    private static final int MIN_INPUT_ARRAY_SIZE = 1;
    private static final float LOW_PROB = 0f;
    private static final float HIGH_PROB = 1f;
    private static final String INFINITE_PROB = "A probability is infinite";
    private static final String INPUT_ARRAY_IS_NULL = "An input array is null";
    private static final String INPUT_ARRAY_IS_EMPTY = "An input array is empty";
    private static final String NEGATIVE_PROBABILITY = "A probability is not in range [0;1]";
    public static final String NAN_PROB = "A probability is Nan";
    public static final String PROBABILITIES_ARE_ZERO = "All probabilities are zero";
    private static final String SIZE_MISMATCH = "Numbers and Probabilities arrays are not same size.";

    private final int[] randomNums;
    private final float[] probabilities;

    /**
     * Create random generator instance
     *
     * @param randomNums    array of random numbers
     * @param probabilities array of probabilities
     * @throws NullArrayInputException        if randomNums or probabilities is null
     * @throws EmptyArrayInputException       if randomNums or probabilities is empty
     * @throws LengthMismatchException        if {@code singletons.length != probabilities.length}
     * @throws InfiniteProbabilityException   if a probability is infinite
     * @throws NotANumberProbabilityException if a probability is not a number
     * @throws NotInRangeProbabilityException if a probability is not in range [0 , 1]
     * @throws ZeroProbabilityException       if all probabilities are equals to 0;
     */
    public RandomGen(int[] randomNums, float[] probabilities) throws NullArrayInputException, EmptyArrayInputException,
            LengthMismatchException, InfiniteProbabilityException, NotANumberProbabilityException,
            NotInRangeProbabilityException, ZeroProbabilityException {

        if (randomNums == null || probabilities == null) {
            throw new NullArrayInputException(INPUT_ARRAY_IS_NULL);
        }

        if (randomNums.length < MIN_INPUT_ARRAY_SIZE || probabilities.length < MIN_INPUT_ARRAY_SIZE) {
            throw new EmptyArrayInputException(INPUT_ARRAY_IS_EMPTY);
        }

        if (randomNums.length != probabilities.length) {
            throw new LengthMismatchException(SIZE_MISMATCH);
        }

        float probSum = 0f;
        for (float probability : probabilities) {


            if (Float.isInfinite(probability)) {
                throw new InfiniteProbabilityException(INFINITE_PROB);
            }

            if (Float.isNaN(probability)) {
                throw new NotANumberProbabilityException(NAN_PROB);
            }

            if (probability < LOW_PROB || probability > HIGH_PROB) {
                throw new NotInRangeProbabilityException(NEGATIVE_PROBABILITY);
            }

            probSum += probability;
        }

        if (probSum == 0f) {
            throw new ZeroProbabilityException(PROBABILITIES_ARE_ZERO);
        }

        this.randomNums = randomNums;
        this.probabilities = normalizeProbabilities(probabilities, probSum);
    }

    private float[] normalizeProbabilities(float[] probabilities, float probSum) {
        float[] normProbs = new float[probabilities.length];
        float normalizationCoef = 1 / probSum;

        for (int i = 0; i < probabilities.length; i++) {
            normProbs[i] = probabilities[i] * normalizationCoef;
        }
        return normProbs;
    }

    /**
     * Generates random probability via Random().nextFloat().
     * Searches for the index in the normalized probabilities
     *
     * @return int value. One of the seeded numbers
     */
    @Override
    public int nextNum() {
        float probsSum = 0f;
        int probIndex = 0;
        float probability = new Random().nextFloat();
        while (probsSum < probability) {
            probsSum += probabilities[probIndex];
            probIndex++;
        }

        int numIndex = probIndex - 1;

        //In case of new Random().nextFloat() is O, probsSum < probability is false, hence numIndex = 0 - 1
        if (numIndex < 0) {
            return randomNums[0];
        }
        return randomNums[numIndex];
    }
}

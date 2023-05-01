import generator.exceptions.EmptyArrayInputException;
import generator.exceptions.InfiniteProbabilityException;
import generator.exceptions.LengthMismatchException;
import generator.exceptions.NotANumberProbabilityException;
import generator.exceptions.NotInRangeProbabilityException;
import generator.exceptions.NullArrayInputException;
import generator.exceptions.ZeroProbabilityException;
import generator.impl.RandomGen;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomGenTest {


    @Test
    void constructorShouldThrowNullArrayInputException_WhenNumsArrayNull() {
        int[] nums = {1, 2, 3};
        float[] probs = null;
        assertThrows(NullArrayInputException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowEmptyArrayInputException_WhenNumsArrayEmpty() {
        int[] nums = {};
        float[] probs = {0.1f, 0.5f, 0.4f};
        assertThrows(EmptyArrayInputException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowINullArrayInputException_WhenProbArrayNull() {
        int[] nums = {1, 2, 3};
        float[] probs = null;
        assertThrows(NullArrayInputException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowEmptyArrayInputException_WhenProbArrayEmpty() {
        int[] nums = {1, 2, 3};
        float[] probs = {};
        assertThrows(EmptyArrayInputException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowLengthMismatchException_WhenArraysSizeMismatch() {
        int[] nums = {1, 2, 3};
        float[] probs = {0.1f, 0.5f,};
        assertThrows(LengthMismatchException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowNotInRangeProbabilityException_WhenProbInf() {
        int[] nums = {1, 2, 3};
        float[] probs = {Float.POSITIVE_INFINITY, 0.5f, 0.1f};
        assertThrows(InfiniteProbabilityException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowNotInRangeProbabilityException_WhenProbNan() {
        int[] nums = {1, 2, 3};
        float[] probs = {0f / 0f, 0.5f, 0.1f};
        assertThrows(NotANumberProbabilityException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowNotInRangeProbabilityException_WhenProbGreaterThatOne() {
        int[] nums = {1, 2, 3};
        float[] probs = {0.1f, 1.5f, 0.1f};
        assertThrows(NotInRangeProbabilityException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowNotInRangeProbabilityException_WhenProbNegative() {
        int[] nums = {1, 2, 3};
        float[] probs = {-0.1f, 0.5f, 0.1f};
        assertThrows(NotInRangeProbabilityException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void constructorShouldThrowZeroProbabilityException_WhenAllProbsAreZero() {
        int[] nums = {1, 2, 3};
        float[] probs = {0f, 0f, 0f};
        assertThrows(ZeroProbabilityException.class, () -> new RandomGen(nums, probs).nextNum());
    }

    @Test
    void nextNumShouldReturnNumFromSeeds_WhenValidInput() {
        int[] nums = {1, 2, 3};
        float[] probs = {0.1f, 0.31f, 0.4f};
        int result = new RandomGen(nums, probs).nextNum();
        assertTrue(IntStream.of(nums).anyMatch(n -> n == result));
    }

}

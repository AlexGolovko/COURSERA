import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NegativeCycleTest {
    private static Stream<Arguments> prepareData() {
        String caseOne = ("4 4\n" +
                "1 2 1\n" +
                "4 1 2\n" +
                "2 3 2\n" +
                "1 3 5\n");
        String caseTwo = "5 9\n" +
                "1 2 4\n" +
                "1 3 2\n" +
                "2 3 2\n" +
                "3 2 1\n" +
                "2 4 2\n" +
                "3 5 4\n" +
                "5 4 1\n" +
                "2 5 3\n" +
                "3 4 4\n";
        String caseThree = "3 3\n" +
                "1 2 7\n" +
                "1 3 5\n" +
                "2 3 2\n";
        String caseFour = "4 4\n" +
                "1 2 -5\n" +
                "4 1 2\n" +
                "2 3 2\n" +
                "3 1 1";

        return Stream.of(Arguments.arguments(caseOne.getBytes(), 0),
                Arguments.arguments(caseTwo.getBytes(), 0), Arguments.arguments(caseThree.getBytes(), 0),
                Arguments.arguments(caseFour.getBytes(), 1));
    }



    @ParameterizedTest
    @MethodSource("prepareData")
    public void NegativeCyclePath(byte[] scanner, int result) {
        System.setIn(new ByteArrayInputStream(scanner));
        assertEquals(result, NegativeCycle.negativeCycle());
    }
}
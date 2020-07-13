import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AcyclicityTest {

    private static Stream<Arguments> prepareCyclies() {

        String firstCase = "4 4\n" +
                "1 2\n" +
                "4 1\n" +
                "2 3\n" +
                "3 1";
        String secondCase = "5 7\n" +
                "1 2\n" +
                "2 3\n" +
                "1 3\n" +
                "3 4\n" +
                "1 4\n" +
                "2 5\n" +
                "3 5";
        String thirdCase = "5 10\n" +
                "5 4\n" +
                "4 5\n" +
                "3 1\n" +
                "1 4\n" +
                "2 3\n" +
                "4 2\n" +
                "3 4\n" +
                "5 2\n" +
                "4 1\n" +
                "3 5";

        return Stream.of(
                Arguments.arguments(prepareScanner(firstCase), 1),
                Arguments.arguments(prepareScanner(secondCase), 0),
                Arguments.arguments(prepareScanner(thirdCase), 1)
        );
    }

    static DirectedScanner prepareScanner(String input) {
        System.setIn(new ByteArrayInputStream((input).getBytes()));
        return new DirectedScanner().scan();
    }

    @Test
    void directedScannerTest() {
        System.setIn(new ByteArrayInputStream(("4 4\n" +
                "1 2\n" +
                "4 1\n" +
                "2 3\n" +
                "3 1").getBytes()));
        DirectedScanner scanner = new DirectedScanner().scan();
        assertEquals(4, scanner.vertices.size());
        assertEquals(1, scanner.vertices.get(2).getVertexes().size());
    }

    @ParameterizedTest
    @MethodSource("prepareCyclies")
    void testCycle(DirectedScanner scanner, int result) {
        assertEquals(result, new Cycle(scanner).hasCycle());
    }
}
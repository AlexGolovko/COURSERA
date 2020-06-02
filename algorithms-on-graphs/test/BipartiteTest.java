import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BipartiteTest {
    private static Stream<Arguments> prepareData() {
        String caseOne = ("4 4\n" +
                "1 2\n" +
                "4 1\n" +
                "2 3\n" +
                "3 1\n");
        String caseTwo = "5 4\n" +
                "5 2\n" +
                "4 2\n" +
                "3 4\n" +
                "1 4";
        return Stream.of(Arguments.arguments(caseOne.getBytes(), 0),
                Arguments.arguments(caseTwo.getBytes(), 1));
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    void testBipartite(byte[] scanner, int result) {
        System.setIn(new ByteArrayInputStream(scanner));
        int isBipartite =Bipartite.bipartite(new Bipartite.GraphScanner().scan());
        assertEquals(result, isBipartite);
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    void testBipartiteOnQueue(byte[] scanner, int result) {
        System.setIn(new ByteArrayInputStream(scanner));
        int isBipartite =Bipartite.bipartiteOnQueue(new Bipartite.GraphScanner().scan());
        assertEquals(result, isBipartite);
    }
}
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;


class BFSTest {


    private static Stream<Arguments> prepareData() {
        String caseOne = ("6 6\n" +
                "1 2\n" +
                "4 1\n" +
                "2 3\n" +
                "3 1\n" +
                "1 5\n" +
                "5 6\n" +
                "2 6");
        String caseTwo = "5 4\n" +
                "5 2\n" +
                "1 3\n" +
                "3 4\n" +
                "1 4\n" +
                "3 5";
        String caseThree = "5 4\n" +
                "5 2\n" +
                "1 3\n" +
                "3 4\n" +
                "1 4\n" +
                "3 5";
        return Stream.of(arguments(caseOne.getBytes(), 3),
                arguments(caseTwo.getBytes(), -1),
                arguments(caseThree.getBytes(), -1)
        );
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    void testBFS(byte[] scanner, int result) {
        System.setIn(new ByteArrayInputStream(scanner));
        int distance = new BFS.BFSPath().solve(new BFS.BFSScanner().scan());
        assertEquals(result, distance);
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    void testBFSClassic(byte[] scanner, int result) {
        System.setIn(new ByteArrayInputStream(scanner));
        int distance = new BFS.BFSPathClassic().solve(new BFS.BFSScanner().scan());
        assertEquals(result, distance);
    }
}
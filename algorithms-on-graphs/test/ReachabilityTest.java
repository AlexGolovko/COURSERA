import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ReachabilityTest {

    @Test
    void StaticScannerTest() {
        InputStream stream = System.in;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(("4 4\n" +
                "1 2\n" +
                "3 2\n" +
                "4 3\n" +
                "1 4\n" +
                "1 4").getBytes());
        System.setIn(inputStream);
        StaticScanner scanner = new StaticScanner();
        assertNotNull(scanner.vertexes);
        assertEquals(scanner.vertexes.size(), 4);
        assertEquals(scanner.x, 0);
        assertEquals(scanner.y, 3);
        System.setIn(stream);
    }

    private static StaticScanner prepareStaticScanner(String input) {
        InputStream stream = System.in;
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            return new StaticScanner();
        } finally {
            System.setIn(stream);
        }
    }

    @ParameterizedTest
    @MethodSource("prepareTestReachPath")
    void testReachPath(StaticScanner scanner, int result) {
        assertEquals(new Reach(scanner).isReachable(), result);
    }

    static Stream<Arguments> prepareTestReachPath() {
        String firstCase = "4 4\n" +
                "1 2\n" +
                "3 2\n" +
                "4 3\n" +
                "1 4\n" +
                "1 4";
        String secondCase = "4 2\n" +
                "1 2\n" +
                "3 2\n" +
                "1 4";
        return Stream.of(
                arguments(prepareStaticScanner(firstCase), 1),
                arguments(prepareStaticScanner(secondCase), 0)
        );
    }

}
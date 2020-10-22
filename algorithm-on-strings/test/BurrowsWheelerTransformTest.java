import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BurrowsWheelerTransformTest {
    private static Stream<Arguments> prepareDate() {

        String[] firstCase = new String[]{"AA$", "AA$"};
        String[] secondCase = new String[]{"ACACACAC$", "CCCC$AAAA"};
        String[] thirdCase = new String[]{"AGACATA$", "ATG$CAAA"};

        return Stream.of(
                Arguments.arguments(firstCase[0].getBytes(), firstCase[1]),
                Arguments.arguments(secondCase[0].getBytes(), secondCase[1]),
                Arguments.arguments(thirdCase[0].getBytes(), thirdCase[1])
        );
    }

    @ParameterizedTest
    @MethodSource("prepareDate")
    void test(byte[] scanner, String result) throws IOException {
        System.setIn(new ByteArrayInputStream(scanner));
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream out = System.out;
        System.setOut(new PrintStream(outputStream));
        final Instant start = Instant.now();
        new BurrowsWheelerTransform().run();
        final Instant end = Instant.now();
        assertEquals(result, outputStream.toString().replace("\r\n", ""));
        System.setOut(out);
        System.out.println(Duration.between(start, end));
    }
}
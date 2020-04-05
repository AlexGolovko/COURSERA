import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectedComponentsTest {

    private static Stream<Arguments> prepareData() {

        String firstCase = "4 2\n" +
                "1 2\n" +
                "3 2";
        String secondCase = "4 4\n" +
                "1 2\n" +
                "3 2\n" +
                "4 3\n" +
                "1 4";
        return Stream.of(
                Arguments.arguments(prepareScanner(firstCase), 2),
                Arguments.arguments(prepareScanner(secondCase), 1)
        );
    }

    private static CustomScanner prepareScanner(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        return new CustomScanner();
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    void testComponentCounter(CustomScanner customScanner, int counter) {
        assertEquals(counter, new ComponentCounter(customScanner).count());
    }

    @Test
    void testCustomScanner() {
        System.setIn(new ByteArrayInputStream(("4 2\n" +
                "1 2\n" +
                "3 2").getBytes()));
        CustomScanner customScanner = new CustomScanner();
        assertEquals(customScanner.vertexes.size(),4);
        assertEquals(2,customScanner.vertexes.get(1).edges.size() );
    }


}
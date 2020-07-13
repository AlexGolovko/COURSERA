import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToposortTest {
    private static Stream<Arguments> prepareData() {
        String firstCase = "4 3\n" +
                "1 2\n" +
                "4 1\n" +
                "3 1";
        String secondCase = "4 1\n" +
                "3 1";
        String thirdCase = "5 7\n" +
                "2 1\n" +
                "3 2\n" +
                "3 1\n" +
                "4 3\n" +
                "4 1\n" +
                "5 2\n" +
                "5 3";
        return Stream.of(
                Arguments.arguments(prepareTopoScanner(firstCase), Arrays.asList(4, 3, 1, 2)),
                Arguments.arguments(prepareTopoScanner(secondCase), Arrays.asList(4, 2, 3, 1)),
                Arguments.arguments(prepareTopoScanner(thirdCase), Arrays.asList(5, 4, 3, 2, 1))
        );
    }

    private static List<TopoVertex> prepareTopoScanner(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        return new TopoScanner().scan();
    }

    @Test
    void TopoScannerTest() {
        System.setIn(new ByteArrayInputStream(("4 3\n" +
                "1 2\n" +
                "4 1\n" +
                "3 1").getBytes()));
        List<TopoVertex> vertices = new TopoScanner().scan();
        assertEquals(4, vertices.size());
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    void toposortTest(List<TopoVertex> vertices, List<Integer> result) {
        List<Integer> inOrder = new TopologicalSort(vertices).handle();
        List<Integer> zeroResultCollection = result.stream().map(it -> it - 1).collect(Collectors.toList());
        Collections.reverse(zeroResultCollection);
        assertEquals(zeroResultCollection, inOrder);
    }
}
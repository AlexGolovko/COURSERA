import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DijkstraTest {

    private static Stream<Arguments> prepareData() {
        String caseOne = ("4 4\n" +
                "1 2 1\n" +
                "4 1 2\n" +
                "2 3 2\n" +
                "1 3 5\n" +
                "1 3");
        String caseTwo = "5 9\n" +
                "1 2 4\n" +
                "1 3 2\n" +
                "2 3 2\n" +
                "3 2 1\n" +
                "2 4 2\n" +
                "3 5 4\n" +
                "5 4 1\n" +
                "2 5 3\n" +
                "3 4 4\n" +
                "1 5";
        String caseThree = "3 3\n" +
                "1 2 7\n" +
                "1 3 5\n" +
                "2 3 2\n" +
                "3 2";
        String caseFour = parseTest("[-1, 8, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, 3, 8, -1, -1, 6, -1]" +
                "[8, -1, -1, -1, -1, -1, 8, -1]" +
                "[-1, -1, -1, -1, -1, 8, -1, 6]" +
                "[-1, -1, 7, -1, -1, -1, 7, -1]" +
                "[-1, -1, -1, 3, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, 5, -1, 4]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1]");
        caseFour += "1 6";
        String caseFive = parseTest("[-1, 8, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, 3, 8, -1, -1, 6, -1]" +
                "[8, -1, -1, -1, -1, -1, 8, -1]" +
                "[-1, -1, -1, -1, -1, 8, -1, 6]" +
                "[-1, -1, 7, -1, -1, -1, 7, -1]" +
                "[-1, -1, -1, 3, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, 5, -1, 4]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1]");
        caseFive += "1 8";
        String caseSix = parseTest("[-1, 8, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, 3, 8, -1, -1, 6, -1]" +
                "[8, -1, -1, -1, -1, -1, 8, -1]" +
                "[-1, -1, -1, -1, -1, 8, -1, 6]" +
                "[-1, -1, 7, -1, -1, -1, 7, -1]" +
                "[-1, -1, -1, 3, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, 5, -1, 4]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1]");
        caseSix += "1 5";
        String caseSeven= parseTest("[-1, 7, 4, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]" +
                "[9, -1, 1, -1, 9, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, 7, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, 1]" +
                "[4, 1, -1, -1, -1, -1, -1, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1]" +
                "[-1, 3, 1, -1, 3, -1, -1, -1, 2, 3, -1, -1, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 5, -1, -1, -1, -1, -1, -1, -1]" +
                "[7, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 3, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, 2, -1, 1, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, 4, 9, -1, 3, -1, 5, -1, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, -1, 3, -1, -1, -1, -1, -1, 9, -1, -1, -1, 4, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, 7, -1, -1, -1, 2, -1, -1, 7, -1, -1]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, 2, -1, -1, -1, -1, 6, -1, -1, 9, -1]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 6, -1, -1, -1, -1, -1, -1, 2]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9, -1, -1, 1]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 6]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, -1, -1, 2, -1, -1, -1, -1]" +
                "[-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, -1, -1, 2, -1, -1, -1, -1]");
        caseSeven+= "4 16";
        return Stream.of(arguments(caseOne.getBytes(), 3),
                arguments(caseTwo.getBytes(), 6), arguments(caseThree.getBytes(), -1), arguments(caseFour.getBytes(), 19), arguments(caseFive.getBytes(), 18), arguments(caseSix.getBytes(), -1),//
                arguments(caseSeven.getBytes(), 23));
    }

    public static String parseTest(String matrix) {
        String result = "";
        String[] split = matrix.split("]");
        int edge = 0;
        for (int i = 0; i < split.length; i++) {
            String row = split[i];
            row = row.substring(1);
            String[] strings = row.split(",");
            for (int j = 0; j < strings.length; j++) {
                String s = strings[j];
                if (Integer.parseInt(s.trim()) > 0) {
                    edge++;
                    result += (i + 1) + " " + (j + 1) + " " + s + "\n";
                }
            }
        }
        result = split.length + " " + edge + "\n" + result;

        return result;
    }

    @ParameterizedTest
    @MethodSource("prepareData")
    public void DjijkstraPath(byte[] scanner, int result) {
        System.setIn(new ByteArrayInputStream(scanner));
        assertEquals(result, Dijkstra.internal());
    }
}
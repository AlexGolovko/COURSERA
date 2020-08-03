import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ConnectingPointsTest {

    private static Stream<Arguments> prepareData() {
        String caseOne = ("4\n" +
                "0 0\n" +
                "0 1\n" +
                "1 0\n" +
                "1 1");
        String caseTwo = "5\n" +
                "0 0\n" +
                "0 2\n" +
                "1 1\n" +
                "3 0\n" +
                "3 2";


        return Stream.of(Arguments.arguments(caseOne.getBytes(), 3.0),
                Arguments.arguments(caseTwo.getBytes(), 7.06449510224598));
    }


    @ParameterizedTest
    @MethodSource("prepareData")
    public void ConnectingPointsTest(byte[] scanner, double result) {
        System.setIn(new ByteArrayInputStream(scanner));
        assertEquals(result, ConnectingPoints.minDist());
    }

}
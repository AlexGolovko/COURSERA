import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    String BWT(String text) {
        StringBuilder result = new StringBuilder();
        final int length = text.length();
        final char[] chars = text.toCharArray();
        final ArrayList<char[]> listChars = new ArrayList<>(length);
        listChars.add(chars);
        for (int i = 1; i < length; i++) {
            final char[] temp = new char[length];
            for (int j = 0; j < length; j++) {
                int circleIdx = i + j > length - 1 ? (i + j) - length : i + j;
                temp[j] = chars[circleIdx];
            }
            listChars.add(temp);
        }
        listChars.sort((v1, v2) -> {
            for (int i = 0; i < v1.length; i++) {
                if (v1[i] - v2[i] != 0) {
                    return v1[i] - v2[i];
                }
            }
            return 0;
        });
        for (int i = 0; i < length; i++) {
            result.append(listChars.get(i)[length - 1]);
        }
        return result.toString();
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();

    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}

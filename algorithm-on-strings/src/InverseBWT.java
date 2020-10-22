import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class InverseBWT implements Runnable {
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

    String inverseBWT(String bwt) {
        final int length = bwt.length();
        StringBuilder result = new StringBuilder(length);
        final char[] firstColumn = bwt.toCharArray();
        Arrays.sort(firstColumn);
        final HashMap<Character, Integer> firstColumnIdxMap = new HashMap<>();
        final HashMap<Character, Integer> lastColumnIdxMap = new HashMap<>();
        final ArrayList<int[]> firstLastProperty = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            final int[] temp = new int[4];
            temp[0] = firstColumn[i];
            if (firstColumnIdxMap.containsKey(firstColumn[i])) {
                final Integer currIdx = firstColumnIdxMap.get(firstColumn[i]) + 1;
                temp[1] = currIdx;
                firstColumnIdxMap.put(firstColumn[i], currIdx);
            } else {
                temp[1] = 1;
                firstColumnIdxMap.put(firstColumn[i], 1);
            }
            temp[2] = bwt.charAt(i);
            if (lastColumnIdxMap.containsKey(bwt.charAt(i))) {
                final Integer currIdx = lastColumnIdxMap.get(bwt.charAt(i)) + 1;
                temp[3] = currIdx;
                lastColumnIdxMap.put(bwt.charAt(i), currIdx);
            } else {
                temp[3] = 1;
                lastColumnIdxMap.put(bwt.charAt(i), 1);
            }
            firstLastProperty.add(temp);
        }
        int currLine = 0;
        for (int i = 0; i < length; i++) {
            result.append((char) firstLastProperty.get(currLine)[0]);
            currLine = findNextLine(currLine, firstLastProperty);
        }
        return result.reverse().toString();
    }

    private int findNextLine(final int currLine, final ArrayList<int[]> props) {
        final char letter = (char) props.get(currLine)[2];
        final int idx = props.get(currLine)[3];
        int low = 0;
        int high = props.size();
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (props.get(mid)[0] == letter && props.get(mid)[1] == idx) {
                return mid;
            }
            if (props.get(mid)[0] < letter || (props.get(mid)[0] == letter && props.get(mid)[1] < idx))
                low = mid + 1;
            else
                high = mid - 1;
        }
        throw new RuntimeException();
    }


    static public void main(String[] args){
        new Thread(new InverseBWT()).start();
    }

    public void run() {
        FastScanner scanner = new FastScanner();
        try {
            String bwt = scanner.next();
            System.out.println(inverseBWT(bwt));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

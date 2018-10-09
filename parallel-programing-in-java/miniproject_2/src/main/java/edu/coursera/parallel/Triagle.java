package edu.coursera.parallel;

Consider the Pascal’s triangle implementation below, with a triangle containing R rows, with row i containing i entries. A triangle with R rows would therefore have R(R+1)2 total entries. Assuming a memoized parallelization strategy like the one below (and discussed in Lecture 2.3), how many futures would be created when computing Pascal’s triangle with R rows ?

public class Triagle {
    final Map<Pair<Int, Int>, future<Integer>> cache = new ...;
    future<Integer> choose(final int N, final int K) {
        final Pair<Int, Int> key = Pair.factory(N, K);
        if (!cache.contains(key)) {
            future<Integer> newFuture = null;
            if (N == 0) {
                // Root element case, zero gets
                newFuture = future {
                    return 1;
                }
            } else if (K == 0) {
                // Left edge element case, one get
                newFuture = future { return choose(N - 1, K).get(); }
            } else if (N == K) {
                // Right edge element case, one get
                newFuture = future { return choose(N - 1, K - 1).get(); }
            } else {
                // Inner element case, two gets
                newFuture = future {
                    return choose(N - 1, K - 1).get() +
                            choose(N - 1, K).get();
                }
            }
            cache.put(key, newFuture);
        }
        return cache.get(key);
    }
    // Scan across target row of Pascal’s triangle
    final int targetRowLength = targetRowIndex + 1;
for (int i = 0; i < targetRowLength; i++) {
        result[i] = choose(targetRowIndex, i).get();
    }

}

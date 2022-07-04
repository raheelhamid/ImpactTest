import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class objectClass {

    @Test
    public void testCompactingNumbersWithJavaStream() {
        List<String> result = IntStream.of(1,3,6,7,8,12,13,14,15,21,22,23,24,31)
                .boxed()
                .collect(Collector.of(
                        () -> {
                            List<List<Integer>> list = new ArrayList<>();
                            list.add(new ArrayList<>());
                            return list;
                        },
                        (list, x) -> {
                            List<Integer> inner = list.get(list.size() - 1);
                            if (inner.size() == 0) {
                                inner.add(x);
                            } else {
                                int lastElement = inner.get(inner.size() - 1);
                                if (lastElement == x - 1) {
                                    inner.add(x);
                                } else {
                                    List<Integer> oneMore = new ArrayList<>();
                                    oneMore.add(x);
                                    list.add(oneMore);
                                }
                            }
                        },
                        (left, right) -> {
                            throw new IllegalArgumentException("No parallel!");
                        },

                        list -> {

                            return list.stream()
                                    .map(inner -> {
                                        if (inner.size() > 1) {
                                            return inner.get(0) + "-" + inner.get(inner.size() - 1);
                                        }
                                        return "" + inner.get(0);
                                    }).collect(Collectors.toList());

                        }
                        ));

        System.out.println(result);
    }

}
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        List<List<String>> result = splitList(list, 2);
        System.out.println(result);
    }

    @NotNull
    private static List<List<String>> splitList(List<String> list, int unitSize) {
        if (list == null || unitSize <= 0) {
            throw new RuntimeException("list is not null and unitSize must be greater than zero");
        }
        Queue<String> queue = new LinkedList<>(list);
        List<List<String>> result = new ArrayList<>();
        double groupNum = Math.ceil(list.size() / (double) unitSize);
        for (int i = 0; i < groupNum; i++) {
            List<String> eachList = new ArrayList<>();
            //last group
            if (i == groupNum - 1) {
                eachList.addAll(queue);
            }
            else {
                for (int j = 0; j < unitSize; j++) {
                    eachList.add(queue.remove());
                }
            }
            result.add(eachList);
        }
        return result;
    }


}

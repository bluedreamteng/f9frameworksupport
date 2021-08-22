package com.tengfei.f9framework.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 集合工具类
 * @author ztf
 */
public class CollectionUtil {

    /**
     *
     * @param list list to be split
     * @param unitSize unitSize
     * @param <T> Object
     * @return List with sub lists
     */
    @NotNull
    public static<T> List<List<T>> splitList(List<T> list, int unitSize) {
        if (list == null || unitSize <= 0) {
            throw new RuntimeException("list is not null and unitSize must be greater than zero");
        }
        List<List<T>> result = new ArrayList<>();
        Queue<T> queue = new LinkedList<>(list);
        double groupNum = Math.ceil(list.size() / (double) unitSize);
        for (int i = 0; i < groupNum; i++) {
            List<T> eachList = new ArrayList<>();
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

package org.example.sortmanager;

import java.util.List;
import java.util.Map;

public interface SortManager {
    List<String> ascendingMergeSortInt(Map<
            String, //Файл
            List<Integer> //Элементы файла
            > filesWithElements);

    List<String> ascendingMergeSortString(Map<
            String,
            List<String>
            > filesWithElements);
    List<String> descendingMergeSortInt(Map<
            String,
            List<Integer>
            > filesWithElements);

    List<String> descendingMergeSortString(Map<
            String,
            List<String>
            > filesWithElements);
}

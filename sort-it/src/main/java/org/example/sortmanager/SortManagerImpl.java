package org.example.sortmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Менеджер алгоритмов сортировок
 */
public class SortManagerImpl implements SortManager {

    /**
     * Алгоритм сортировки слиянием отсортированных массивов/файлов
     * Берутся первые элементы файлов, сравниваются, наименьшее число попадает
     * в результирующий массив, а из файла (в мапе, оригинал файла не трогается) это число удаляется
     * операция проходит, пока все файлы не окажутся пустыми
     *
     * @param filesWithElements - key: название файла, value: элементы файла (предварительно отсортированы)
     * @return List<String> элементы, которые запишутся в выходной файл
     */
    @Override
    public List<String> ascendingMergeSortInt(Map<
            String, //Файл
            List<Integer> //Элементы файла
            > filesWithElements) {
        var result = new ArrayList<String>();

        while (!filesWithElements.isEmpty()) {
            String fileWithSmallestNumber = "";
            var smallestNumber = Integer.MAX_VALUE;
            for (String file : filesWithElements.keySet()) {
                int currentNumber = filesWithElements.get(file).get(0); //рассматриваем только первые элементы
                if (currentNumber < smallestNumber) {
                    fileWithSmallestNumber = file;
                    smallestNumber = currentNumber;
                }
            }
            result.add(String.valueOf(smallestNumber));
            filesWithElements.get(fileWithSmallestNumber).remove(0); //удаляем наименьший элемент из файла

            if (filesWithElements.get(fileWithSmallestNumber).isEmpty()) { //если файл после удаления элемента пуст - файл удаляется
                filesWithElements.remove(fileWithSmallestNumber);
            }
        }
        return result;
    }

    @Override
    public List<String> ascendingMergeSortString(Map<
            String, //Файл
            List<String> //Элементы файла
            > filesWithElements) {
        var result = new ArrayList<String>();

        while (!filesWithElements.isEmpty()) {
            String smallestString = "";
            String fileWithSmallestString = "";
            var smallestStringSize = Integer.MAX_VALUE;
            for (String file : filesWithElements.keySet()) {
                var currentString = filesWithElements.get(file).get(0);
                if (currentString.length() < smallestStringSize) {
                    fileWithSmallestString = file;
                    smallestString = currentString;
                    smallestStringSize = currentString.length();
                }
            }
            result.add(smallestString);
            filesWithElements.get(fileWithSmallestString).remove(0);

            if (filesWithElements.get(fileWithSmallestString).isEmpty()) {
                filesWithElements.remove(fileWithSmallestString);
            }
        }
        return result;
    }

    @Override
    public List<String> descendingMergeSortInt(Map<
            String, //Файл
            List<Integer> //Элементы файла
            > filesWithElements) {
        var result = new ArrayList<String>();

        while (!filesWithElements.isEmpty()) {
            String fileWithBiggestNumber = "";
            var biggestNumber = Integer.MIN_VALUE;
            for (String file : filesWithElements.keySet()) {
                int currentNumber = filesWithElements.get(file).get(0);
                if (currentNumber > biggestNumber) {
                    fileWithBiggestNumber = file;
                    biggestNumber = currentNumber;
                }
            }
            result.add(String.valueOf(biggestNumber));
            filesWithElements.get(fileWithBiggestNumber).remove(0);

            if (filesWithElements.get(fileWithBiggestNumber).isEmpty()) {
                filesWithElements.remove(fileWithBiggestNumber);
            }
        }
        return result;
    }

    @Override
    public List<String> descendingMergeSortString(Map<
            String, //Файл
            List<String> //Элементы файла
            > filesWithElements) {
        var result = new ArrayList<String>();

        while (!filesWithElements.isEmpty()) {
            String biggestString = "";
            String fileWithBiggestString = "";
            var biggestStringSize = Integer.MIN_VALUE;
            for (String file : filesWithElements.keySet()) {
                var currentString = filesWithElements.get(file).get(0);
                if (currentString.length() > biggestStringSize) {
                    fileWithBiggestString = file;
                    biggestString = currentString;
                    biggestStringSize = currentString.length();
                }
            }

            result.add(biggestString);
            filesWithElements.get(fileWithBiggestString).remove(0);

            if (filesWithElements.get(fileWithBiggestString).isEmpty()) {
                filesWithElements.remove(fileWithBiggestString);
            }
        }
        return result;
    }
}

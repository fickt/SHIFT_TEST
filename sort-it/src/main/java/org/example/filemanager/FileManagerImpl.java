package org.example.filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Файл менеджер. Выгружает и записывает данные в файл
 */
public class FileManagerImpl implements FileManager {

    private static final String MSG_FILE_NOT_FOUND = "Файл не найден! Путь: %s!%n";
    private static final String MSG_INVALID_ELEMENT_IN_FILE = "Невалидный элемент: %s в файле: %s!%n";
    private static final String MSG_IO_EXCEPTION = "Ошибка! InputOutputException, writeOutputFile";
    private static final String SORT_TYPE_DESCENDING = "-d";

    /**
     * Достает пути входных файлов из аргументов
     * @param argsList - аргументы
     * @return List<String> - пути к файлам
     */
    @Override
    public List<String> extractFileDirectoriesFromArgs(List<String> argsList) {
        var fileDirectories = new ArrayList<String>();
        argsList.stream()
                .map(o -> o.replace("\\", "/")) //java не очень любит пути по типу C:\Users\folder и т.д
                .forEach(obj -> {
                    var file = new File(obj);
                    if (file.isFile()) {
                        fileDirectories.add(obj);
                    } else {
                        System.out.printf(MSG_FILE_NOT_FOUND, obj);
                    }
                });
        return fileDirectories;
    }

    /**
     * Достаёт все Integer данные из файла
     * @param file - файл из которого достаются данные
     * @param sortType - тип сортировки, на основании его будет валидироваться отсортированность файла,
     *                все невалидные данные не войдут в результат
     * @return List<Integer> интовые данные
     */
    @Override
    public List<Integer> getIntElementsFromFile(String file, String sortType) {
        var elements = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                elements.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.printf(MSG_FILE_NOT_FOUND, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return validateInputIntElements(elements, file, sortType);
    }


    @Override
    public List<String> getStringElementsFromFile(String file, String sortType) {
        var elements = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                elements.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.printf(MSG_FILE_NOT_FOUND, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return validateInputStringElements(elements, file, sortType);
    }

    /**
     * @param outputFile - выходной файл
     * @param elements - результат сортировки, который записывается в выходной файл
     */
    @Override
    public void writeOutputFile(String outputFile, List<String> elements) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String element : elements) {
                writer.append(element).append("\n");
            }
        } catch (IOException e) {
            System.out.println(MSG_IO_EXCEPTION);
        }
    }

    /**
     * Метод валидирует входные данные из файла, проверяется, являются ли элементы Integer
     * Также проверяется, отсортированы ли входные данные
     *
     * @param elements - элементы из файла
     * @param fileName - название файла
     * @return - List<Integer> возвращает валидированные данные, всё, что не прошло валидацию, не входит в результирующий массив
     */
    private List<Integer> validateInputIntElements(List<String> elements, String fileName, String sortType) {
        if (elements.isEmpty()) {
            return Collections.emptyList();
        }

        var resultElement = new ArrayList<Integer>();
        Integer previousElement = null;
        Integer currentElement;


        for (String element : elements) {
            try {
                currentElement = Integer.parseInt(element);
            } catch (NumberFormatException e) {
                System.out.printf(MSG_INVALID_ELEMENT_IN_FILE, element, fileName);
                continue;
            }
            if (previousElement == null) {
                resultElement.add(currentElement);
                previousElement = currentElement;
                continue;
            } else {
                if (sortType.equals(SORT_TYPE_DESCENDING)) { // при типе сортировки "-d"
                    if (previousElement < currentElement) { //если предыдущий элемент < текущего, тогда он лишний и значит удаляется
                        System.out.printf(MSG_INVALID_ELEMENT_IN_FILE, currentElement, fileName);
                        continue;
                    }
                } else { // при типе сортировки "-a"
                    if (previousElement > currentElement) { //если предыдущий элемент > текущего, тогда он лишний и значит удаляется
                        System.out.printf(MSG_INVALID_ELEMENT_IN_FILE, currentElement, fileName);
                        continue;
                    }
                }
            }
            resultElement.add(currentElement);
            previousElement = currentElement;
        }
        return resultElement;
    }

    private List<String> validateInputStringElements(List<String> elements, String fileName, String sortType) {
        if (elements.isEmpty()) {
            return Collections.emptyList();
        }

        var resultElement = new ArrayList<String>();
        String previousElement = null;
        String currentElement;


        for (String element : elements) {
            if (element.contains(" ")) {
                System.out.printf(MSG_INVALID_ELEMENT_IN_FILE, element, fileName);
                continue;
            }

            currentElement = element;
            if (previousElement == null) {
                resultElement.add(currentElement);
                previousElement = currentElement;
                continue;
            } else {
                if (sortType.equals(SORT_TYPE_DESCENDING)) { // при типе сортировки "-d"
                    if (previousElement.length() < currentElement.length()) { //если предыдущий элемент < текущего, тогда он лишний и значит удаляется
                        System.out.printf(MSG_INVALID_ELEMENT_IN_FILE, currentElement, fileName);
                        continue;
                    }
                } else { // при типе сортировки "-a"
                    if (previousElement.length() > currentElement.length()) { //если предыдущий элемент > текущего, тогда он лишний и значит удаляется
                        System.out.printf(MSG_INVALID_ELEMENT_IN_FILE, currentElement, fileName);
                        continue;
                    }
                }
            }
            resultElement.add(currentElement);
            previousElement = currentElement;
        }
        return resultElement;
    }
}

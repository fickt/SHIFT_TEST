package org.example.service;

import org.example.filemanager.FileManager;
import org.example.sortmanager.SortManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SortItServiceImpl implements SortItService {
    private static final String SORT_TYPE_ASCENDING = "-a";
    private static final String SORT_TYPE_DESCENDING = "-d";
    private static final String DATA_TYPE_STRING = "-s";
    private static final String DATA_TYPE_INT = "-i";
    private static final String MSG_INVALID_ARGUMENTS = "Введены некорректные аргументы!";
    private static final String MSG_OUTPUT_FILE_NOT_FOUND = "Выходной файл не найден! Путь: %s";
    private static final String MSG_EMPTY_ARGUMENTS = "Список аргументов пуст!";
    private static final String MSG_OPERATION_SUCCESS = "Операция завершена успешно!";
    private static final String MSG_NO_FILES_PROVIDED = "В аргументах не указаны выходной и входные файлы!";
    private static final String MSG_NO_DATA_TYPE_PROVIDED = "В аргументах не указан тип входных данных";
    private static final String MSG_NO_INPUT_FILES_PROVIDED = "Не указаны входящие файлы!";

    private String sortType = SORT_TYPE_ASCENDING;
    private String dataType;
    private File outputFile;
    private List<String> argsList;

    private final SortManager sortManager;
    private final FileManager fileManager;


    public SortItServiceImpl(SortManager sortManager, FileManager fileManager) {
        this.sortManager = sortManager;
        this.fileManager = fileManager;
    }

    @Override
    public void start(String[] args) {

        if (!isArgsValid(args)) {
            return;
        }

        var fileDirectories = fileManager.extractFileDirectoriesFromArgs(argsList);
        List<String> result;

        if (dataType.equals(DATA_TYPE_INT)) {
            result = sortInt(fileDirectories);
        } else {
            result = sortString(fileDirectories);
        }

        fileManager.writeOutputFile(outputFile.getPath(), result);
        System.out.println(MSG_OPERATION_SUCCESS);
    }

    /**
     * Валидирует и устанавливает тип сортировки, тип входных/выходных данных, выходной файл
     * @param args - аргументы запуска
     * @return Boolean true - аргументы вылидны, false - невалидны
     */
    private Boolean isArgsValid(String[] args) {

        if (args.length == 0) {
            System.out.println(MSG_EMPTY_ARGUMENTS);
            return Boolean.FALSE;
        }

        argsList = new ArrayList<>(Arrays.asList(args));

        if (argsList.get(0).equals(SORT_TYPE_DESCENDING) || argsList.get(0).equals(SORT_TYPE_ASCENDING)) { //если есть режим сортировки в аргументах
            sortType = argsList.get(0);
            argsList.remove(0); //тип сортировки достали, больше он тут не нужен

            if (argsList.isEmpty()) { //если после режима сортировки нет типа данных
                System.out.println(MSG_NO_DATA_TYPE_PROVIDED);
                return Boolean.FALSE;
            }

            if (argsList.get(0).equals(DATA_TYPE_INT) || argsList.get(0).equals(DATA_TYPE_STRING)) {
                dataType = argsList.get(0);
                argsList.remove(0);
            } else {
                System.out.println(MSG_INVALID_ARGUMENTS);
                return Boolean.FALSE;
            }
            if (argsList.isEmpty()) { //если после типа сортировки и типа данных ничего нет, значит нет файлов, с которыми мы работаем
                System.out.println(MSG_NO_FILES_PROVIDED);
                return Boolean.FALSE;
            }

        } else if (argsList.get(0).equals(DATA_TYPE_INT) || argsList.get(0).equals(DATA_TYPE_STRING)) { //если нет режима сортировки в аргументах
            dataType = argsList.get(0);
            argsList.remove(0);
        } else { // если введены некорректные аргументы
            System.out.println(MSG_INVALID_ARGUMENTS);
            return Boolean.FALSE;
        }

        outputFile = new File(argsList.get(0)); //выходной файл
        argsList.remove(0);

        if(argsList.isEmpty()) { //отсутствуют входящие файлы
            System.out.println(MSG_NO_INPUT_FILES_PROVIDED);
            return Boolean.FALSE;
        }

        if (!outputFile.isFile()) {
            System.out.printf(MSG_OUTPUT_FILE_NOT_FOUND, outputFile);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * Вызывает все необходимые методы для сортировки int данных
     * @param fileDirectories - файлы, данные которых отсортируются
     * @return List<String> - отсортированные данные
     */
    private List<String> sortInt(List<String> fileDirectories) {
        var filesWithElements = new HashMap<
                String, //Файл
                List<Integer> //Элементы файла
                >();
        for (String file : fileDirectories) { //разделяем на файлы и их данные
            var elements = fileManager.getIntElementsFromFile(file, sortType);
            if (!elements.isEmpty()) {
                filesWithElements.put(file, elements);
            }
        }

        if (sortType.equals(SORT_TYPE_ASCENDING)) {
            return sortManager.ascendingMergeSortInt(filesWithElements);
        } else {
            return sortManager.descendingMergeSortInt(filesWithElements);
        }
    }

    /**
     * Вызывает все необходимые методы для сортировки String данных
     * @param fileDirectories - файлы, данные которых отсортируются
     * @return List<String> - отсортированные данные
     */
    private List<String> sortString(List<String> fileDirectories) {
        var filesWithElements = new HashMap<
                String,
                List<String>
                >();
        for (String file : fileDirectories) {
            var elements = fileManager.getStringElementsFromFile(file, sortType);
            if (!elements.isEmpty()) {
                filesWithElements.put(file, elements);
            }
        }

        if (sortType.equals(SORT_TYPE_ASCENDING)) {
            return sortManager.ascendingMergeSortString(filesWithElements);
        } else {
            return sortManager.descendingMergeSortString(filesWithElements);
        }
    }
}

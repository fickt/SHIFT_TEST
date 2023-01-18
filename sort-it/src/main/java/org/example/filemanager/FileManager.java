package org.example.filemanager;

import java.util.List;

public interface FileManager {
    List<String> extractFileDirectoriesFromArgs(List<String> args);
    void writeOutputFile(String file, List<String> elements);
    List<Integer> getIntElementsFromFile(String file, String sortType);
    List<String> getStringElementsFromFile(String file, String sortType);
}

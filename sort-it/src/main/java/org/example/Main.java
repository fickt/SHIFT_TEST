package org.example;

import org.example.filemanager.FileManagerImpl;
import org.example.service.SortItService;
import org.example.service.SortItServiceImpl;
import org.example.sortmanager.SortManagerImpl;

/**
 * @author Michael Arbuzov
 * @version 1.0
 */
public class Main {
    private static final SortItService service = new SortItServiceImpl(
            new SortManagerImpl(),
            new FileManagerImpl()
    );

    public static void main(String[] args) {
        service.start(args);
    }
}

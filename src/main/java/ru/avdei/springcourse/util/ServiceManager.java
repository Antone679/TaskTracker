package ru.avdei.springcourse.util;

import ru.avdei.springcourse.service.HistoryService;
import ru.avdei.springcourse.service.HistoryServiceImpl;
import ru.avdei.springcourse.service.TaskService;

public class ServiceManager {
    public static TaskService getDefault(){
        return null;
    }
    public static HistoryService getDefaultHistory(){
        return new HistoryServiceImpl();
    }
}

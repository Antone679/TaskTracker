package ru.avdei.springcourse.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.avdei.springcourse.entity.Epic;
import ru.avdei.springcourse.entity.Task;
import ru.avdei.springcourse.entity.TaskType;
import ru.avdei.springcourse.repository.TaskRepository;
import ru.avdei.springcourse.util.ManagerSaveException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SerializableServiceImplTest {
    @InjectMocks
    private SerializableServiceImpl service;
    private Path tempFile;
    private Path csvFile;
    @Mock
    private TaskRepository repository;
    @Mock
    private BufferedReader reader;
    @Mock
    HistoryServiceImpl historyService;


    @BeforeEach
    void init() {
        csvFile = Paths.get("src/main/resources/test-information.csv");
        tempFile = Paths.get("src/main/resources/temp-information.csv");

        service = new SerializableServiceImpl();
        service.setCsvFile(csvFile);
    }

    @Test
    void saveToCSV() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile.toFile()))) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            writer.write("1,EPIC,e,NEW,e");
            writer.newLine();
            writer.newLine();
        }
        String line = "2,TASK,t,NEW,t";

        service.saveToCSV(line);

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toFile()))) {
            String neededLine;
            int counter = 0;
            while ((neededLine = reader.readLine()) != null) {
                counter++;
                if (counter == 3) {
                    assertEquals(line, neededLine);
                }
                if (counter == 4) {
                    assertTrue(neededLine.isEmpty());
                }
            }
            assertEquals(4, counter);
        }
    }


    @Test
    void initializeTasks() throws IOException {
        String line = "1,TASK,t,NEW,t";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile.toFile()))) {
            writer.write("id,type,name,status,description,epic");
        }
        service.saveToCSV(line);
        service.initializeTasks();

        assertEquals(3, service.getTasks().size());

        service.setTaskRepository(repository);
        when(repository.findById(1)).thenReturn(new Task("t", "t", TaskType.TASK));
        assertEquals(new Task("t", "t", TaskType.TASK), service.findById(1));
    }

    @AfterEach
    void tearDown() throws IOException {

        Files.deleteIfExists(csvFile);
        Files.deleteIfExists(tempFile);
    }
}
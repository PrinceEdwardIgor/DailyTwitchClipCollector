package com.DailyTwitchClipCollector.DailyTwitchClipCollector;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class makeDirTest {
    @Test
    void makeDirTest() throws IOException {
        Files.createDirectories(Paths.get("C:\\Users\\ECCOM\\Desktop\\testFolder"));
    }
}

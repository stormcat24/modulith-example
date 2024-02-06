package dev.stormcat.tools.test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public final class FileLoader {

    public static String load(String path) {
        try (InputStream stream = FileLoader.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new FileNotFoundException(path + " not found");
            }

            return new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

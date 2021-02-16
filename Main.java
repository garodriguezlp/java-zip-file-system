import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

class Main {
    public static void main(String[] args) {
        Path zipPath = Paths.get("hw.zip");
        try {
            FileSystem zipFs = FileSystems.newFileSystem(zipPath, null);
            Path zipFsPath = zipFs.getPath("/");
            walkZipFile(zipFsPath);
            addNewChild(zipFsPath);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    static void walkZipFile(Path pah) throws IOException {
        Files.walk(pah).forEach(path -> {
            System.out.println("--- ----------------------------------------------");
            System.out.println("--- " + path);
            System.out.println("--- ----------------------------------------------");
            printFileContent(path);
            System.out.println("\n");

        });
    }

    static void printFileContent(Path path) {
        try {
            if (!Files.isDirectory(path)) {
                System.out.println("File content:");
                Files.readAllLines(path).forEach(line -> {
                    System.out.println(line);
                });
            } else {
                System.out.println("No content to print because file is a directory");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    static void addNewChild(Path zipFsPath) throws IOException {
        Path targetPath = zipFsPath.resolve("hw-v3.txt");
        Writer writer = Files.newBufferedWriter(targetPath, StandardCharsets.UTF_8);
        
    }
}
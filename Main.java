import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

class Main {
    public static void main(String[] args) {
        Path zipPath = Paths.get("hello-world.zip");
        try {
            try (FileSystem zipFs = FileSystems.newFileSystem(zipPath, null)) {
                Path zipFsPath = zipFs.getPath("/");
                walkZipFile(zipFsPath);
                Path sourcePath = Paths.get("hello-world-v3.txt");
                Files.copy(sourcePath, zipFs.getPath("/").resolve("hello-world-v3.txt"), StandardCopyOption.REPLACE_EXISTING);
                addNewChild(zipFsPath);
            }
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
        Path targetPath = zipFsPath.resolve("hello-world-from-the-trenches.txt");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(targetPath, StandardCharsets.UTF_8))) {
            writer.println("Hello from the trenches");
        }
    }
}
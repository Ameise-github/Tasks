import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class TreeCopy extends SimpleFileVisitor<Path> {
    private Path source;
    private Path destination;

    public TreeCopy(Path source, Path destination) {
        this.source = source;
        this.destination = destination;
    }

    /**Копируем файл*/
    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes){
        Path newDir = destination.resolve(source.relativize(path));
        try {
            Files.copy(path,newDir,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }

    /**Копирование каталога*/
    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes){
        Path newDir = destination.resolve(source.relativize(path));
        try {
            Files.copy(path, newDir,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileVisitResult.CONTINUE;
    }
}

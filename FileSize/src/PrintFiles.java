import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.*;

public class PrintFiles extends SimpleFileVisitor<Path> {
    private long sumSize = 0;
    // Распечатать информацию о
    // типе каждого файла.
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
//        if (attr.isSymbolicLink()) {
//            System.out.format("Символическая ссылка: %s ", file);
//        } else if (attr.isRegularFile()) {
//            System.out.format("Файл: %s ", file);
//        } else {
//            System.out.format("Другое: %s ", file);
//        }
//        System.out.println("(" + attr.size() + "  bytes)");
        sumSize += attr.size();
        return CONTINUE;
    }

    // Распечатать каждый каталог, который посетил.
//    @Override
//    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
//        System.out.format("Каталог: %s%n", dir);
//        return CONTINUE;
//    }

    // Если при доступе к файлу произошла ошибка , сообщите пользователю.
    // Если вы не переопределите этот метод
    // и возникнет ошибка, IOException
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    /**
     * Фунция получения суммы размера каталога в байтах*/
    public long getSumSize() {
        return sumSize;
    }
}

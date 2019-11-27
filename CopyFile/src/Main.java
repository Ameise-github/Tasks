import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] Arg) {
        scanner = new Scanner(System.in);
        while (true){
            File files = getFiles("Введите наименование папки которую копируем :  ", "source");
            File filesCopy = getFiles("Введите папку куда копируем: ", "copy");
            if (!files.isDirectory()) {
                System.out.println("Неободимо вести наименование копируемого КАТАЛОГА!!!");
                continue;
            } else {
                try {
                    Files.walkFileTree(files.toPath(),new TreeCopy(files.toPath(),filesCopy.toPath()));
                    System.out.println("Папка успешно скопирована!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**Проверка директории*/
    private static File getFiles(String pathCopy, String lable) {
        while (true) {
            if (lable.equals("source")) {
                System.out.println(pathCopy);
                String path = scanner.nextLine().trim();
                File fls = new File(path);
                if (!fls.exists()) {
                    System.out.println("Файл не найен!");
                    continue;
                } else return fls;
            }
            if (lable.equals("copy")) {
                System.out.println(pathCopy);
                String path = scanner.nextLine().trim();
                File fls = new File(path);
                if (!fls.exists()) {
                    System.out.println("Такого каталога не существует!");
                    String nameNewDir = getNameDir(path);
                    System.out.println("Создать каталог с именем: " + nameNewDir + ". (Y/N)");
                    String answ = scanner.nextLine().trim();
                    if (answ.equals("Y")) {
                        File newDir = new File(path);
                        if(newDir.mkdir()){
                            System.out.println("Каталог " + newDir.getAbsolutePath() + " успешно создан!");
                            return newDir;
                        }else {
                            System.out.println("Каталог " + newDir.getAbsolutePath() + " создать не удалось!");
                        }
                    } else if (answ.equals("N")) {
                        System.out.println("Введите наименование каталога: ");
                        //String newNameDir = scanner.nextLine().trim();
                        File newDir = new File(scanner.nextLine().trim());
                        if(newDir.mkdir()){
                            System.out.println("Каталог " + newDir.getAbsolutePath() + " успешно создан!");
                            return newDir;
                        }else {
                            System.out.println("Каталог " + newDir.getAbsolutePath() + " создать не удалось!");
                        }
                    }
                } else return fls;
            }
        }
    }

    /**Получение имени директории*/
    private static String getNameDir(String path) {
        String[] paths = path.split("/");
        if (paths.length != 0) {
            return paths[paths.length - 1];
        } else {
            return path;
        }
    }
}

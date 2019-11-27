import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] Args) {
        scanner = new Scanner(System.in);
        System.out.print("Введите адрес сайта URL ->   ");
        String url = scanner.nextLine();
        Document document = null;
        ArrayList<String> imgSrc = new ArrayList<>();
        try {
            //Скачали html страницу
            document = Jsoup.connect(url).get();
            //Нашли элементы img и их атирибут ссылку на картинку
            Elements img = document.select("[src]");
            for (Element el : img) {
                if (el.tagName().equals("img")) {
                    imgSrc.add(el.attr("abs:src"));
//                    System.out.format(" * %s: <%s> %sx%s\n",
//                            el.tagName(), el.attr("abs:src"), el.attr("width"), el.attr("height"));
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        File filePath = getFiles("Укажите папку для сохранения картинок ->   ");

        System.out.println("Поалуйста подождите, идет скачивание картнок.");
        //Скачиваем и сохраняем картинки
        imgSrc.forEach((m -> loadImage(m,filePath.getAbsolutePath())));

        System.out.println("Картинки сохранены!");
    }

    /**
     * Проверка директории
     */
    private static File getFiles(String str) {
        while (true) {
            System.out.print(str);
            String path = scanner.nextLine().trim();
            File fls = new File(path);
            if (!fls.exists()) {
                System.out.println("Такого каталога не существует!");
                String nameNewDir = getNameDir(path);
                System.out.println("Создать каталог с именем: " + nameNewDir + ". (Y/N)");
                String answ = scanner.nextLine().trim();
                if (answ.equals("Y")) {
                    File newDir = new File(path);
                    if (newDir.mkdir()) {
                        System.out.println("Каталог " + newDir.getAbsolutePath() + " успешно создан!");
                        return newDir;
                    } else {
                        System.out.println("Каталог " + newDir.getAbsolutePath() + " создать не удалось!");
                    }
                } else if (answ.equals("N")) {
                    System.out.println("Введите наименование каталога: ");
                    String newNameNewDir = path.substring(0, path.lastIndexOf("/") + 1) + scanner.nextLine().trim();
                    File newDir = new File(newNameNewDir);
                    if (newDir.mkdir()) {
                        System.out.println("Каталог " + newDir.getAbsolutePath() + " успешно создан!");
                        return newDir;
                    } else {
                        System.out.println("Каталог " + newDir.getAbsolutePath() + " создать не удалось!");
                    }
                }
            } else return fls;

        }
    }

    /**
     * Получение имени директории
     */
    private static String getNameDir(String path) {
        String[] paths = path.split("/");
        if (paths.length != 0) {
            return paths[paths.length - 1];
        } else {
            return path;
        }
    }

    /**
     * Загрузка картинки и ее сохранение
     */
    private static void loadImage(String URL, String pathDir) {
        //образование имени файла
        String fileName = pathDir + "/" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss'.png'").format(new Date());
        try {
            //скачивание картинки
            BufferedImage img = ImageIO.read(new URL(URL));
            File file = new File(fileName);
            if (!file.exists()) {
                //сохранение картинки
                file.createNewFile();
            }
            ImageIO.write(img, "png", file);
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

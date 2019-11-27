import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    static public void main(String[] Arg){
        scanner = new Scanner(System.in);
        File files = getFiles("Введите путь к папке/файлу:  ");
        if(!files.isDirectory()){
            System.out.println("Размер файла равен: " + files.length() + " Б");
        }else{
            //Находим все файлы
            try {
                PrintFiles pf = new PrintFiles();
                Files.walkFileTree(Paths.get(files.getAbsolutePath()),pf);
                System.out.println("Размер каталога равен: " + getSizeFiles((double)pf.getSumSize()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getSizeFiles(Double sumSize) {
        BigDecimal decimal;
        if(sumSize.toString().length() >= 4){
            Double kB = sumSize / 1024;
            String[] Bs = kB.toString().split("\\.");
            if(Bs.length != 0){
                if(Bs[0].length() >= 4){
                    Double mB = kB / 1024;
                    Bs = mB.toString().split("\\.");
                    if(Bs[0].length() >= 4){
                        Double gB = mB / 1024;
                        decimal = BigDecimal.valueOf(gB).setScale(2, RoundingMode.HALF_EVEN);
                        return decimal.toString() + " ГБ";
                    }
                    decimal = BigDecimal.valueOf(mB).setScale(2, RoundingMode.HALF_EVEN);
                    return decimal.toString() + " МБ";
                }
                decimal = BigDecimal.valueOf(kB).setScale(2, RoundingMode.HALF_EVEN);
                return decimal.toString() + " КБ";
            }
        }
        return sumSize + " Б";
    }

    public static File getFiles(String s) {
        while(true){
            System.out.println(s);
            String path = scanner.nextLine().trim();
            File fls = new File(path);
            if(!fls.exists()) {
                System.out.println("Файл не найен!");
                continue;
            }else return fls;
        }
    }


}

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.makers.FixedSizeThumbnailMaker;
import net.coobird.thumbnailator.makers.ScaledThumbnailMaker;
import net.coobird.thumbnailator.resizers.BicubicResizer;
import net.coobird.thumbnailator.resizers.BilinearResizer;
import net.coobird.thumbnailator.resizers.ProgressiveBilinearResizer;

import javax.imageio.ImageIO;
import javax.swing.plaf.IconUIResource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static net.coobird.thumbnailator.Thumbnailator.createThumbnail;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Введиите путь к папке с картинками ->  ");
        String srcFolder = "source/imageSrc";   //scanner.nextLine().trim();
//        System.out.print("Введите путь к папке для сохранения обработанных картинок ->  ");
        String dstFolder = "source/imageDst1";   //scanner.nextLine().trim();

        File srcDir = new File(srcFolder);
        if (!srcDir.exists()) {
            System.out.println("Папки не существует! " + srcDir.getAbsolutePath());
        } else {

            long start = System.currentTimeMillis();

            File[] files = srcDir.listFiles();

//            int middel = files.length / 2;
//            File[] files1 = new File[middel];
//            System.arraycopy(files,0,files1,0,files1.length);
//            ImageResize resize1 = new ImageResize(files1,400,dstFolder,start);
//            new Thread(resize1).start();
//
//            File[] files2 = new File[files.length - middel];
//            System.arraycopy(files,middel,files2,0,files2.length);
//            ImageResize resize2 = new ImageResize(files2,400,dstFolder,start);
//            new Thread(resize2).start();

            //Количество ядер процессора
            int cores = Runtime.getRuntime().availableProcessors();
            int m = files.length / cores;
            int count = 0;
//            for (int i = 1; i <= cores; i++) {
//                File[] files1;
//                if(i == cores){
//                    files1 = new File[files.length - count];
//                }else {
//                    files1 = new File[m];
//                }
//                System.arraycopy(files, count, files1, 0, files1.length);
//                ImageResize resize = new ImageResize(files1, 400, dstFolder, start);
//                new Thread(resize).start();
//                count += m;
//            }

            /*новый метод*/
            for (int i = 1; i <= cores; i++) {
                File[] files1;
                if(i == cores){
                    files1 = new File[files.length - count];
                }else {
                    files1 = new File[m];
                }
                System.arraycopy(files, count, files1, 0, files1.length);
                ImageResizeModel resize = new ImageResizeModel(files1, dstFolder, start);
                new Thread(resize).start();
                count += m;
            }
        }
    }
}

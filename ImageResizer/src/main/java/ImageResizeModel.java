import net.coobird.thumbnailator.makers.ScaledThumbnailMaker;
import net.coobird.thumbnailator.resizers.ProgressiveBilinearResizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizeModel implements Runnable {
    private final ProgressiveBilinearResizer resizer = new ProgressiveBilinearResizer();
    private File[] files;
    private String dstFolder;
    private long start;

    public ImageResizeModel(File[] files, String dstFolder, long start) {
        this.files = files;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run() {
        for (File file : files) {
            try {
                BufferedImage image = ImageIO.read(file);
                if (image == null) {
                    continue;
                }
                BufferedImage newImage = new ScaledThumbnailMaker()
                        .scale(0.12)    //в процентах
                        .resizer(resizer)
                        .imageType(image.getType())
                        .make(image);

                File newFile = new File(dstFolder + "/" + file.getName());
//                    createThumbnail(file,newFile,100,150);
//                    Thumbnails.of(file)
//                            .size(300, 320)
//                            .resizer(resizer)
//                            .toFile(newFile);
                ImageIO.write(newImage, "jpg", newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Картинки были обработанны и сохранены. Время обработки составляет: " + (System.currentTimeMillis() - start));

    }
}

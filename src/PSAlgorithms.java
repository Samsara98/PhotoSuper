import acm.graphics.*;

import java.awt.*;

public class PSAlgorithms implements PSAlgorithmsInterface {

    public GImage rotateCounterclockwise(GImage source) {
        /************************************************
         * 旋转前，旧图片的信息
         ************************************************/
        int[][] oldPixelArray = source.getPixelArray();     // 旧图片数组
        int oldHeight = oldPixelArray.length;               // 旧图片高度
        int oldWidth = oldPixelArray[0].length;             // 旧图片宽度

        /************************************************
         * 旋转前，旧图片的信息
         ************************************************/
        int newHeight = oldWidth;                               // 新图片高度等于旧图片宽度
        int newWidth = oldHeight;                               // 新图片宽度等于旧图片高度
        int[][] newPixelArray = new int[newHeight][newWidth];   // 为新图片新建一个数组，行数是newHeight，列数是newWidth

        /************************************************
         * 新旧数组的像素对应关系
         ************************************************/
        for (int yNew = 0; yNew < newHeight; yNew++) {
            for (int xNew = 0; xNew < newWidth; xNew++) {
                int yOld = xNew;
                int xOld = oldWidth - yNew - 1;
                newPixelArray[yNew][xNew] = oldPixelArray[yOld][xOld];
            }
        }

        return new GImage(newPixelArray);
    }

    public GImage rotateClockwise(GImage source) {
        // TODO
        return null;
    }

    public GImage flipHorizontal(GImage source) {
        // TODO
        return null;
    }

    public GImage negative(GImage source) {
        int[][] pixelArray = source.getPixelArray();
        for (int row = 0; row < pixelArray.length; row++) {
            for (int col = 0; col < pixelArray.length; col++) {
                int pixel = pixelArray[col][row];
                int r = GImage.getRed(pixel);
                int g = GImage.getGreen(pixel);
                int b = GImage.getBlue(pixel);
                int newPixel = GImage.createRGBPixel(255 - r, 255 - b, 255 - g);
                pixelArray[col][row] = newPixel;
            }
        }

        return new GImage(pixelArray);
    }

    public GImage greenScreen(GImage source) {
        // TODO
        return null;
    }

    public GImage convolution(GImage source) {
        // TODO
        return null;
    }

    /**
     * 裁剪图片，裁剪后仅保留选区内容，其他全部删掉
     * @param source        要被裁剪的原始图片
     * @param cropX         选区左上角的x坐标
     * @param cropY         选区左上角的y坐标
     * @param cropWidth     选区的宽度
     * @param cropHeight    选区的高度
     * @return              裁剪后的图片
     */
    public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
        // TODO
        return null;
    }
}

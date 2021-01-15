import acm.graphics.*;

import java.util.ArrayList;


public class PSAlgorithms implements PSAlgorithmsInterface {
    public GImage rotateCounterclockwise(GImage source) {
        /************************************************
         * 旋转前，旧图片的信息
         ************************************************/
        int[][] oldPixelArray = source.getPixelArray();     // 旧图片数组
        int oldHeight = oldPixelArray.length;               // 图片高度
        int oldWidth = oldPixelArray[0].length;             // 图片宽度

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
        /************************************************
         * 旋转前，旧图片的信息
         ************************************************/
        int[][] oldPixelArray = source.getPixelArray();     // 旧图片数组
        int oldHeight = oldPixelArray.length;               // 旧图片高度
        int oldWidth = oldPixelArray[0].length;             // 旧图片宽度

        /************************************************
         * 旋转后，新图片的信息
         ************************************************/
        int newHeight = oldWidth;                               // 新图片高度等于旧图片宽度
        int newWidth = oldHeight;                               // 新图片宽度等于旧图片高度
        int[][] newPixelArray = new int[newHeight][newWidth];   // 为新图片新建一个数组，行数是newHeight，列数是newWidth

        /************************************************
         * 新旧数组的像素对应关系
         ************************************************/
        for (int yNew = 0; yNew < newHeight; yNew++) {
            for (int xNew = 0; xNew < newWidth; xNew++) {
                int yOld = oldHeight - xNew - 1;
                int xOld = yNew;
                newPixelArray[yNew][xNew] = oldPixelArray[yOld][xOld];
            }
        }

        return new GImage(newPixelArray);
    }

    public GImage flipHorizontal(GImage source) {  //水平翻转算法
        /************************************************
         * 旋转前，旧图片的信息
         ************************************************/
        int[][] oldPixelArray = source.getPixelArray();     // 旧图片数组
        int Height = oldPixelArray.length;               // 旧图片高度
        int Width = oldPixelArray[0].length;             // 旧图片宽度

        int[][] newPixelArray = new int[Height][Width];   // 为新图片新建一个数组，行数是newHeight，列数是newWidth

        /************************************************
         * 新旧数组的像素对应关系
         ************************************************/
        for (int yNew = 0; yNew < Height; yNew++) {
            for (int xNew = 0; xNew < Width; xNew++) {
                int yOld = yNew;
                int xOld = Width - xNew - 1;
                newPixelArray[yNew][xNew] = oldPixelArray[yOld][xOld];
            }
        }
        return new GImage(newPixelArray);
    }

    public GImage negative(GImage source) {  //反相算法
        int[][] pixelArray = source.getPixelArray();
        for (int row = 0; row < pixelArray[0].length; row++) {
            for (int col = 0; col < pixelArray.length; col++) {
                int pixel = pixelArray[col][row];
                int r = GImage.getRed(pixel);
                int g = GImage.getGreen(pixel);
                int b = GImage.getBlue(pixel);
                int newPixel = GImage.createRGBPixel(255 - r, 255 - g, 255 - b);
                pixelArray[col][row] = newPixel;  //将像素替换为反相后的像素
            }
        }

        return new GImage(pixelArray);
    }

    public GImage greenScreen(GImage source) {
        int[][] pixelArrary = source.getPixelArray();  //图像
        int Width = pixelArrary[0].length;
        int Height = pixelArrary.length;
        for (int newy = 0; newy < Height; newy++) {
            for (int newx = 0; newx < Width; newx++) {
                if (isGreen(pixelArrary[newy][newx])){
                    int transparentPixel = GImage.createRGBPixel(0, 0, 0, 0);
                    pixelArrary[newy][newx] = transparentPixel;
                }


            }
        }
        return new GImage(pixelArrary);
    }

    private boolean isGreen(int pixel) {
        int r = GImage.getRed(pixel);
        int g = GImage.getGreen(pixel);
        int b = GImage.getBlue(pixel);
        if (r >= b){
            if (g >= 2* r){
                return true;
            }
        }else {
            if (g >= 2* b){
                return true;
            }
        }
        return false;
    }

    public GImage convolution(GImage source) {   //卷积算法
        int[][] pixelArrary = source.getPixelArray();  //图像
        int Width = pixelArrary[0].length;
        int Height = pixelArrary.length;
        int[][] newPixelArrary = new int[Height][Width];
        for (int newy = 0; newy < Height; newy++) {
            for (int newx = 0; newx < Width; newx++) {
                newPixelArrary[newy][newx] = getAverageRGB(pixelArrary, newx, newy);
            }
        }
        return new GImage(newPixelArrary);
    }

    private int getAverageRGB(int[][] pixelArrary, int x, int y) {  //取卷积半径内RGB平均值
        int[] xArrary = getArrary(x);
        int[] yArrary = getArrary(y);
        java.util.List<Integer> r = new ArrayList<>();
        java.util.List<Integer> g = new ArrayList<>();
        java.util.List<Integer> b = new ArrayList<>();
        for (int x_ : xArrary) {
            if (x_ >= 0 && x_ < pixelArrary[0].length) {
                for (int y_ : yArrary) {
                    if (y_ >= 0 && y_ < pixelArrary.length) {
                        r.add(GImage.getRed(pixelArrary[y_][x_]));
                        g.add(GImage.getGreen(pixelArrary[y_][x_]));
                        b.add(GImage.getBlue(pixelArrary[y_][x_]));
                    }
                }
            }
        }
        int aver_r = getAver(r);
        int aver_g = getAver(g);
        int aver_b = getAver(b);
        return GImage.createRGBPixel(aver_r, aver_g, aver_b);
    }

    private int getAver(java.util.List<Integer> r) {  //取List平均值
        Integer sum = sum(r);
        return sum / r.size();
    }

    private int[] getArrary(int x) {  //获得像素卷积半径内的像素坐标
        int[] xArrary = new int[(1 + 2 * CONVOLUTION_RADIUS)];
        xArrary[0] = x;
        int num = CONVOLUTION_RADIUS;
        for (int i = 1; i <= xArrary.length - 2; i += 2) {
            xArrary[i] = x + num;
            xArrary[i + 1] = x - num;
            num--;
        }
        return xArrary;
    }


    private Integer sum(java.util.List<Integer> r) {  //求List和
        int sum = 0;
        for (int i = 0; i < r.size(); i++) {
            sum += r.get(i);
        }
        return sum;
    }

    /**
     * 裁剪图片，裁剪后仅保留选区内容，其他全部删掉
     *
     * @param source     要被裁剪的原始图片
     * @param cropX      选区左上角的x坐标
     * @param cropY      选区左上角的y坐标
     * @param cropWidth  选区的宽度
     * @param cropHeight 选区的高度
     * @return 裁剪后的图片
     */
    public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {  //裁剪算法
        int[][] pixelArray = source.getPixelArray(); //旧图片的像素数组
        int[][] newPixelArray = new int[cropHeight][cropWidth]; //裁剪后图片的像素数组
        for (int y = 0; y < cropHeight; y++) {
            for (int x = 0; x < cropWidth; x++) {
                int newx = cropX + x;
                int newy = cropY + y;
                newPixelArray[y][x] = pixelArray[newy][newx];
            }
        }
        return new GImage(newPixelArray);
    }


    /**
     * 均衡化
     *
     */
    public GImage equalization(GImage source) {   //均衡化算法
        int[][] pixelArray = source.getPixelArray(); //旧图片的像素数组
        int Width = pixelArray[0].length;
        int Height = pixelArray.length;
        int[][] newPixelArray = new int[Height][Width];  //新图片的像素数组
        int[] pixelLuminosity = new int[256];  //保存某亮度像素数量的数组，
        for (int y = 0; y < Height; y++) {  //获取全像素亮度
            for (int x = 0; x < Width; x++) {
                int Luminosity = getLuminosity(pixelArray[y][x]);
                pixelLuminosity[Luminosity] += 1;
            }
        }
        for (int y_ = 0; y_ < Height; y_++) {  //根据每个像素亮度在pixelLuminosity的位置，计算均衡化的亮度
            for (int x_= 0; x_ < Width; x_++) {
                int Luminosity = getLuminosity(pixelArray[y_][x_]);
                int sum =0;
                for (int i = Luminosity+1; i < 256 ; i++) {
                    sum += pixelLuminosity[i];
                    int rgb = 255*((Height*Width)-sum)/(Height*Width);
                    newPixelArray[y_][x_] = GImage.createRGBPixel(rgb, rgb, rgb);
                }
            }
        }

        return new GImage(newPixelArray);
    }

    private int getLuminosity(int pixel) {   //根据像素获取亮度
        int r = GImage.getRed(pixel);
        int g = GImage.getGreen(pixel);
        int b = GImage.getBlue(pixel);
        return computeLuminosity(r,g,b);
    }
}

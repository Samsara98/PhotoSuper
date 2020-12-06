import acm.graphics.*;

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
        int[][] newPixelArray = new int[newHeight][newWidth];   // 为新图片新建一个数组

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
        // TODO
        return null;
    }

    public GImage greenScreen(GImage source) {
        // TODO
        return null;
    }

    public GImage blur(GImage source) {
        // TODO
        return null;
    }

    public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight) {
        // TODO
        return null;
    }
}

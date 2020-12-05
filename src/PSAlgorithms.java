import acm.graphics.*;

public class PSAlgorithms implements PSAlgorithmsInterface {

    public GImage rotateLeft(GImage source) {
        int[][] oldPixelArray = source.getPixelArray();
        int oldHeight = oldPixelArray.length;
        int oldWidth = oldPixelArray[0].length;
        int newHeight = oldWidth;
        int newWidth = oldHeight;
        int[][] newPixelArray = new int[newHeight][newWidth];

        for (int yNew = 0; yNew < newHeight; yNew++) {
            for (int xNew = 0; xNew < newWidth; xNew++) {
            	int yOld = xNew;
            	int xOld = oldWidth - yNew - 1;
                newPixelArray[yNew][xNew] = oldPixelArray[yOld][xOld];
            }
        }

        return new GImage(newPixelArray);
    }

    public GImage rotateRight(GImage source) {
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

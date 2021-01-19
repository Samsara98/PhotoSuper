import acm.graphics.GImage;
import acm.graphics.GMath;

public interface PSAlgorithmsInterface {

	public static final int CONVOLUTION_RADIUS = 1;
	public static final int MOSAIC_RADIUS = 3;
	public static final double SATURATION = 0.8;
	public static final int ZIPRADIO = 2;
	public static final int SELECTRADIUS = 25;
	public static String TMPFILE = "./res/tmp.jpg";
	public GImage flipHorizontal(GImage source);
	public GImage rotateCounterclockwise(GImage source);
	public GImage rotateClockwise(GImage source);
	public GImage greenScreen(GImage source);
	public GImage negative(GImage source);
	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight);
	public GImage convolution(GImage source);
	
	/* 计算图片亮度 */
	public default int computeLuminosity(int r, int g, int b) {
		return GMath.round(0.299 * r + 0.587 * g + 0.114 * b);
	}
}

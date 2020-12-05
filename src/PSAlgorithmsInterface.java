import acm.graphics.GImage;
import acm.graphics.GMath;

public interface PSAlgorithmsInterface {
	
	public GImage flipHorizontal(GImage source);
	public GImage rotateLeft(GImage source);
	public GImage rotateRight(GImage source);
	public GImage greenScreen(GImage source);
	public GImage negative(GImage source);
	public GImage crop(GImage source, int cropX, int cropY, int cropWidth, int cropHeight);
	public GImage blur(GImage source);
	
	/* 计算图片亮度 */
	public default int computeLuminosity(int r, int g, int b) {
		return GMath.round(0.299 * r + 0.587 * g + 0.114 * b);
	}
}

import acm.program.*;
import acm.util.*;
import acm.graphics.*;
import adalab.core.diff.DiffImage;
import adalab.core.gui.GuiUtils;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Locale;

import javax.imageio.*;
import javax.swing.*;


public class PS extends GraphicsProgram {

    public static final int APPLICATION_HEIGHT = 600;

    // 支持保存为3种图片格式
    private static final String[] SAVE_IMAGE_EXTENSIONS = new String[]{"png", "bmp", "wbmp"};
    // 支持打开6种图片格式
    private static final String[] LOAD_IMAGE_EXTENSIONS = new String[]{"png", "bmp", "wbmp",
            "jpg", "gif", "jpeg"};

    // 用于在窗口顶部显示信息
    private JLabel infoLabel;

    // 用于在窗口底部显示像素坐标、RGB值等
    private JLabel statsLabel;

    // 当前显示的图像，如果无图像则为null
    private GImage currentImage;

    //橡皮擦是否打开
    private boolean cleanSwitch = false;

    File CURRENTFILE;

    // 图像算法
    private PSAlgorithms algorithms;

    protected GRect selectedArea;
    private boolean currentlySelecting;
    private double fixedX;
    private double fixedY;

    //美颜面板是否打开
    private boolean beautifyPanel;
    JComboBox type;
    JPanel jp = new JPanel();
    JButton btn = new JButton("OK");

    public void init() {
        String[] types = {"tea_time", "wangjiawei", "sakura","17_years_old", "chaplin",
                "jiang_nan", "lavender", "paris", "story", "chanel", "prague", "old_dream"};
        type = new JComboBox(types);

        addButtons();
        addActionListeners();
        setTitle("ImageShop");
        algorithms = new PSAlgorithms();
        addMouseListeners();
    }

    // 添加按钮
    private void addButtons() {
        // WEST是窗口左侧
        add(new JButton("打开图片"), WEST);
        add(new JButton("保存图片"), WEST);
        add(new JButton("叠加图片"), WEST);
        add(new JButton("对比图片"), WEST);
        add(new JSeparator(), WEST);
        add(new JButton("逆时针旋转"), WEST);
        add(new JButton("顺时针旋转"), WEST);
        add(new JButton("水平翻转"), WEST);
        add(new JButton("反相"), WEST);
        add(new JButton("绿屏扣图"), WEST);
        add(new JButton("卷积"), WEST);
        add(new JButton("裁剪"), WEST);
        add(new JButton("均衡化"), WEST);
        add(new JButton("初始化"), EAST);
        add(new JButton("马赛克"), EAST);
        add(new JButton("毛玻璃"), EAST);
        add(new JButton("饱和度增强"), EAST);
        add(new JButton("灰度图"), EAST);
        add(new JButton("色调调整(冷)"), EAST);
        add(new JButton("对比度增强"), EAST);
        add(new JButton("ZIP"), EAST);
        add(new JButton("放大"), EAST);
        add(new JButton("橡皮擦"), EAST);
        add(new JButton("美颜"), EAST);


        // NORTH是窗口上部
        infoLabel = new JLabel("SamsarA's PS");
        add(infoLabel, NORTH);

        // SOUTH是窗口下部
        statsLabel = new JLabel(" ");
        add(statsLabel, SOUTH);
    }

    // 当有按钮被按下时，自动调用这个函数
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        if (command.equals("打开图片")) {
            loadImage();
        } else if (command.equals("保存图片")) {
            saveImage();
        } else if (command.equals("叠加图片")) {
            overlayImage();
        } else if (command.equals("对比图片")) {
            diffImage();
        } else if (currentImage == null) {
            showErrorPopup("请先打开一张图片。");
        } else if (command.equals("水平翻转")) {
            GImage newImage = algorithms.flipHorizontal(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("逆时针旋转")) {
            GImage newImage = algorithms.rotateCounterclockwise(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("顺时针旋转")) {
            GImage newImage = algorithms.rotateClockwise(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("绿屏扣图")) {
            GImage newImage = algorithms.greenScreen(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("反相")) {
            GImage newImage = algorithms.negative(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("裁剪")) {
            if (currentlySelecting) {
                GImage newImage = algorithms.crop(currentImage, (int) selectedArea.getX(),
                        (int) selectedArea.getY(), (int) selectedArea.getWidth(),
                        (int) selectedArea.getHeight());

                setImage(newImage);
                infoLabel.setText(command + "已生效。");
            } else {
                showErrorPopup("请选择裁剪区域");
            }
        } else if (command.equals("卷积")) {
            GImage newImage = algorithms.convolution(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("均衡化")) {
            GImage newImage = algorithms.equalization(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("马赛克")) {
            GImage newImage = algorithms.mosaic(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("饱和度增强")) {
            GImage newImage = algorithms.saturationEnhancement(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("灰度图")) {
            GImage newImage = algorithms.grey(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("色调调整(冷)")) {
            GImage newImage = algorithms.colorConditioning(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("对比度增强")) {
            GImage newImage = algorithms.contrastEnhancement(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("毛玻璃")) {
            GImage newImage = algorithms.groundGlass(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("ZIP")) {
            GImage newImage = algorithms.zip(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("放大")) {
            GImage newImage = algorithms.extend(currentImage);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("美颜")) {
            if (!beautifyPanel) {
                add(jp, EAST);
                jp.add(type, EAST);
                jp.add(btn, EAST);
                btn.addActionListener(this);
                beautifyPanel = true;
            }
        } else if (command.equals("OK")) {
            String filterType = (String) type.getSelectedItem();
            currentImage.saveImage("./res/0.jpg");
            File file = new File("./res/0.jpg");
            GImage newImage = algorithms.beautify(file, 100, 100, filterType);
            setImage(newImage);
            infoLabel.setText(command + "已生效。");
        } else if (command.equals("橡皮擦")) {
            cleanSwitch = !cleanSwitch;
            if (cleanSwitch == true) {
                infoLabel.setText(command + "已生效。");
            } else {
                infoLabel.setText(command + "已关闭。");
            }
        } else if (command.equals("初始化")) {
            initImage();
        } else {
            infoLabel.setText("未知命令： " + command + "");
        }
        deselect();
    }

    // 当鼠标移动时，自动调用这个函数
    public void mouseMoved(MouseEvent e) {
        if (inImageBounds(e.getX(), e.getY())) {
            String status = "(x=" + e.getX() + ", y=" + e.getY() + ")";
            int pixel = currentImage.getPixelArray()[e.getY()][e.getX()];
            status += " (R=" + GImage.getRed(pixel) + ", G=" + GImage.getGreen(pixel) + ", B="
                    + GImage.getBlue(pixel) + ")";
            statsLabel.setText(status);
        } else {
            statsLabel.setText(" ");
        }
    }

    // 取消选择
    public void deselect() {
        if (currentlySelecting) {
            remove(selectedArea);
            selectedArea = null;
            currentlySelecting = false;
        }
    }

    // 当鼠标单击时，自动调用这个函数
    public void mouseClicked(MouseEvent e) {
        deselect();
    }

    // 当鼠标按下时，自动调用这个函数
    public void mousePressed(MouseEvent e) {
        deselect();

        if (cleanSwitch == true) {
            int x = e.getX();
            int y = e.getY();
            GImage newImage = algorithms.clean(currentImage, x, y);
            setImage(newImage);
        } else if (selectedArea == null) {
            fixedX = e.getX();
            fixedY = e.getY();
            selectedArea = new GRect(fixedX, fixedY, 0, 0);
            selectedArea.setColor(Color.RED);
            add(selectedArea);
            currentlySelecting = true;
        }
    }

    // 当鼠标拖拽时，自动调用这个函数
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (cleanSwitch == true) {
            GImage newImage = algorithms.clean(currentImage, x, y);
            setImage(newImage);
        } else if (currentImage != null && x <= currentImage.getWidth() && y <= currentImage.getHeight()) {
            double newX = Math.min(fixedX, x);
            double newY = Math.min(fixedY, y);

            double width = Math.abs(x - fixedX);
            double height = Math.abs(y - fixedY);

            selectedArea.setBounds(newX, newY, width, height);
        }
    }

    // 判断某一坐标是否位于图像内部
    private boolean inImageBounds(int x, int y) {
        if (currentImage == null) {
            return false;
        } else {
            double height = currentImage.getHeight();
            double width = height <= 0 ? 0 : currentImage.getWidth();
            return x >= 0 && x < width && y >= 0 && y < height;
        }
    }

    // 设置当前图像
    private void setImage(GImage image) {
        if (currentImage != null) {
            remove(currentImage);
        }

        setBackground(new Color(208, 188, 188));
        if (image != null) {
            setCanvasSize(image.getWidth(), image.getHeight());
            add(image);
        }

        currentImage = image;
    }

    /* 获取图像文件夹，默认是res
     */
    private File getImageDirectory() {
        File dir = new File(System.getProperty("user.dir") + "/res");
        if (!dir.isDirectory()) {
            dir = new File(System.getProperty("user.dir"));
        }
        return dir;
    }

    /* 获取保存文件夹，默认是output
     */
    private File getOutputDirectory() {
        File dir = new File(System.getProperty("user.dir") + "/output");
        if (!dir.isDirectory()) {
            dir = new File(System.getProperty("user.dir"));
        }
        return dir;
    }


    // 重置图片
    private void initImage() {
        // Init the image and add it to the canvas
        if (null != CURRENTFILE) {
            File currentFile = CURRENTFILE;
            GImage image = new GImage(currentFile.getAbsolutePath());
            setImage(image);
            infoLabel.setText("图片" + currentFile.getName() + "已初始化。");
        }
    }


    // 打开图像
    private void loadImage() {
        // Initialize the file chooser prompt
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(GuiUtils.getExtensionFileFilter("Image files", LOAD_IMAGE_EXTENSIONS));
        chooser.setCurrentDirectory(getImageDirectory());

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // Load the image and add it to the canvas
            File currentFile = chooser.getSelectedFile();
            CURRENTFILE = currentFile;
            GImage image = new GImage(currentFile.getAbsolutePath());
            setImage(image);
            infoLabel.setText("图片" + currentFile.getName() + "已打开。");
        }
    }

    // 保存图像
    private void saveImage() {
        if (currentImage == null) {
            showErrorPopup("待保存图片为空");
            return;
        }

        // 文件选择器
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(GuiUtils.getExtensionFileFilter(".png, .bmp, and .wbmp files", SAVE_IMAGE_EXTENSIONS));
        chooser.setCurrentDirectory(getImageDirectory());

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            // 检查文件是否已经存在
            if (chooser.getSelectedFile().exists()) {
                if (JOptionPane.showConfirmDialog(this,
                        "文件已存在。覆盖当前文件？",
                        "覆盖？", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // 保存图像
            try {
                currentImage.saveImage(chooser.getSelectedFile());
                infoLabel.setText("图像已保存为" + chooser.getSelectedFile().getName() + "。");
            } catch (ErrorException e) {
                showErrorPopup("文件名无效，请保存为.png或.bmp文件。");
            }
        }
    }

    // 将所选图像叠加到当前图像上面
    private void overlayImage() {
        if (currentImage == null) {
            showErrorPopup("请先选择一张图片进行叠加。");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(GuiUtils.getExtensionFileFilter("Image files", LOAD_IMAGE_EXTENSIONS));
        chooser.setCurrentDirectory(getImageDirectory());

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // 当前图像副本
            Image oldImage = currentImage.getImage();
            int width = oldImage.getWidth(getGCanvas());
            int height = oldImage.getHeight(getGCanvas());

            // 将图像叠加
            Image newImage = getGCanvas().createImage(width, height);
            Graphics g = newImage.getGraphics();
            g.drawImage(oldImage, 0, 0, getGCanvas());
            File file = chooser.getSelectedFile();
            Image overlay = new GImage(file.getAbsolutePath()).getImage();
            int x0 = (width - overlay.getWidth(getGCanvas())) / 2;
            int y0 = (height - overlay.getHeight(getGCanvas())) / 2;
            g.drawImage(overlay, x0, y0, getGCanvas());

            currentImage.setImage(newImage);
            infoLabel.setText("成功叠加图像" + file.getName() + "。");
        }
    }

    // 比较两幅图像的差别
    private void diffImage() {
        if (currentImage == null) {
            showErrorPopup("当前无图像");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(GuiUtils.getExtensionFileFilter("Image files", LOAD_IMAGE_EXTENSIONS));
        chooser.setCurrentDirectory(getOutputDirectory());

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File imageFile2 = chooser.getSelectedFile();
            try {
                Image image2 = ImageIO.read(imageFile2);
                Image image1 = currentImage.getImage();
                new DiffImage(image1, image2);
            } catch (IOException ioe) {
                showErrorPopup("无法读取" + imageFile2.getName() + "图片数据: " + ioe.getMessage());
            }
        }
    }

    /*
     * 显示错误信息
     */
    private void showErrorPopup(String text) {
        JOptionPane.showMessageDialog(this, "Error: " + text, "Error", JOptionPane.ERROR_MESSAGE);
        infoLabel.setText("错误： " + text);
    }
}


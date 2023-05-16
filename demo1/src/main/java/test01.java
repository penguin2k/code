import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class test01 {
    public static void main(String[]  args) throws AWTException,IOException{
        //创建一个robot对象
        Robot robut=new Robot();
        //获取屏幕分辨率
        Dimension d=  Toolkit.getDefaultToolkit().getScreenSize();
        //打印屏幕分辨率
        System.out.println(d);
        //创建该分辨率的矩形对象
        Rectangle screenRect=new  Rectangle(d);
        //根据这个矩形截图
        BufferedImage  bufferedImage=robut.createScreenCapture(screenRect);
        //保存截图
        File file=new File("截图1.png");
        ImageIO.write(bufferedImage,"png",file);
    }
}
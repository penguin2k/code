import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class test01 {
    public static void main(String[]  args) throws AWTException,IOException{
        //����һ��robot����
        Robot robut=new Robot();
        //��ȡ��Ļ�ֱ���
        Dimension d=  Toolkit.getDefaultToolkit().getScreenSize();
        //��ӡ��Ļ�ֱ���
        System.out.println(d);
        //�����÷ֱ��ʵľ��ζ���
        Rectangle screenRect=new  Rectangle(d);
        //����������ν�ͼ
        BufferedImage  bufferedImage=robut.createScreenCapture(screenRect);
        //�����ͼ
        File file=new File("��ͼ1.png");
        ImageIO.write(bufferedImage,"png",file);
    }
}
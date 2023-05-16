import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.nio.charset.*;
import java.text.*;


public class Client {

    public static Socket client=null;
    public static StringBuilder uidReceiver = null;
    public static void main(String[] args) throws Exception{
        ClientFrame cframe = new ClientFrame();
        cframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        cframe.setLocation((w - cframe.WIDTH)/2, (h - cframe.HEIGHT)/2);
        cframe.setVisible(true);
        try {
            client = new Socket(InetAddress.getLocalHost(), 6666);
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = in.read(bytes);
            cframe.jtaChat.append(new String(bytes, 0, len));
            cframe.jtaChat.append("\n");
            while (true) {
                in = client.getInputStream();
                len = in.read(bytes);
                // System.out.println(len);
                String msg = new String(bytes, 0, len);
                String type = msg.substring(0, msg.indexOf("/"));
                String chat = msg.substring(msg.indexOf("/")+1);
                if (type.equals("OnlineListUpdate")) {
                    DefaultTableModel dtm = (DefaultTableModel) cframe.jtbOnline.getModel();
                    dtm.setRowCount(0);
                    String[] onlineList = chat.split(",");
                    for (String member : onlineList) {
                        String[] tmp = new String[3];
                        String me=member.substring(member.indexOf("~") + 1);
                        if (me.equals(InetAddress.getLocalHost().getHostAddress() + ":" + client.getLocalPort())) {
                            continue;
                        }
                        tmp[0]="";
                        tmp[1] = member.substring(0, member.indexOf(":"));
                        tmp[2] = member.substring(member.indexOf(":") + 1);
                        dtm.addRow(tmp);
                    }
                    DefaultTableCellRenderer tbr = new DefaultTableCellRenderer();
                    tbr.setHorizontalAlignment(JLabel.CENTER);
                    cframe.jtbOnline.setDefaultRenderer(Object.class, tbr);
                }
                else if (type.equals("Chat")) {
                    String sender = chat.substring(0, chat.indexOf("/"));
                    String word = chat.substring(chat.indexOf("/") + 1);
                    cframe.jtaChat.append(cframe.sdf.format(new Date()) + "\n来自 " + sender + ":\n" + word + "\n\n");
                    cframe.jtaChat.setCaretPosition(cframe.jtaChat.getDocument().getLength());
                }
            }
        }catch(Exception e)
        {
            cframe.jtaChat.append("服务器挂了.....\n");
            e.printStackTrace();
        }
    }
}

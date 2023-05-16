import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class exp_client implements Runnable {
    static Socket cs=null;
    String hostName="localhost";
    int port =8000;
    String number=null;
    DataInputStream is=null;
    DataOutputStream os =null;
    DataInputStream stdin=null;
   static clientfram cframe = new clientfram();
    exp_client(String hostName,int port)
    {
        this.hostName=hostName;
        this.port=port;
    }
    public void run() {
     try
     {
         cs=new Socket(hostName,port);
         is=new DataInputStream(cs.getInputStream());
         os=new DataOutputStream(cs.getOutputStream());
         stdin=new DataInputStream(System.in);
         System.out.println("�ͻ���");
         os.writeUTF("ni hao");
         os.flush();
         new rcThread(cs).start();
         new csThread(cs).start();
     }
     catch (IOException e)
     {
         System.err.println(e);
     } catch (Exception e) {
         throw new RuntimeException(e);
     }
    }
    class rcThread extends Thread {
        Socket client;
        DataInputStream is=null;
        rcThread(Socket client)throws Exception
        {
            this.client=client;
            is=new DataInputStream(client.getInputStream());
        }
        @Override
        public void run()
        {
            String str;
            stdin = new DataInputStream(System.in);
                try {
                    while (true) {
                        System.out.println("�����߳�����");
                        str = is.readUTF();
                        System.out.println(str);
                        cframe.jtaChat.append(str);
                        cframe.jtaChat.append("\n");
                    }
                } catch (Exception e) {
                    System.err.println(e.toString());
                }

        }
    }
    class csThread extends Thread{
        Socket client;
        DataOutputStream is=null;
        csThread(Socket client)throws Exception
        {
            this.client=client;
            os = new DataOutputStream(client.getOutputStream());
        }

        @Override
        public void run() {
            String str;
            try
            {
                while (true)
                {
                    str=stdin.readLine();
                    System.out.println("�����߳�����");
                    os.writeUTF(client.getInetAddress().toString()+":"+client.getLocalAddress()+"�û���"+number+" "+str);
                    os.flush();
                }
            } catch (IOException e) {
                System.err.println(e.toString());
            }
        }
    }

    public static void main(String[] args) {
        //���ڹرռ���Ч������ͨ���˳����˳��ͻ����Ա��ƺ�
        cframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //��ȡ������Ļ����ֱ���
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        //��ȡ������Ļ����ֱ���
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        //����������
        cframe.setLocation((w - cframe.WIDTH)/2, (h - cframe.HEIGHT)/2);
        //���ÿͻ��˴���Ϊ�ɼ�
        cframe.setVisible(true);

        exp_client ec=new exp_client("localhost",8888);
        Thread tec=new Thread(ec);
        try{
            tec.start();
            tec.join();
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}



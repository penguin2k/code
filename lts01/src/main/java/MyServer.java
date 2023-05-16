import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MyServer implements Runnable {
    ServerSocket ss=null;
    Socket cs=null;
    Vector<Socket>userlist=new Vector<>();
    MyServer(int port)
            {
                try{
                    ss=new ServerSocket(port);
                    System.out.println("�����˿�"+port);
                    System.out.println("Server online...."+ss.getInetAddress().getLocalHost().getHostAddress()+","+port);
                }
                catch (IOException e)
                {
                    System.err.println("�˿�����ʧ��");
                    System.exit(1);
                }
            }

    @Override
    public void run() {
        try {
            while (true)
            {
                cs=ss.accept();
                userlist.add(cs);
                System.out.println(cs.getLocalAddress());
                System.out.println("��ӳɹ�");
                clientThread ct=new clientThread(cs);
                Thread tct=new Thread(ct);
                tct.start();
            }
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
     /*       System.err.println("���ܿͻ�������ʧ��");
            System.exit(1);*/
        }

    }
    class clientThread implements  Runnable{
        Socket cs=null;
        clientThread(Socket cs)
        {
            this.cs=cs;
        }
        @Override
        public void run() {
            String inputstr,str,outputstr = null;
            try
            {
                System.out.println("�߳�����");
                DataOutputStream os=new DataOutputStream(cs.getOutputStream());
                DataInputStream is=new DataInputStream(cs.getInputStream());
                os.writeUTF("welcome to my chat server"+cs.getPort());
                os.flush();
                byte[] buf = new byte[1024];
                int len = 0;
                /*DataInputStream stdin=new DataInputStream(System.in);*/
                while ((str=is.readUTF()) !=null)
                {
                    System.out.println("����ѭ��");
/*                    inputstr= is.readUTF();
                    outputstr=is.readUTF();*/
/*                    System.out.println("customer:"+inputstr);*/
                    System.out.println("shuchu");
                    for(Socket cs:userlist)
                    {
                        os=new DataOutputStream(cs.getOutputStream());
                        os.writeUTF(str);
                        os.flush();
                    }
                    if(str.equals("Exit")){

                        //����ArrayList��Map
                        userlist.remove(cs);
                        break;
                    }
                    len=is.read(buf);
                    String msg=new String(buf,0,len);
                    //   System.out.println(msg);

                    //��Ϣ���ͣ��˳���������
                    String type=msg.substring(0,msg.indexOf('/'));
                    //�������ݣ��ջ�����������
                    String chat=msg.substring(msg.indexOf('/')+1);
                    if(type.equals("Chat")) {
                        //��ȡ��������Ϣ
                        String[] receiveArr = chat.substring(0, chat.indexOf('/')).split(",");
                        //��ȡ��������
                        String word = chat.substring(chat.indexOf('/') + 1);
                        System.out.println(word);
                        //�������߹㲥����������Ϣ
                    }

                }
                os.close();
                is.close();
                cs.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
    public static void main(String[] args) {
        MyServer ms=new MyServer(8888);

        Thread tms=new Thread(ms);
        tms.start();
    }
}

package org.example;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class multicastDemo01 extends Thread
{
    String message[] = {"失物招领：有谁在操场丢失钥匙一串，请到学校广播站认领。","大风蓝色预警：预计今天下午有北风6级，请有关单位和人员做好防范准备。"};
    int port = 9876;//组播的端口
    InetAddress group = null;//组播的组地址
    MulticastSocket mutiSocket = null;//组播套接字

    public multicastDemo01()
    {
        try
        {
            group = InetAddress.getByName("230.198.112.0");//设置广播组地址
            mutiSocket = new MulticastSocket(port);//多点广播套接字将在port端口广播
            mutiSocket.setTimeToLive(1);
            mutiSocket.joinGroup(group);
        }
        catch (Exception e)
        {
            System.out.println("Error:"+e);
        }
    }

    public void run()
    {
        while(true)
        {
            try
            {
                DatagramPacket packet = null;
                for(String msg : message)//循环发送每条广播信息
                {
                    byte buff[] = msg.getBytes();
                    packet = new DatagramPacket(buff, buff.length,group,port);
                    System.out.println(new String(buff));
                    mutiSocket.send(packet);
                    sleep(2000);
                }
            }
            catch (Exception e)
            {
                System.out.println("Error:"+e);
            }
        }
    }

    public static void main(String[] args)
    {
        new multicastDemo01().start();
    }
}
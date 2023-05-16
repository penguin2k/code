import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

class ClientFrame extends JFrame {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final int WIDTH = 700;
    final int HEIGHT = 700;
    JButton btnSend = new JButton("发送");
    JButton btnClear = new JButton("清屏");
    JButton btnExit = new JButton("退出");
    JLabel lblReceiver = new JLabel("谁来接收：");
    JTextArea jtaSay = new JTextArea();
    JTextArea jtaChat = new JTextArea();
    String[] colTitles = {" ", "IP", "端口"};
    String[][] rowData = null;
    JTable jtbOnline = new JTable
            (
                    new DefaultTableModel(rowData, colTitles) {
                        //表格不可编辑，只可显示
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    }
            );
    JScrollPane jspChat = new JScrollPane(jtaChat);
    JScrollPane jspOnline = new JScrollPane(jtbOnline);
    public ClientFrame() {
        setTitle("聊天室");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLayout(null);
        btnSend.setBounds(20, 600, 100, 60);
        btnClear.setBounds(140, 600, 100, 60);
        btnExit.setBounds(260, 600, 100, 60);
        lblReceiver.setBounds(20, 420, 300, 30);
        btnSend.setFont(new Font("宋体", Font.BOLD, 18));
        btnClear.setFont(new Font("宋体", Font.BOLD, 18));
        btnExit.setFont(new Font("宋体", Font.BOLD, 18));
        this.add(btnSend);
        this.add(btnClear);
        this.add(btnExit);
        this.add(lblReceiver);
        jtaSay.setBounds(20, 460, 360, 120);
        jtaSay.setFont(new Font("楷体", Font.BOLD, 16));
        this.add(jtaSay);
        jtaChat.setLineWrap(true);
        jtaChat.setEditable(false);
        jtaChat.setFont(new Font("楷体", Font.BOLD, 16));
        jspChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jspChat.setBounds(20, 20, 360, 400);
        this.add(jspChat);
        jspOnline.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jspOnline.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jspOnline.setBounds(420, 20, 250, 400);
        this.add(jspOnline);
        btnSend.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                jtaChat.setCaretPosition(jtaChat.getDocument().getLength());
                                try {
                                    if (Client.uidReceiver.toString().equals("") == false) {
                                        jtaChat.append(sdf.format(new Date()) + "\n发往 " + Client.uidReceiver.toString() + ":\n");
                                        jtaChat.append(jtaSay.getText() + "\n\n");
                                        OutputStream out = Client.client.getOutputStream();
                                        out.write(("Chat/" + Client.uidReceiver.toString() + "/" + jtaSay.getText()).getBytes());
                                    }
                                } catch (Exception e) {
                                } finally {
                                    jtaSay.setText("");
                                }
                            }
                        }
                );
        btnClear.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                jtaChat.setText("");
                            }
                        }
                );
        btnExit.addActionListener
                (
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    OutputStream out = Client.client.getOutputStream();
                                    out.write("Exit/".getBytes());
                                    System.exit(0);
                                } catch (Exception e) {
                                }
                            }
                        }
                );
        jtbOnline.addMouseListener
                (
                        new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent event) {
                                DefaultTableModel tbm = (DefaultTableModel) jtbOnline.getModel();
                                int[] selectedIndex = jtbOnline.getSelectedRows();
                                Client.uidReceiver = new StringBuilder("");
                                for (int i = 0; i < selectedIndex.length; i++) {
                                    Client.uidReceiver.append((String) tbm.getValueAt(selectedIndex[i], 1));
                                    Client.uidReceiver.append(":");
                                    Client.uidReceiver.append((String) tbm.getValueAt(selectedIndex[i], 2));
                                    if (i != selectedIndex.length - 1)
                                        Client.uidReceiver.append(",");
                                }
                                lblReceiver.setText("发给：" + Client.uidReceiver.toString());
                            }

                            @Override
                            public void mousePressed(MouseEvent event) {}

                            @Override
                            public void mouseReleased(MouseEvent event) {}

                            @Override
                            public void mouseEntered(MouseEvent event) {}

                            @Override
                            public void mouseExited(MouseEvent event) {}
                        }
                );
    }
}

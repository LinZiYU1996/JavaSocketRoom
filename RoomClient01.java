package net;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 
 * @author linziyu
 *OK�Ľ�����
 */

public class RoomClient01 implements ActionListener {
	static TextArea textArea;
	static TextField textField;
	static Button send;
	static String toServer;
	static DataInputStream dataInputStream;
	static DataOutputStream dataOutputStream;
	 Socket socket;
	public RoomClient01(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}
	/*
	 * ����GUI���
	 */
	public void createUI () throws IOException {
		Frame f = new Frame("�ͻ���");
		textArea = new TextArea();
         textField = new TextField();
         send = new Button("send");
         send.addActionListener(this);
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        p.add(textField, "Center");
        p.add(send, "East");
        f.add(textArea, "Center");
        f.add(p, "South");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setSize(400, 400);
        f.setLocation(600, 0);
        f.setVisible(true);
        connect();
}
	/*
	 * ���ӷ���˲��Ҳ��ϴӷ���˶�ȡ����
	 */
	
	public void connect() throws IOException {
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		while(true) {
			String string = dataInputStream.readUTF();
			textArea.append("����>>"+string+"\n");
		}
	}
	/*
	 * ��ӵ���¼�
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source==send) {
			toServer=textField.getText();
			try {
				textArea.append("�ͻ�>>"+toServer+"\n");
				dataOutputStream.writeUTF(toServer);
				dataOutputStream.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 5551);
		RoomClient01 roomClient01 = new RoomClient01(socket);
		roomClient01.createUI();
	}

}

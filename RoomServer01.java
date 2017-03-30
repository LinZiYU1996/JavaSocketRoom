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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 
 * @author linziyu
 *OK�Ľ�����
 */
public class RoomServer01 implements ActionListener{
	 static  TextArea textArea;
	 static TextField textField;
	 static String fromclient;
	 static Button send;
	 static DataInputStream dataInputStream;
	 static DataOutputStream dataOutputStream;
	 ServerSocket serverSocket;
	 /*
	  * ����GUI���
	  */
	 	public void createUI () throws IOException {
			Frame f = new Frame("�����");
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
	 	public RoomServer01(ServerSocket serverSocket) {
			// TODO Auto-generated constructor stub
	 		this.serverSocket = serverSocket;
		}
	 	/*
	 	 *��ӵ���¼� 
	 	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source==send) {
			String toclient = textField.getText();
			textArea.append("����>>"+toclient);
					try {
						dataOutputStream.writeUTF(toclient);
						dataOutputStream.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		}
	}
	/*
	 * ���ӿͻ��˲��Ҳ��ϴӿͻ��˶�ȡ����
	 */
	public  void connect() throws IOException{
		Socket socket = serverSocket.accept();
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		while(true) {
			String string = dataInputStream.readUTF();
			textArea.append("�ͻ�>>"+string+"\n");
		}
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(5551);
		RoomServer01 roomServer01 = new RoomServer01(serverSocket);
		roomServer01.createUI();
	}

}

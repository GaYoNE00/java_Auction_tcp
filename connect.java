import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;

public class connect {
	public void main_1(String nickname_g, Main_View mv) {
		Socket socket = null;
		Starting st;
		String nickname = nickname_g;
		try {
			socket = new Socket("118.46.199.58", 9500);
			System.out.println("���Ἲ��!");
			st = new Starting(socket, nickname, mv);
			new ReadThread(socket, st).start();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}

class ReadThread extends Thread {

	Socket socket;

	Starting st;

	public ReadThread(Socket socket, Starting st) {

		this.st = st;

		this.socket = socket;

	}

	public void run() {

		BufferedReader br = null;
		updatesql as = new updatesql();
		try {

			// �����κ��� ���۵� ���ڿ� �о���� ���� ��Ʈ����ü ����

			br = new BufferedReader(

					new InputStreamReader(socket.getInputStream()));

			while (true) {

				// �������κ��� ���ڿ� �о��

				String str = br.readLine();
				st.lblpriceing.setText(as.updatesql() + "");
				st.lblgetter.setText(as.getter);

				if (str == null) {

					System.out.println("������ ������");

					break;

				} else if (str.equals("[�ý���] ����! ������� ��Ÿ� �����߽��ϴ�.")) {
					st.btngetin.setText("������");
				}

				// ���۹��� ���ڿ� ȭ�鿡 ���

				// System.out.println("[server] " + str);

				st.getJta().append(str + "\n");
				st.jscp.getVerticalScrollBar().setValue(st.jscp.getVerticalScrollBar().getMaximum());
				
			}

		} catch (IOException | SQLException ie) {

			System.out.println(ie.getMessage());

		} finally {

			try {

				if (br != null)
					br.close();

				if (socket != null)
					socket.close();

			} catch (IOException ie) {
			}

		}

	}

}

class WriteThread {

	Socket socket;

	Starting st;

	String str;

	String id;

	public WriteThread(Starting st) {

		this.st = st;

		this.socket = st.socket;

	}

	public void sendMsg() {

		// Ű����κ��� �о���� ���� ��Ʈ����ü ����

		BufferedReader br =

				new BufferedReader(new InputStreamReader(System.in));

		PrintWriter pw = null;

		try {

			// ������ ���ڿ� �����ϱ� ���� ��Ʈ����ü ����

			pw = new PrintWriter(socket.getOutputStream(), true);

			// ù��° �����ʹ� id �̴�. ���濡�� id�� �Բ� �� IP�� �����Ѵ�.

			if (st.isFirst == true) {

				InetAddress iaddr = socket.getLocalAddress();

				String ip = iaddr.getHostAddress();

				getId();

				System.out.println("ip:" + ip + "id:" + id);

				str = "[" + id + "] �� �α��� (" + ip + ")";

			} else if (st.msg.equals("����! ������� ��Ÿ� �����߽��ϴ�.")) {
				str = "[�ý���] " + st.msg;

			} else {
				str = "[" + id + "] " + st.msg;

			}

			// �Է¹��� ���ڿ� ������ ������

			pw.println(str);

		} catch (IOException ie) {

			System.out.println(ie.getMessage());

		} finally {

			try {

				if (br != null)
					br.close();

				// if(pw!=null) pw.close();

				// if(socket!=null) socket.close();

			} catch (IOException ie) {

				System.out.println(ie.getMessage());

			}

		}

	}

	public void getId() {

		id = st.nickname;

	}

}
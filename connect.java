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
			System.out.println("연결성공!");
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

			// 서버로부터 전송된 문자열 읽어오기 위한 스트림객체 생성

			br = new BufferedReader(

					new InputStreamReader(socket.getInputStream()));

			while (true) {

				// 소켓으로부터 문자열 읽어옴

				String str = br.readLine();
				st.lblpriceing.setText(as.updatesql() + "");
				st.lblgetter.setText(as.getter);

				if (str == null) {

					System.out.println("접속이 끊겼음");

					break;

				} else if (str.equals("[시스템] 낙찰! 경매인이 경매를 종료했습니다.")) {
					st.btngetin.setText("나가기");
				}

				// 전송받은 문자열 화면에 출력

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

		// 키보드로부터 읽어오기 위한 스트림객체 생성

		BufferedReader br =

				new BufferedReader(new InputStreamReader(System.in));

		PrintWriter pw = null;

		try {

			// 서버로 문자열 전송하기 위한 스트림객체 생성

			pw = new PrintWriter(socket.getOutputStream(), true);

			// 첫번째 데이터는 id 이다. 상대방에게 id와 함께 내 IP를 전송한다.

			if (st.isFirst == true) {

				InetAddress iaddr = socket.getLocalAddress();

				String ip = iaddr.getHostAddress();

				getId();

				System.out.println("ip:" + ip + "id:" + id);

				str = "[" + id + "] 님 로그인 (" + ip + ")";

			} else if (st.msg.equals("낙찰! 경매인이 경매를 종료했습니다.")) {
				str = "[시스템] " + st.msg;

			} else {
				str = "[" + id + "] " + st.msg;

			}

			// 입력받은 문자열 서버로 보내기

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
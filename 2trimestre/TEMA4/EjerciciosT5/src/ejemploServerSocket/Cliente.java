package ejemploServerSocket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	public static void main(String[] args) {
		try {
			Socket s=new Socket("192.168.1.137",1234);
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			bw.write("Me recibe??");
			bw.flush();
			bw.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

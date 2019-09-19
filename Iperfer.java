import java.util.*;
import java.net.*;
import java.io.*;



public class Iperfer {
	public static String hostName;
	public static int portNum;
	
	public static void client(String host, String port) throws Exception {
		portNum = Integer.parseInt(port);
		Socket client = new Socket("localhost", 8989);
		
		String str = "hello there";
		
		OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
		PrintWriter out = new PrintWriter(os);
		
		os.write(str);
		
	}
	public static void server(String port) throws IOException {
		portNum = Integer.parseInt(port);
		ServerSocket server = new ServerSocket(8989);
		Socket client = server.accept();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String sr = br.readLine();
		System.out.println(sr);
	}
	public static void args(String host, String port) {
		hostName = host;
		portNum = Integer.parseInt(port);
		
	}
	
	public static void main(String[] args) throws Exception{
		//client(args[0], args[1]);
		server(args[1]);
		//client(args[0], args[1]);
		
 
	}

}

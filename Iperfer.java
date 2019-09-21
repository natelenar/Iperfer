import java.util.*;
import java.net.*;
import java.io.*;



public class Iperfer {

	public static void client(String[] arg) throws Exception {
		String host = arg[0];
		String port = arg[1];
		int time = Integer.parseInt(arg[2]);

		int sentPkt = 0;
		long t = time * 1000;
		long ms = System.currentTimeMillis();
		t = ms + t;
/*		int[] packet = new int[250];
		for(int i = 0; i < 250; i++){
			packet[i] = 0;
		}*/
		byte[] packet = new byte[1000];
		for(int i = 0; i <1000; i++){
			packet[i] = 0;
		}
		try{
			Socket client = new Socket(host, Integer.parseInt(port));

			DataOutputStream os = new DataOutputStream(client.getOutputStream());

			while(System.currentTimeMillis() < t){
				os.write(packet);
				sentPkt ++;
			}
		}
		catch (Exception e){
			System.out.println("ERRORRRRRR!");
		}
		System.out.println(t);
		long mbps = ((sentPkt / 1000) * 8) / time;

		System.out.println("sent = " + sentPkt + " rate = " + mbps);
	}

	public static void server(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
		Socket client = server.accept();
		int packet = 0;
		int received = 0;
		long mbps  = 0;
		long t = 0;
		byte[] cbuf = new byte[1000];
		try{
			DataInputStream br = new DataInputStream(client.getInputStream());
			long begin = System.currentTimeMillis();
			while((packet = br.read(cbuf, 0, cbuf.length)) != -1){
				received++;
			}
			long finish = System.currentTimeMillis();
			t = finish - begin;

		} catch(Exception e){
			System.out.println("ERROR in Server");
		}
		System.out.println(t/1000);
		mbps = ((received / 1000) * 8) / (t/1000);
		System.out.println("received = " + received + " rate = " + mbps);
	}
	public static String[] argsParser(String[] args){
		String[] argVars = new String[7];
		if(args[0].equals("-c")) {
			if(args.length != 7) {
				System.out.println("Error: invalid arguments");
			}
			if((!args[1].equals("-h")) && (!args[3].equals("-p")) && (!args[5].equals("-t"))) {
				System.out.println("Error: invalid arguments");
			}
			if((Integer.parseInt(args[4]) < 1024) || (Integer.parseInt(args[4]) > 65535)) {
				System.out.println("Error: port number must be in the range 1024 to 65535");
			}
			argVars[0] = args[2];
			argVars[1] = args[4];
			argVars[2] = args[6];
		}
		if(args[0].equals("-s")){
			if (args.length != 3) {
				System.err.println("Error: invalid arguments");
				System.exit(1);
			}
			if((Integer.parseInt(args[2]) < 1024) || (Integer.parseInt(args[2]) > 65535)) {
				System.out.println("Error: port number must be in the range 1024 to 65535");
			}
			argVars[0] = args[2];
		}
		return argVars;
	}

	public static void main(String[] args) throws Exception{
		String[] argVars = argsParser(args);
		if(args[0].equals("-c")){
			client(argVars);
		}
		if(args[0].equals("-s")){
			server(argVars);
		}
	}

}

import java.util.*;
import java.net.*;
import java.io.*;



public class Iperfer {
	public static String hostName;
	public static int portNum;

	public static void client(String[] arg) throws Exception {
		String host = arg[0];
		String port = arg[1];
		int time = Integer.parseInt(arg[2]);

		int sentPkt = 0;
		long t = time * 1000;
		long ms = System.currentTimeMillis();
		t = ms + t;
		byte byteString[];
		for(int i = 0; i < 1000; i++){
			byteString[i] = 0;
		}
		try{
			portNum = Integer.parseInt(port);
			Socket client = new Socket("localhost", 8989);

			String str = "hello there";

			OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
			PrintWriter out = new PrintWriter(os);

			while(System.currentTimeMillis() < ms){
				out.print(byteString);
				sentPkt ++;
			}
		}
		catch (Exception e){
			System.out.println("ERRORRRRRR!");
		}
		int mbps = ((sentPkt / 1000) * 8) / t;

		System.out.println("sent = " + sentPkt + "rate = " + mbps);
	}

	public static void server(String[] args) throws IOException {
		portNum = Integer.parseInt(args[0]);
		ServerSocket server = new ServerSocket(8989);
		Socket client = server.accept();
		byte byteString[] = new  byte[1000];
		int received;
		int mbps;

		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while(br.read(byteString, 0, 1000) != -1){
				received++;
			}
		} catch(Exception e){
			System.out.println("ERROR in Server");
		}
		mbps = ((received / 100) * 8) / t;
		System.out.println("received = " + received + "rate = " + mbps);
	}
	public static void args(String host, String port) {
		hostName = host;
		portNum = Integer.parseInt(port);

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
			argVars[3] = args[6];
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

		//client(args[0], args[1]);
		server(args[1]);
		//client(args[0], args[1]);


	}

}

import java.util.*;
import java.net.*;
import java.io.*;



public class Iperfer {
	public static String hostName;
	public static int portNum;

	public static void client(String host, String port, int time) throws Exception {
		int sentPkt = 0;
		long t = time * 1000;
		long ms = System.currentTimeMillis();
		t = ms + t;
		byte byteString[];
		for(int i = 0; i < 1000; i++){
			byteString[i] = 0;
		}

		portNum = Integer.parseInt(port);
		Socket client = new Socket("localhost", 8989);

		String str = "hello there";

		OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
		PrintWriter out = new PrintWriter(os);

		while(System.currentTimeMillis() < ms){
			os.write(byteString);
			sentPkt ++;
		}
		int mbps = ((sentPkt / 1000) * 8) / t;

		System.out.println("sent = " + sentPkt + "rate = " + mbps);
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

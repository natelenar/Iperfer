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
			String hostName = args[2];
			int portNumber = Integer.parseInt(args[4]);
			int time = Integer.parseInt(args[6]);
			try (
		            Socket iperf = new Socket(hostName, portNumber);
		            PrintWriter out =
		                new PrintWriter(iperf.getOutputStream(), true);
		            BufferedReader in =
		                new BufferedReader(
		                    new InputStreamReader(iperf.getInputStream()));
		            BufferedReader stdIn =
		                new BufferedReader(
		                    new InputStreamReader(System.in))
		        ) {
				String userInput;
	            while ((userInput = stdIn.readLine()) != null) {
	                out.println(userInput);
	                System.out.println("echo: " + in.readLine());
	            }
		        } catch (UnknownHostException e) {
		            System.exit(1);
		        } catch (IOException e) {
		            System.exit(1);
		        } 
		    }
		//client(args[0], args[1]);
		server(args[1]);
		//client(args[0], args[1]);
		
 
	}

}

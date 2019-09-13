package sslclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {
	
	public static void main(String[] args) throws UnknownHostException, IOException, NoSuchAlgorithmException, KeyManagementException {
        int port = 10790;
        String host = "localhost";
        
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, null, null);
        
        SSLSocketFactory factory = context.getSocketFactory();
        String [] suites = factory.getSupportedCipherSuites();
        SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
        socket.setEnabledCipherSuites(suites);
        System.out.println(socket.getSession().getCipherSuite());
        
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner scan = new Scanner(System.in);
    	
        while (true) {
            System.out.print("> ");
            
            if(scan.hasNext()) {
            	bw.write(scan.nextLine() + "\r\n");
            	bw.flush();
            }
             
            String fromserver = br.readLine();
            
            if (fromserver != null) {
            	System.out.println("server> " + fromserver);
            	if (fromserver.compareTo("fin") == 0) break;
            }
            else break;
        }
        scan.close();
        System.out.println("system Exit");
    }
 
}
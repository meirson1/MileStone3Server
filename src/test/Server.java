package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

	public interface ClientHandler {
		// define...
		void handleClient(InputStream inFromClient, OutputStream outToClient);

		void close();
	}

	volatile boolean stop;

	public Server() {
		stop = false;
	}


	private void startServer(int port, ClientHandler ch) {
		// implement here the server...
		try {
			ServerSocket server = new ServerSocket(port);
			server.setSoTimeout(1000);
			while (!stop) {
				try {
					Socket client = server.accept();
					ch.handleClient(client.getInputStream(), client.getOutputStream());
					ch.close();
					client.close();
				} catch (SocketTimeoutException e) {
					stop();
				}
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// runs the server in its own thread
	public void start(int port, ClientHandler ch) {
		new Thread(() -> startServer(port, ch)).start();
	}

	public void stop() {
		stop = true;
	}
}

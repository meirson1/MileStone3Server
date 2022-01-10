package test;


import test.Commands.DefaultIO;
import test.Server.ClientHandler;
import java.io.IOException;

import java.io.*;
import java.util.Scanner;

public class AnomalyDetectionHandler implements ClientHandler {


	SocketIO socketIO;

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) {
		socketIO = new SocketIO(inFromClient, outToClient);
		CLI cli = new CLI(socketIO);
		cli.start();
	}

	@Override
	public void close() {
		socketIO.close();
	}

	public class SocketIO implements DefaultIO {

		private BufferedReader FromClient;
		private PrintWriter ToClient;

		public SocketIO(InputStream inFromClient, OutputStream outToClient) {
			this.FromClient = new BufferedReader(new InputStreamReader(inFromClient));
			this.ToClient = new PrintWriter(outToClient, true);
		}

		@Override
		public String readText() {
			try {
				return FromClient.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void write(String text) {
			ToClient.flush();
			ToClient.print(text);
		}

		@Override
		public float readVal() {
			try {
				return Float.parseFloat(FromClient.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 1;
		}

		@Override
		public void write(float val) {
			ToClient.flush();
			ToClient.print(val);
		}

		public void close() {
			try {
				FromClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ToClient.close();
		}
	}
}

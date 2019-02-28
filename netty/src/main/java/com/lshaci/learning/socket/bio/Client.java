package com.lshaci.learning.socket.bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		try (
				// 连接服务端
				Socket socket = new Socket("127.0.0.1", 8765);
				
				InputStream in = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os, true);
		) {
			// 向服务端发送消息
			pw.println("服务器你好");
			// 接收服务端返回的消息
			String res = br.readLine();
			System.err.println("接收到服务器消息: " + res);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

package com.lshaci.learning.socket.bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		try (
				// 创建socket服务端
				ServerSocket server = new ServerSocket(8765);
				// 等待客户端连接
				Socket socket = server.accept();
		) {
			// 新建线程处理客服端信息
			new Thread(() -> {
				try (
						InputStream in = socket.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						OutputStream os = socket.getOutputStream();
						PrintWriter pw = new PrintWriter(os, true);
				) {
					while (true) {
						String message = br.readLine();
						if (message == null) {
							break;
						}
						System.err.println("接收到客户端消息: " + message);
						pw.println("客户端你好");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			synchronized (Server.class) {
				Server.class.wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}

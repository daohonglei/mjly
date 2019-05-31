package com.producer.socketaio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AioServer {
	//线程池
	private ExecutorService executorService;
	//线程组
	private AsynchronousChannelGroup threadGroup;
	//服务器通道
	public AsynchronousServerSocketChannel asynchronousServerSocketChannel;

	@Value("${socket.aio.port}")
	private int port;
	
	@Autowired
	private ServerCompletionHandler serverCompletionHandler;
	
	@Bean
	public AioServer getAioServer(){
		try {
			//创建一个缓存池
			executorService = Executors.newCachedThreadPool();
			//创建线程组
			threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
			//创建服务器通道
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open(threadGroup);
			//进行绑定
			asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("server start , port : " + port);
			//进行阻塞
			//asynchronousServerSocketChannel.accept(this, new ServerCompletionHandler());
			asynchronousServerSocketChannel.accept(this, serverCompletionHandler);

			//一直阻塞 不让服务器停止
			while(true){
				Thread.sleep(Integer.MAX_VALUE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new AioServer();
	}
}

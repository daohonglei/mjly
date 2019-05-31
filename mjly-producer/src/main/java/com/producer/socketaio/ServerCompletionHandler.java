package com.producer.socketaio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {
	
	@Autowired
	private DefaultMQProducer defaultMQProducer;

	@Override
	public void completed(AsynchronousSocketChannel asc, AioServer attachment) {
		//当有下一个客户端接入的时候 直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
		attachment.asynchronousServerSocketChannel.accept(attachment, this);
		read(asc);
	}

	private void read(final AsynchronousSocketChannel asc) {
		//读取数据
		ByteBuffer buf = ByteBuffer.allocate(1024);
		asc.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer resultSize, ByteBuffer attachment) {
				try {
					//进行读取之后,重置标识位
					attachment.flip();
					//获得读取的字节数
					System.out.println("Server -> " + "收到客户端的数据长度为:" + resultSize);
					//获取读取的数据
					Message message = new Message("demo-topic", "demo-tag", attachment.array());
					defaultMQProducer.send(message);
					String resultData = new String(attachment.array()).trim();
					System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
					String response = "服务器响应, 收到了客户端发来的数据: " + resultData;
					write(asc, response);
				} catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
					e.printStackTrace();
				}							
			}
			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				exc.printStackTrace();
			}
		});
	}
	
	private void write(AsynchronousSocketChannel asc, String response) {
		try {
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.put(response.getBytes());
			buf.flip();
			asc.write(buf).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void failed(Throwable exc, AioServer attachment) {
		exc.printStackTrace();
	}
}

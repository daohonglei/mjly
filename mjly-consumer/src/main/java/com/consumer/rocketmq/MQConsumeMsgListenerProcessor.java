package com.consumer.rocketmq;

import java.sql.SQLException;
import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.consumer.dao.MysqlTest;
import com.consumer.dao.util.ConnectionList;



@Component
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {
	@Autowired
	private ConnectionList connectionList;

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
      for (MessageExt msg : msgs) {
    	  try {
    		  System.out.println("消费者消费数据:"+new String(msg.getBody()));
			  new MysqlTest().test(new String(msg.getBody()), connectionList.getConnectionUtil().getConnection());
		  } catch (SQLException e) {
			  e.printStackTrace();
			  return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		  }
      }
      return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
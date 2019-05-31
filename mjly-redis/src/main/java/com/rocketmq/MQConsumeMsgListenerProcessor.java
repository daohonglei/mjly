package com.rocketmq;

import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redis.bean.User;
import com.redis.dao.UserDao;


@Component
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {
	@Autowired
	private UserDao userDao;
	Long i=0L;
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
      for (MessageExt msg : msgs) {
    	  try {
    		  System.out.println("消费者消费数据:"+new String(msg.getBody()));
			  userDao.save(new User(i++,new String(msg.getBody())),24*60*60L);
		   } catch (Exception e) {
			   e.printStackTrace();
			   return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		   }
      }
      return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
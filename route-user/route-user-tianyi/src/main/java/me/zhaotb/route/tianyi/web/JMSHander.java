package me.zhaotb.route.tianyi.web;

import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class JMSHander { 

	private static Logger logger = Logger.getLogger("JMSHander");

	@Autowired
	protected JmsTemplate jmsTemplate;
	
	public JMSHander() {
	}

	/**
	 * 将信息发送到tk指定的队列中，等待其他web来获取
	 * 
	 * @param tk
	 *            设置指定JMSCorrelationID的值，便于筛选
	 * @param rID
	 *            RSESSIONID(全局会话ID)
	 * @throws JMSException
	 */
	public void sendRID(String tk, String rID) throws JMSException {
		jmsTemplate.send(new TextMessageCreator(rID, tk));
		logger.info("发送RID："+rID);
	}

	/**
	 * 通过tk在队列中取出信息
	 * @param tk  选择器的JMSCorrelationID的值
	 * @return
	 * @throws JMSException
	 */
	public String recevieRID(String tk) throws JMSException {
		logger.info("接收消息！");
		return ((TextMessage)jmsTemplate.receiveSelected("JMSCorrelationID='"+tk+"'")).getText();
	}
	
	private class TextMessageCreator implements MessageCreator{
		String text;
		String id;
		public TextMessageCreator(String text, String id) {
			this.text = text;
			this.id = id;
		}
		public Message createMessage(Session session) throws JMSException {
			TextMessage message = session.createTextMessage(text);
			message.setJMSCorrelationID(id);
			return message;
		}
		
	}

}

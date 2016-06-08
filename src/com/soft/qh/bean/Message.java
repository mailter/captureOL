package com.soft.qh.bean;
/**
 * 消息类
 * @author lujf
 *
 */
public class Message {
	private MessageHead messageHead;
	private MessageBody messageBody;
	public MessageHead getMessageHead() {
		return messageHead;
	}
	public void setMessageHead(MessageHead messageHead) {
		this.messageHead = messageHead;
	}
	public MessageBody getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(MessageBody messageBody) {
		this.messageBody = messageBody;
	}
	
}

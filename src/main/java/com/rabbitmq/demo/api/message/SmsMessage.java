package com.rabbitmq.demo.api.message;

public class SmsMessage extends BaseMessage {

	private static final long serialVersionUID = 1L;

	private String phoneNumber;
	private String content;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SmsMessage [phoneNumber=" + phoneNumber + ", content=" + content + ", retryCount=" + retryCount + "]";
	}
}

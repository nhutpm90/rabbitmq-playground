package com.rabbitmq.demo.api.message;

import java.io.Serializable;

public abstract class BaseMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	protected int retryCount;

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public void increaseRetryCount() {
		this.retryCount++;
	}

}

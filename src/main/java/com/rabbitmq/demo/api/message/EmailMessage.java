package com.rabbitmq.demo.api.message;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EmailMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String email;
	private List<String> cc;
	private String template;

	private Map<String, String> params;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "EmailMessage [email=" + email + ", cc=" + cc + ", template=" + template + ", params=" + params + "]";
	}
}

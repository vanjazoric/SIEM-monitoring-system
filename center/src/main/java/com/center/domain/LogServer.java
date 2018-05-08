package com.center.domain;

import java.util.Date;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs")
@TypeAlias("log_server")
public class LogServer extends Log {

	private String clientIp;
	private String logHost;
	private String messageId;
	private String method;
	private String resourceRequest;
	private int httpStatus;
	private int sizeOfReturnedObj;

	
	
	public LogServer() {
		super();
	}

	public LogServer(String id, Date timeStamp, String clientIp, String logHost,
			String method, String messageId, String resourceRequest,
			int httpStatus, int sizeOfReturnedObj, Agent agent) {
		super(id, timeStamp, agent);
		this.clientIp = clientIp;
		this.logHost = logHost;
		this.messageId = messageId;
		this.method = method;
		this.resourceRequest = resourceRequest;
		this.httpStatus = httpStatus;
		this.sizeOfReturnedObj = sizeOfReturnedObj;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getLogHost() {
		return logHost;
	}

	public void setLogHost(String logHost) {
		this.logHost = logHost;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public int getSizeOfReturnedObj() {
		return sizeOfReturnedObj;
	}

	public void setSizeOfReturnedObj(int sizeOfReturnedObj) {
		this.sizeOfReturnedObj = sizeOfReturnedObj;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getResourceRequest() {
		return resourceRequest;
	}

	public void setResourceRequest(String resourceResource) {
		this.resourceRequest = resourceResource;
	}

	@Override
	public String toString() {
		return "LogServer [clientIp=" + clientIp + ", logHost=" + logHost
				+ ", messageId=" + messageId + ", method=" + method
				+ ", resourceRequest=" + resourceRequest + ", httpStatus="
				+ httpStatus + ", sizeOfReturnedObj=" + sizeOfReturnedObj + "]";
	}

}
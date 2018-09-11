package com.cashregister.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Advice {

	private HttpStatus status;
	private int statusCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String system;
	private String message;
	private List<SubAdvice> subErrors;

	private Advice() {
		timestamp = LocalDateTime.now();
	}

	public Advice(HttpStatus status) {
		this();
		this.status = status;
		this.subErrors = new ArrayList<>();
	}

	public Advice(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.system = "Unexpected error";
		this.message = ex.getLocalizedMessage();
		this.subErrors = new ArrayList<>();
	}

	public Advice(HttpStatus status, int statusCode, String message, Throwable ex) {
		this();
		this.status = status;
		this.system = message;
		this.message = ex.getLocalizedMessage();
		this.statusCode = statusCode;
		this.subErrors = new ArrayList<>();
	}

	public List<SubAdvice> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(SubAdvice subErrors) {
		this.subErrors.add(subErrors);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getSystem() {
		return system;
	}

	public String getMessage() {
		return message;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	class SubAdvice {

		private String subexceptionMessage;
		private String subexceptionAditionalMessage;

		public SubAdvice(String subexceptionMessage, String subexceptionAditionalMessage) {
			this.subexceptionAditionalMessage = subexceptionAditionalMessage;
			this.subexceptionMessage = subexceptionMessage;
		}

		public String getSubexceptionMessage() {
			return subexceptionMessage;
		}

		public String getSubexceptionAditionalMessage() {
			return subexceptionAditionalMessage;
		}

		public void setSubexceptionMessage(String subexceptionMessage) {
			this.subexceptionMessage = subexceptionMessage;
		}

		public void setSubexceptionAditionalMessage(String subexceptionAditionalMessage) {
			this.subexceptionAditionalMessage = subexceptionAditionalMessage;
		}

	}

}

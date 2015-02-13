package io.pelle.mango.server.api.webhook;

public class WebhookErrorResponse {

	private String error;
	
	public WebhookErrorResponse(String error) {
		super();
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
}

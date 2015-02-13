package io.pelle.mango.server.api.webhook;

@SuppressWarnings("serial")
public class WebhookException extends RuntimeException {

	public WebhookException() {
		super();
	}

	public WebhookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WebhookException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebhookException(String message) {
		super(message);
	}

	public WebhookException(Throwable cause) {
		super(cause);
	}

}

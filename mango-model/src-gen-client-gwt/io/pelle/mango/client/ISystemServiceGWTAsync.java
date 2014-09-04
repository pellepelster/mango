package io.pelle.mango.client;

public interface ISystemServiceGWTAsync {

	
	void getSystemInformation(com.google.gwt.user.client.rpc.AsyncCallback<Systeminformation> callback
	)
	;
	
	void getLog(com.google.gwt.user.client.rpc.AsyncCallback<LogEntry> callback
	)
	;
}

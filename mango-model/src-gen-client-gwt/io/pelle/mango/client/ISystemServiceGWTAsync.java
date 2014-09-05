package io.pelle.mango.client;

public interface ISystemServiceGWTAsync {

	
	void getSystemInformation(com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.Systeminformation> callback
	)
	;
	
	void getLog(com.google.gwt.user.client.rpc.AsyncCallback<io.pelle.mango.client.LogEntry> callback
	)
	;
}

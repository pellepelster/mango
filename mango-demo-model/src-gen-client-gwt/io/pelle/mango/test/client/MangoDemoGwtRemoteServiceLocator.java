package io.pelle.mango.test.client;

public class MangoDemoGwtRemoteServiceLocator implements IMangoDemoGwtRemoteServiceLocator {

    private static final MangoDemoGwtRemoteServiceLocator instance = new MangoDemoGwtRemoteServiceLocator();

    private MangoDemoGwtRemoteServiceLocator() {
    }

    public static MangoDemoGwtRemoteServiceLocator getInstance() {
        return instance;
    }
    
   	private String remoteBaseUrl = null;
	
	public void setRemoteBaseUrl(String remoteBaseUrl) {
		this.remoteBaseUrl = remoteBaseUrl;
	}
    
    public String getModuleBaseUrl()
    {
	    if (remoteBaseUrl != null) {
			return remoteBaseUrl;
	    }
	    else {
	    	return com.google.gwt.core.client.GWT.getModuleBaseURL() + "../remote/rpc"; 
	    }
    } 

	public io.pelle.mango.test.client.IRestTestGWTAsync getRestTest() {
		
		final io.pelle.mango.test.client.IRestTestGWTAsync service = (io.pelle.mango.test.client.IRestTestGWTAsync) com.google.gwt.core.client.GWT.create(io.pelle.mango.test.client.IRestTestGWT.class);
		
		com.google.gwt.user.client.rpc.ServiceDefTarget formEndpoint = (com.google.gwt.user.client.rpc.ServiceDefTarget) service;
		formEndpoint.setServiceEntryPoint(getModuleBaseUrl() + "/RestTest");
		formEndpoint.setRpcRequestBuilder(io.pelle.mango.client.base.MangoClientBase.getInstance().getRpcRequestBuilder());
		
		return service;
	}
}

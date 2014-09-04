package io.pelle.mango.client;

public class MangoGwtRemoteServiceLocator implements IMangoGwtRemoteServiceLocator {

    private static final MangoGwtRemoteServiceLocator instance = new MangoGwtRemoteServiceLocator();

    private MangoGwtRemoteServiceLocator() {
    }

    public static MangoGwtRemoteServiceLocator getInstance() {
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

	public io.pelle.mango.client.IHierachicalServiceGWTAsync getHierachicalService() {
		
		final io.pelle.mango.client.IHierachicalServiceGWTAsync service = (io.pelle.mango.client.IHierachicalServiceGWTAsync) com.google.gwt.core.client.GWT.create(io.pelle.mango.client.IHierachicalServiceGWT.class);
		
		com.google.gwt.user.client.rpc.ServiceDefTarget formEndpoint = (com.google.gwt.user.client.rpc.ServiceDefTarget) service;
		formEndpoint.setServiceEntryPoint(getModuleBaseUrl() + "/HierachicalService");
		formEndpoint.setRpcRequestBuilder(io.pelle.mango.client.base.MangoClientBase.getInstance().getRpcRequestBuilder());
		
		return service;
	}
	public io.pelle.mango.client.ISystemServiceGWTAsync getSystemService() {
		
		final io.pelle.mango.client.ISystemServiceGWTAsync service = (io.pelle.mango.client.ISystemServiceGWTAsync) com.google.gwt.core.client.GWT.create(io.pelle.mango.client.ISystemServiceGWT.class);
		
		com.google.gwt.user.client.rpc.ServiceDefTarget formEndpoint = (com.google.gwt.user.client.rpc.ServiceDefTarget) service;
		formEndpoint.setServiceEntryPoint(getModuleBaseUrl() + "/SystemService");
		formEndpoint.setRpcRequestBuilder(io.pelle.mango.client.base.MangoClientBase.getInstance().getRpcRequestBuilder());
		
		return service;
	}
	public io.pelle.mango.client.IBaseEntityServiceGWTAsync getBaseEntityService() {
		
		final io.pelle.mango.client.IBaseEntityServiceGWTAsync service = (io.pelle.mango.client.IBaseEntityServiceGWTAsync) com.google.gwt.core.client.GWT.create(io.pelle.mango.client.IBaseEntityServiceGWT.class);
		
		com.google.gwt.user.client.rpc.ServiceDefTarget formEndpoint = (com.google.gwt.user.client.rpc.ServiceDefTarget) service;
		formEndpoint.setServiceEntryPoint(getModuleBaseUrl() + "/BaseEntityService");
		formEndpoint.setRpcRequestBuilder(io.pelle.mango.client.base.MangoClientBase.getInstance().getRpcRequestBuilder());
		
		return service;
	}
}

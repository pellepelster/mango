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

}

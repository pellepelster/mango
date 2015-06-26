package io.pelle.mango.server.system;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import io.pelle.mango.client.system.ISystemService;
import io.pelle.mango.client.system.Systeminformation;

public class SystemServiceImpl implements ISystemService {

	private static Logger LOG = Logger.getLogger(SystemServiceImpl.class);
	
	@Override
	public Systeminformation getSystemInformation() {
		
		Systeminformation result = new Systeminformation();
		
		try {
			result.setHostName(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			LOG.error(e);
		}
		
		return result;
	}

}

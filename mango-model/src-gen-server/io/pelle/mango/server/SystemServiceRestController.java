
package io.pelle.mango.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("systemservice")
public class SystemServiceRestController  {

	@org.springframework.beans.factory.annotation.Autowired
	private io.pelle.mango.client.ISystemService systemService;
	
	public void setSystemService(io.pelle.mango.client.ISystemService systemService)
	{
		this.systemService = systemService;
	}

	@RequestMapping(value = "getsysteminformation/")
	public  io.pelle.mango.client.Systeminformation
	 getSystemInformation() {
		return this.systemService.getSystemInformation();
	}
	@RequestMapping(value = "getlog/")
	public  io.pelle.mango.client.LogEntry
	 getLog() {
		return this.systemService.getLog();
	}
}

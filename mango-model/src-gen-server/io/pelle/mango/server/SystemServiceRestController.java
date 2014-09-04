
package io.pelle.mango.server;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("systemservice")
public class SystemServiceRestController  {

	@org.springframework.beans.factory.annotation.Autowired
	private io.pelle.mango.client.ISystemService systemService;
	
	public void setSystemService(io.pelle.mango.client.ISystemService systemService)
	{
		this.systemService = systemService;
	}

	
	
	
	

}

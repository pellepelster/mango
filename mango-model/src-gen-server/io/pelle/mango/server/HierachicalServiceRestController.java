
package io.pelle.mango.server;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("hierachicalservice")
public class HierachicalServiceRestController  {

	@org.springframework.beans.factory.annotation.Autowired
	private io.pelle.mango.client.IHierachicalService hierachicalService;
	
	public void setHierachicalService(io.pelle.mango.client.IHierachicalService hierachicalService)
	{
		this.hierachicalService = hierachicalService;
	}

	
	
	
	
	
	
	
	
	
	

}

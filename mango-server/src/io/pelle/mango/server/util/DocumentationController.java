package io.pelle.mango.server.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocumentationController {

	@RequestMapping("/documentation")
	public String documentation() {
		return "dsds";
	}

}

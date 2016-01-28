package io.pelle.mango.demo.server.test;

import io.pelle.mango.server.MangoWebApplicationInitializer;

public class MangoDemoWebApplicationInitializer extends MangoWebApplicationInitializer {

	public MangoDemoWebApplicationInitializer() {
		super(MangoDemoWebApplicationContext.class);
	}

}
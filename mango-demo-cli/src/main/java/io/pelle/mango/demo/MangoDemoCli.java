package io.pelle.mango.demo;

import io.pelle.mango.cli.BaseMangoCli;

public class MangoDemoCli {

	public static void main(String[] args) {

		BaseMangoCli.create(MangoDemoCliApplicationContext.class, args).getApplicationContext().getBean(MangoDemoBean.class).execute();
	}

}

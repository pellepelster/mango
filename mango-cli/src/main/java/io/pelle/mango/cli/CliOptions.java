package io.pelle.mango.cli;

import com.beust.jcommander.Parameter;

public class CliOptions {
 
  @Parameter(names = { "-verbose" }, description = "level of verbosity")
  private boolean verbose = false;
 
  @Parameter(names = "-url", description = "base url")
  private String url = "http://localhost:8080";
 
  @Parameter(names = "-debug", description = "Debug mode")
  private boolean debug = false;

public boolean isVerbose() {
	return verbose;
}

public void setVerbose(boolean verbose) {
	this.verbose = verbose;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

public boolean isDebug() {
	return debug;
}

public void setDebug(boolean debug) {
	this.debug = debug;
}
  
  
}

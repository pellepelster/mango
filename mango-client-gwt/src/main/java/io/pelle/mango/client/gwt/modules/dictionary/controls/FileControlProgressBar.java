package io.pelle.mango.client.gwt.modules.dictionary.controls;

import org.gwtbootstrap3.client.ui.Progress;
import org.gwtbootstrap3.client.ui.ProgressBar;

import gwtupload.client.HasProgress;

public class FileControlProgressBar extends Progress implements HasProgress {

	ProgressBar progressBar;

	public FileControlProgressBar() {

		progressBar = new ProgressBar();
		add(progressBar);
	}

	public void setProgress(long done, long total) {
		progressBar.setPercent(total / 100 * done);
	}
}

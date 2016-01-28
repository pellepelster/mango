package io.pelle.mango.client.gwt.modules.log;

import io.pelle.mango.client.gwt.modules.log.EndlessDataGrid.EndlessDataGridCallback;
import io.pelle.mango.client.log.LogEntryVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

import java.io.Serializable;
import java.util.List;

public class LogDataGridCallback extends EndlessDataGridCallback<LogEntryVO> {

	private final Serializable reference;

	private LogDataGridCallback(Serializable reference) {
		this.reference = reference;
	}

	public static LogDataGridCallback create(Serializable reference) {
		return new LogDataGridCallback(reference);
	}

	@Override
	void initalData(int pageSize) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getLogService().getLog(pageSize, reference, new BaseErrorAsyncCallback<List<LogEntryVO>>() {
			@Override
			public void onSuccess(List<LogEntryVO> result) {
				initialDataFinished(result);
			};
		});
	}

	@Override
	void onPageUp(LogEntryVO item, int pageSize) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getLogService().getLogAfter(item.getTimestamp(), pageSize, reference, new BaseErrorAsyncCallback<List<LogEntryVO>>() {
			@Override
			public void onSuccess(List<LogEntryVO> result) {
				pageUpFinished(result);
			};
		});
	}

	@Override
	void onPageDown(LogEntryVO item, int pageSize) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getLogService().getLogBefore(item.getTimestamp(), pageSize, reference, new BaseErrorAsyncCallback<List<LogEntryVO>>() {
			@Override
			public void onSuccess(List<LogEntryVO> result) {
				pageDownFinished(result);
			};
		});
	}

}

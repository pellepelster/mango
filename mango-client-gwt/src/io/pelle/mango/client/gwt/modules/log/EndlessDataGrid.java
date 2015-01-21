package io.pelle.mango.client.gwt.modules.log;

import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.gwt.modules.dictionary.BaseDataGridResources;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;

public class EndlessDataGrid<T> extends DataGrid<T> {

	public static String NEW_ROW_STYLE = "endless-grid-new-row";

	private int CALLBACK_QUIET_PERIOD_DEFAULT = 200;

	private int callbackQuietPeriod = CALLBACK_QUIET_PERIOD_DEFAULT;

	private long lastCallbackInvocation = -1;

	private boolean callbackRunning = false;

	private List<RowStyles<T>> rowStyleList = new ArrayList<RowStyles<T>>();

	protected void setCallbackRunning(boolean running) {
		if (running) {
			lastCallbackInvocation = System.currentTimeMillis();
		}

		callbackRunning = running;
	}

	public static abstract class EndlessDataGridCallback<C> {

		abstract void initalData(int pageSize);

		abstract void onPageUp(C item, int pageSize);

		abstract void onPageDown(C item, int pageSize);

		protected void pageDownFinished(List<C> data) {
			dataGrid.setCallbackRunning(false);

			if (!data.isEmpty()) {
				initialDataFinished(data);
			}
		}

		protected void pageUpFinished(List<C> data) {
			dataGrid.setCallbackRunning(false);

			if (!data.isEmpty()) {

				if (data.size() < dataGrid.getPageSize()) {

					dataGrid.setNewData(data);

					List<C> currentList = dataGrid.getDataProvider().getList();

					List<C> fill = null;

					if (data.size() + currentList.size() <= dataGrid.getPageSize()) {
						fill = currentList;
					} else {
						fill = currentList.subList(data.size() - 1, currentList.size() - 1);
					}

					data.addAll(fill);
				}

				initialDataFinished(data);
			}
		}

		protected void initialDataFinished(List<C> data) {
			dataGrid.setCallbackRunning(false);
			dataGrid.getDataProvider().getList().clear();
			dataGrid.getDataProvider().getList().addAll(data);
		}

		private EndlessDataGrid<C> dataGrid;

		public void setDataGrid(EndlessDataGrid<C> dataGrid) {
			this.dataGrid = dataGrid;
		}
	}

	private EndlessDataGridCallback<T> dataGridCallback;

	private final ListDataProvider<T> dataProvider = new ListDataProvider<T>();

	private final List<T> newData = new ArrayList<T>();

	protected void setNewData(List<T> newData) {
		this.newData.clear();

		if (newData.size() < getPageSize()) {
			this.newData.addAll(newData);
		}
	}

	public ListDataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public ScrollPanel getScrollPanel() {
		HeaderPanel header = (HeaderPanel) getWidget();
		return (ScrollPanel) header.getContentWidget();
	}

	@Override
	public void setRowStyles(RowStyles<T> rowStyles) {
		addRowStyles(rowStyles);
	}

	public void addRowStyles(RowStyles<T> rowStyles) {
		rowStyleList.add(rowStyles);
	}

	private RowStyles<T> NEW_DATA_ROWSTYLE = new RowStyles<T>() {

		@Override
		public String getStyleNames(T row, int rowIndex) {

			String styleNames = "";

			if (newData.contains(row)) {
				newData.remove(row);
				styleNames += NEW_ROW_STYLE;
			}

			return styleNames;
		}
	};

	public EndlessDataGrid() {
		super(BaseCellTable.DEFAULT_PAGESIZE, (BaseDataGridResources) GWT.create(BaseDataGridResources.class));

		dataProvider.addDataDisplay(this);

		super.setRowStyles(new RowStyles<T>() {
			@Override
			public String getStyleNames(final T row, final int rowIndex) {
				Iterable<String> styles = Iterables.transform(rowStyleList, new Function<RowStyles<T>, String>() {
					@Override
					public String apply(RowStyles<T> input) {
						return input.getStyleNames(row, rowIndex);
					}
				});

				return Joiner.on(" ").join(styles);
			}
		});

		addRowStyles(NEW_DATA_ROWSTYLE);

	}

	public void setDataGridCallback(EndlessDataGridCallback<T> dataGridCallback) {
		this.dataGridCallback = dataGridCallback;
		dataGridCallback.setDataGrid(this);
		dataGridCallback.initalData(getPageSize());
		registerScrollHandler();
	}

	private void registerScrollHandler() {
		getScrollPanel().addDomHandler(new MouseWheelHandler() {

			@Override
			public void onMouseWheel(MouseWheelEvent event) {

				boolean fireCallback = !callbackRunning;

				if (lastCallbackInvocation > -1) {
					long quietTime = System.currentTimeMillis() - lastCallbackInvocation;
					fireCallback &= quietTime >= callbackQuietPeriod;
				}

				if (fireCallback) {
					if (getVisibleItems().isEmpty()) {
						setCallbackRunning(true);
						dataGridCallback.initalData(getPageSize());
					} else {
						if (event.getDeltaY() < 0) {
							if (getScrollPanel().getVerticalScrollPosition() == getScrollPanel().getMinimumVerticalScrollPosition()) {
								setCallbackRunning(true);
								dataGridCallback.onPageUp(getVisibleItems().get(0), getPageSize());
							}
						} else {
							if (getScrollPanel().getVerticalScrollPosition() == getScrollPanel().getMaximumVerticalScrollPosition()) {
								setCallbackRunning(true);
								dataGridCallback.onPageDown(getVisibleItems().get(getVisibleItems().size() - 1), getPageSize());
							}
						}
					}
				}
			}
		}, MouseWheelEvent.getType());

	}

	public void setCallbackQuietPeriod(int callbackQuietPeriod) {
		this.callbackQuietPeriod = callbackQuietPeriod;
	}

}

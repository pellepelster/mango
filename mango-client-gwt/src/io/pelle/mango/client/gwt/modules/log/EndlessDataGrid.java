package io.pelle.mango.client.gwt.modules.log;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;

public class EndlessDataGrid<T> extends DataGrid<T> {

	public static abstract class EndlessDataGridCallback<C> {

		abstract void initalGetData(int pageSize);

		abstract void getDataBefore(C item, int pageSize);

		abstract void getDataAfter(C item, int pageSize);

		protected void setData(List<C> data) {
			for (ListDataProvider<C> dataProvider : dataProviders) {

				if (!data.isEmpty()) {
					dataProvider.getList().clear();
					dataProvider.getList().addAll(data);
				}
			}
		}

		private List<ListDataProvider<C>> dataProviders = new ArrayList<ListDataProvider<C>>();

		protected void addDataProvider(ListDataProvider<C> dataProvider) {
			dataProviders.add(dataProvider);
		}

	}

	private EndlessDataGridCallback<T> dataGridCallback;

	private final ListDataProvider<T> dataProvider = new ListDataProvider<T>();

	public ScrollPanel getScrollPanel() {
		HeaderPanel header = (HeaderPanel) getWidget();
		return (ScrollPanel) header.getContentWidget();
	}

	public EndlessDataGrid() {
		super();
		dataProvider.addDataDisplay(this);

		getScrollPanel().addDomHandler(new MouseWheelHandler() {

			@Override
			public void onMouseWheel(MouseWheelEvent event) {

				if (getVisibleItems().isEmpty()) {
					dataGridCallback.initalGetData(getPageSize());
				} else {
					if (event.getDeltaY() < 0) {
						if (getScrollPanel().getVerticalScrollPosition() == getScrollPanel().getMinimumVerticalScrollPosition()) {
							dataGridCallback.getDataBefore(getVisibleItems().get(0), getPageSize());
						}
					} else {
						if (getScrollPanel().getVerticalScrollPosition() == getScrollPanel().getMaximumVerticalScrollPosition()) {
							dataGridCallback.getDataAfter(getVisibleItems().get(getVisibleItems().size() - 1), getPageSize());
						}
					}
				}
			}
		}, MouseWheelEvent.getType());
	}

	public void setDataGridCallback(EndlessDataGridCallback<T> dataGridCallback) {
		this.dataGridCallback = dataGridCallback;
		dataGridCallback.addDataProvider(dataProvider);
		dataGridCallback.initalGetData(getPageSize());
	}

}

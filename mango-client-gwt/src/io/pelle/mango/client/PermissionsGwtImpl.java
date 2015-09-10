
package io.pelle.mango.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.common.collect.Iterables;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.view.client.ListDataProvider;

import io.pelle.mango.client.base.modules.dictionary.IUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IBaseControlModel;
import io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel;
import io.pelle.mango.client.base.util.Predicates;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.gwt.ControlHandler;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.gwt.modules.dictionary.container.BaseListSelectionPopup;
import io.pelle.mango.client.gwt.utils.CustomGwtComposite;
import io.pelle.mango.client.security.MangoOperationVO;
import io.pelle.mango.client.security.MangoPermissionVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.container.IContainer;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

@CustomGwtComposite("permissions")
public class PermissionsGwtImpl implements IContainer<Panel>, ClickHandler, IUpdateListener {

	private Div panel = new Div();

	public PermissionsImpl container;

	private ListDataProvider<MangoPermissionVO> dataProvider;

	private List<MangoPermissionVO> availablePermissions = new ArrayList<MangoPermissionVO>();

	public PermissionsGwtImpl(PermissionsImpl container) {
		super();
		this.container = container;

		DataGrid<MangoPermissionVO> permissionsTable = new DataGrid<MangoPermissionVO>();
		permissionsTable.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		permissionsTable.setWidth("100%");

		dataProvider = new ListDataProvider<MangoPermissionVO>();
		dataProvider.addDataDisplay(permissionsTable);

		TextControlModel textControlModel = new TextControlModel(MangoClientWeb.getInstance().getMessages().permission(), MangoPermissionVO.PERMISSIONID);
		permissionsTable.addColumn(ControlHandler.getInstance().<MangoPermissionVO> createColumn(textControlModel));
		panel.add(permissionsTable);

		Cell<List<MangoOperationVO>> operations = new OperationsCell(availablePermissions);
		Column<MangoPermissionVO, List<MangoOperationVO>> column = new Column<MangoPermissionVO, List<MangoOperationVO>>(operations) {
			@Override
			public List<MangoOperationVO> getValue(MangoPermissionVO vo) {
				return vo.getOperations();
			}
		};

		permissionsTable.addColumn(column, new TextHeader("sss"));

		Button addButton = new Button(MangoClientWeb.getInstance().getMessages().addPermission());
		addButton.addClickHandler(this);
		panel.add(addButton);

		container.getAvailablePermissions(new BaseErrorAsyncCallback<List<MangoPermissionVO>>() {

			@Override
			public void onSuccess(List<MangoPermissionVO> result) {
				if (availablePermissions.isEmpty()) {
					availablePermissions.addAll(result);
				}

				onUpdate();

			}
		});

		container.addUpdateListener(this);
	}

	@Override
	public Panel getContainer() {
		return panel;
	}

	@Override
	public void onClick(ClickEvent event) {

		IBaseControlModel model = new TextControlModel("permission", MangoPermissionVO.PERMISSIONID);

		final BaseListSelectionPopup<MangoPermissionVO> popup = new BaseListSelectionPopup<MangoPermissionVO>(MangoClientWeb.getInstance().getMessages().selectPermissionToAdd(), Arrays.asList(new IBaseControlModel[] { model }),
				new SimpleCallback<MangoPermissionVO>() {

					@Override
					public void onCallback(MangoPermissionVO selectedPermission) {

						if (!Iterables.tryFind(dataProvider.getList(), Predicates.attributeEquals(MangoPermissionVO.PERMISSIONID, selectedPermission.getPermissionId())).isPresent()) {
							MangoPermissionVO mangoPermission = new MangoPermissionVO();
							mangoPermission.setPermissionId(selectedPermission.getPermissionId());

							dataProvider.getList().add(mangoPermission);
						}

					}
				});

		popup.show();

		if (!availablePermissions.isEmpty()) {
			popup.onSuccess(availablePermissions);
		} else {
			container.getAvailablePermissions(new BaseErrorAsyncCallback<List<MangoPermissionVO>>() {

				@Override
				public void onSuccess(List<MangoPermissionVO> result) {

					if (availablePermissions.isEmpty()) {
						availablePermissions.addAll(result);
					}

					popup.onSuccess(result);
				}
			});
		}
	}

	@Override
	public void onUpdate() {
		dataProvider.setList(container.getPermissions());
	}

}

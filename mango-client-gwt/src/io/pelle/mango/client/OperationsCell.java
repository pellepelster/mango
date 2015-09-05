package io.pelle.mango.client;

import java.util.List;

import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

import io.pelle.mango.client.base.util.Predicates;
import io.pelle.mango.client.gwt.utils.HorizontalSpacer;
import io.pelle.mango.client.security.MangoOperationVO;
import io.pelle.mango.client.security.MangoPermissionVO;

public class OperationsCell extends AbstractEditableCell<List<MangoOperationVO>, List<MangoOperationVO>> {

	private List<MangoPermissionVO> availablePermissions;

	public OperationsCell(List<MangoPermissionVO> availablePermissions) {
		this.availablePermissions = availablePermissions;
	}

	@Override
	public boolean isEditing(Context context, Element parent, List<MangoOperationVO> value) {
		return false;
	}

	@Override
	public void render(Context context, List<MangoOperationVO> value, SafeHtmlBuilder sb) {

		final MangoPermissionVO key = (MangoPermissionVO) context.getKey();

		List<MangoOperationVO> viewData = getViewData(key);

		Div div = new Div();

		if (viewData != null && viewData.equals(value)) {
			clearViewData(key);
			viewData = null;
		}

		MangoPermissionVO availablePermission = Iterables.find(availablePermissions, Predicates.<MangoPermissionVO> attributeEquals(MangoPermissionVO.PERMISSIONID, key.getPermissionId()));

		for (final MangoOperationVO availableOperation : availablePermission.getOperations()) {

			final Predicate<MangoOperationVO> predicate = Predicates.attributeEquals(MangoOperationVO.OPERATIONID, availableOperation.getOperationId());
			boolean checked = Iterables.contains(key.getOperations(), predicate);

			CheckBox checkBox = new CheckBox(availableOperation.getOperationId());
			checkBox.setValue(checked);
			checkBox.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);

			checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

				@Override
				public void onValueChange(ValueChangeEvent<Boolean> event) {

					boolean hasOperation = Iterables.contains(key.getOperations(), predicate);

					if (event.getValue()) {

						if (!hasOperation) {
							MangoOperationVO operation = new MangoOperationVO();
							operation.setOperationId(availableOperation.getOperationId());
							key.getOperations().add(operation);
						}
					} else {
						Iterables.removeIf(key.getOperations(), predicate);
					}

				}
			});

			HorizontalSpacer.adapt(checkBox);
			div.add(checkBox);
		}

		sb.append(SafeHtmlUtils.fromSafeConstant(div.getElement().getString()));
	}

}

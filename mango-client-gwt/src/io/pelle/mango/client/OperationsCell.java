package io.pelle.mango.client;

import java.util.List;

import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.html.Div;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

import io.pelle.mango.client.base.util.Predicates;
import io.pelle.mango.client.gwt.utils.HorizontalSpacer;
import io.pelle.mango.client.security.MangoOperationVO;
import io.pelle.mango.client.security.MangoPermissionVO;

public class OperationsCell extends AbstractEditableCell<List<MangoOperationVO>, List<MangoOperationVO>> {

	private List<MangoPermissionVO> availablePermissions;

	public OperationsCell(List<MangoPermissionVO> availablePermissions) {
		super(BrowserEvents.CHANGE, BrowserEvents.KEYDOWN);
		this.availablePermissions = availablePermissions;
	}

	@Override
	public boolean isEditing(Context context, Element parent, List<MangoOperationVO> value) {
		return false;
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, List<MangoOperationVO> value, NativeEvent event, ValueUpdater<List<MangoOperationVO>> valueUpdater) {

		final MangoPermissionVO key = (MangoPermissionVO) context.getKey();

		String type = event.getType();

		Element element = event.getEventTarget().cast();

		if (element instanceof InputElement) {

			InputElement inputElement = (InputElement) element;
			String operationId = inputElement.getName();

			final Predicate<MangoOperationVO> operationPredicate = Predicates.attributeEquals(MangoOperationVO.OPERATIONID, operationId);

			boolean hasOperation = Iterables.tryFind(key.getOperations(), operationPredicate).isPresent();

			if (inputElement.isChecked()) {
				if (!hasOperation) {
					MangoOperationVO operation = new MangoOperationVO();
					operation.setOperationId(operationId);
					key.getOperations().add(operation);
				}
			} else {
				Iterables.removeIf(key.getOperations(), operationPredicate);
			}

		}
		boolean enterPressed = BrowserEvents.KEYDOWN.equals(type) && event.getKeyCode() == KeyCodes.KEY_ENTER;

		if (BrowserEvents.CHANGE.equals(type) || enterPressed) {

			// InputElement input = parent.getFirstChild().cast();
			// Boolean isChecked = input.isChecked();

			/*
			 * Toggle the value if the enter key was pressed and the cell
			 * handles selection or doesn't depend on selection. If the cell
			 * depends on selection but doesn't handle selection, then ignore
			 * the enter key and let the SelectionEventManager determine which
			 * keys will trigger a change.
			 */
			// if (enterPressed && (handlesSelection() ||
			// !dependsOnSelection())) {
			// isChecked = !isChecked;
			// input.setChecked(isChecked);
			// }

			/*
			 * Save the new value. However, if the cell depends on the
			 * selection, then do not save the value because we can get into an
			 * inconsistent state.
			 */
			// if (value != isChecked && !dependsOnSelection()) {
			// setViewData(context.getKey(), isChecked);
			// } else {
			// clearViewData(context.getKey());
			// }
			//
			// if (valueUpdater != null) {
			// valueUpdater.update(isChecked);
			// }
		}
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

			final Predicate<MangoOperationVO> operationPredicate = Predicates.attributeEquals(MangoOperationVO.OPERATIONID, availableOperation.getOperationId());

			boolean checked = Iterables.tryFind(key.getOperations(), operationPredicate).isPresent();

			final CheckBox checkBox = new CheckBox(availableOperation.getOperationId());
			checkBox.setName(availableOperation.getOperationId());

			checkBox.setValue(checked);
			checkBox.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);

			HorizontalSpacer.adapt(checkBox);
			div.add(checkBox);
		}

		sb.append(SafeHtmlUtils.fromSafeConstant(div.getElement().getString()));
	}

}

package io.pelle.mango.client.gwt.modules.dictionary.container;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.base.util.SimpleCallback;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.gwt.modules.dictionary.BaseCellTable;
import io.pelle.mango.client.gwt.modules.hierarchical.HierarchicalTree;
import io.pelle.mango.client.hierarchy.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.web.MangoClientWeb;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class HierarchicalVOSelectionPopup extends BaseVOSelectionPopup<IHierarchicalVO> {

	private HierarchicalTree hierarchicalTree;

	private HierarchicalConfigurationVO hierarchicalConfiguration;

	private DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO;

	private HierarchicalVOSelectionPopup(HierarchicalConfigurationVO hierarchicalConfiguration, IHierarchicalControlModel hierarchicalControlModel) {
		super(MangoClientWeb.MESSAGES.hierarchicalParent(), null);

		this.hierarchicalConfiguration = hierarchicalConfiguration;
	}

	public static void create(final IHierarchicalControlModel hierarchicalControlModel, final AsyncCallback<HierarchicalVOSelectionPopup> asyncCallback) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getHierarchicalService().getConfigurationById(hierarchicalControlModel.getHierarchicalId(), new AsyncCallback<HierarchicalConfigurationVO>() {

			@Override
			public void onFailure(Throwable caught) {
				asyncCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(HierarchicalConfigurationVO result) {
				asyncCallback.onSuccess(new HierarchicalVOSelectionPopup(result, hierarchicalControlModel));
			}
		});

	}

	@Override
	protected Widget createDialogBoxContent() {

		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		scrollPanel.setWidth("100%");

		hierarchicalTree = new HierarchicalTree(hierarchicalConfiguration, false, new SimpleCallback<DictionaryHierarchicalNodeVO>() {

			@Override
			public void onCallback(DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO) {
				HierarchicalVOSelectionPopup.this.dictionaryHierarchicalNodeVO = dictionaryHierarchicalNodeVO;

			}
		});

		hierarchicalTree.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);
		hierarchicalTree.setWidth("100%");
		scrollPanel.add(hierarchicalTree);

		return scrollPanel;
	}

	@Override
	protected void getCurrentSelection(final AsyncCallback<IHierarchicalVO> asyncCallback) {
		MangoClientWeb.getInstance().getRemoteServiceLocator().getBaseEntityService().read(dictionaryHierarchicalNodeVO.getVoId(), dictionaryHierarchicalNodeVO.getVoClassName(), new AsyncCallback<IBaseVO>() {
			@Override
			public void onFailure(Throwable caught) {
				asyncCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(IBaseVO result) {
				if (result instanceof IHierarchicalVO) {
					asyncCallback.onSuccess((IHierarchicalVO) result);
				} else {
					throw new RuntimeException("unsupported vo type '" + result.getClass().getName() + "'");
				}
			}
		});
	}

}

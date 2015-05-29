package io.pelle.mango.client.web.modules.hierarchical;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.modules.dictionary.DictionaryContext;
import io.pelle.mango.client.base.modules.dictionary.controls.BaseButton;
import io.pelle.mango.client.base.modules.dictionary.controls.IButton;
import io.pelle.mango.client.base.modules.dictionary.editor.IDictionaryEditor;
import io.pelle.mango.client.base.modules.dictionary.editor.IEditorUpdateListener;
import io.pelle.mango.client.base.modules.dictionary.hooks.BaseEditorHook;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;

public class HierarchicalEditorHook<VOType extends IBaseVO> extends BaseEditorHook<VOType> {
	private List<IDictionaryModel> childDictionaries;

	private static final String HIERARCHY_ADD_CHILD_BUTTON_DEBUG_ID = "HIERARCHY_ADD_CHILD_BUTTON_DEBUG_ID";

	public HierarchicalEditorHook(List<IDictionaryModel> childDictionaries) {
		super();
		this.childDictionaries = childDictionaries;
	}

	@Override
	public List<IButton> getEditorButtons(final IDictionaryEditor<VOType> dictionaryEditor) {
		final MenuBar hierarchicalMenuOtions = new MenuBar(true);

		for (final IDictionaryModel childDictionary : this.childDictionaries) {
			MenuItem menuItem = new MenuItem(SafeHtmlUtils.fromSafeConstant(DictionaryUtil.getDictionaryAdd(childDictionary)));
			menuItem.setScheduledCommand(new Command() {
				@Override
				public void execute() {
					HashMap<String, Object> parameters = CollectionUtils.getMap(IHierarchicalVO.PARENT_CLASS_FIELD_NAME, dictionaryEditor.getVO().getClass().getName(), IHierarchicalVO.PARENT_ID_FIELD_NAME,
							dictionaryEditor.getVO().getOid());

					DictionaryEditorModuleFactory.openEditor(childDictionary.getName(), parameters);
					hierarchicalMenuOtions.closeAllChildren(false);
				}
			});
			hierarchicalMenuOtions.addItem(menuItem);
		}

		final PopupPanel hierarchicalMenuWrapper = new PopupPanel(true);

		hierarchicalMenuWrapper.setTitle(MangoClientWeb.MESSAGES.addChildren());
		hierarchicalMenuWrapper.add(hierarchicalMenuOtions);

		final IButton hierarchicalButton = new BaseButton(MangoClientWeb.RESOURCES.hierarchy(), MangoClientWeb.MESSAGES.addChildren(), HIERARCHY_ADD_CHILD_BUTTON_DEBUG_ID) {
			@Override
			public void onClick(final ClickEvent event, DictionaryContext dictionaryContext) {
				hierarchicalMenuWrapper.setPopupPositionAndShow(new PositionCallback() {
					@Override
					public void setPosition(int offsetWidth, int offsetHeight) {
						hierarchicalMenuWrapper.setPopupPosition(event.getClientX(), event.getClientY());
					}
				});
			}
		};

		dictionaryEditor.addUpdateListener(new IEditorUpdateListener() {

			@Override
			public void onUpdate() {
				hierarchicalButton.setEnabled(!dictionaryEditor.getVO().isNew());
			}
		});

		return Lists.newArrayList(hierarchicalButton);
	}

}

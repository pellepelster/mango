
package io.pelle.mango.test.client.testdictionary1.dictionaryeditor1;

@java.lang.SuppressWarnings("all")
public class DictionaryEditor1 extends io.pelle.mango.client.base.modules.dictionary.model.editor.EditorModel<io.pelle.mango.test.client.Entity1VO> {

	private class RootComposite extends io.pelle.mango.client.base.modules.dictionary.model.containers.CompositeModel {

		
			public io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel
			 TEXTCONTROL1 = new io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel
			("Textcontrol1", this);

		public RootComposite(io.pelle.mango.client.base.modules.dictionary.model.BaseModel<?> parent) {
			super("RootComposite", parent);

			
			this.getControls().add(TEXTCONTROL1);
			
			
			
				TEXTCONTROL1.setMandatory(false);
				
				TEXTCONTROL1.setAttributePath("stringDatatype1");
				
				// natural key attribute
				TEXTCONTROL1.setMandatory(true);
			
				
				
				
				//IF dictionaryControl.baseControl.entityattribute != null
				//	dictionaryControl.baseControl.entityattribute.dictionaryControlTypeSetters
				//ENDIF
			
		}
	}
	
	private RootComposite rootComposite = new RootComposite(this);

	public DictionaryEditor1(io.pelle.mango.client.base.modules.dictionary.model.BaseModel<?> parent) {
		super("DictionaryEditor1", parent);
	
		
		setCompositeModel(rootComposite);
	}
	
	public io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel
	 TEXTCONTROL1 = rootComposite.TEXTCONTROL1;
}

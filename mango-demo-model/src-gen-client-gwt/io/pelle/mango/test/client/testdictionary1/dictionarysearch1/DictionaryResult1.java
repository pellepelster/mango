package io.pelle.mango.test.client.testdictionary1.dictionarysearch1;

@java.lang.SuppressWarnings("all")
public class DictionaryResult1 extends io.pelle.mango.client.base.modules.dictionary.model.search.ResultModel {

	public io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel
	 TEXTCONTROL1 = new io.pelle.mango.client.base.modules.dictionary.model.controls.TextControlModel
	("Textcontrol1", this);
	
	public DictionaryResult1(io.pelle.mango.client.base.modules.dictionary.model.BaseModel<?> parent) {
		super("DictionaryResult1", parent);

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

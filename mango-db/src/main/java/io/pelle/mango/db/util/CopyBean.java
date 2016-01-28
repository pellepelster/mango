package io.pelle.mango.db.util;

import io.pelle.mango.db.copy.BaseCopyBean;
import io.pelle.mango.db.copy.handler.EnumCopyHandler;
import io.pelle.mango.db.copy.handler.MapCopyHandler;
import io.pelle.mango.db.copy.handler.TypeEqualsCopyHandler;

/**
 * Provides deep bean copy
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public final class CopyBean extends BaseCopyBean {

	private static CopyBean instance;

	public static CopyBean getInstance() {
		if (instance == null) {
			instance = new CopyBean();
		}

		return instance;
	}

	@Override
	protected Class<?> getMappedTargetType(Class<?> sourceType) {
		return EntityVOMapper.getInstance().getMappedClass(sourceType);
	}

	private CopyBean() {
		super();

		addFieldCopyHandler(new MapCopyHandler());
		addFieldCopyHandler(new TypeEqualsCopyHandler());
		addFieldCopyHandler(new EnumCopyHandler());

	}

}
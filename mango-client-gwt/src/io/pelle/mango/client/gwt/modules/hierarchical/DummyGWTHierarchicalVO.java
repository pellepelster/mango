package io.pelle.mango.client.gwt.modules.hierarchical;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.vo.IVOEntityMetadata;

import java.util.HashMap;

/**
 * Dummy hierarchicalVO VO for GWT compiler
 * 
 * @author pelle
 *
 */
@SuppressWarnings("serial")
public class DummyGWTHierarchicalVO implements IHierarchicalVO {

	@Override
	public String getParentClassName() {
		return null;
	}

	@Override
	public Long getParentId() {
		return null;
	}

	@Override
	public void setParentClassName(String parentClassName) {
	}

	@Override
	public void setParentId(Long parentId) {
	}

	@Override
	public Object get(String name) {
		return null;
	}

	@Override
	public void set(String name, Object value) {
	}

	@Override
	public void setId(long id) {
	}

	@Override
	public HashMap<String, Object> getData() {
		return null;
	}

	@Override
	public long getId() {
		return 0;
	}

	@Override
	public long getOid() {
		return 0;
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public void setOid(long oid) {
	}

	@Override
	public String getNaturalKey() {
		return null;
	}

	@Override
	public boolean hasNaturalKey() {
		return false;
	}

	@Override
	public IVOEntityMetadata getMetadata() {
		return null;
	}

	@Override
	public Boolean getHasChildren() {
		return null;
	}

	@Override
	public void setHasChildren(Boolean hasChildren) {
	}

	@Override
	public void setParent(IHierarchicalVO parent) {
	}

	@Override
	public IHierarchicalVO getParent() {
		return null;
	}

}

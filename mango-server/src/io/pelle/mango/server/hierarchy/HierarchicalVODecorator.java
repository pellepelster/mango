package io.pelle.mango.server.hierarchy;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Optional;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IVOEntity;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.hierarchy.IHierarchicalService;
import io.pelle.mango.db.dao.BaseVODAO;
import io.pelle.mango.db.dao.IVOEntityDecorator;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.server.entity.EntityUtils;

public class HierarchicalVODecorator implements IVOEntityDecorator {

	@Autowired
	private BaseVODAO baseVODAO;

	@Autowired
	private IHierarchicalService hierarchicalService;

	/** {@inheritDoc} */
	@Override
	public void decorateVO(IVOEntity vo) {

		assert vo instanceof IHierarchicalVO : "vo has to be an IHierarchicalVO vo";

		IHierarchicalVO hierarchicalVO = (IHierarchicalVO) vo;

		if (hierarchicalVO.getParentClassName() != null && hierarchicalVO.getParentId() != null) {

			Class<? extends IBaseVO> parentVOClass = EntityVOMapper.getInstance().getVOClass(hierarchicalVO.getParentClassName());

			SelectQuery<? extends IBaseVO> query = SelectQuery.selectFrom(parentVOClass).where(EntityUtils.createLongAttributeDescriptor(parentVOClass, IBaseVO.FIELD_ID.getAttributeName()).eq(hierarchicalVO.getParentId()));
			Optional<IHierarchicalVO> parent = (Optional<IHierarchicalVO>) this.baseVODAO.read(query);

			if (parent.isPresent()) {
				hierarchicalVO.setParent(parent.get());
			}
		}

		hierarchicalVO.setHasChildren(this.hierarchicalService.hasChildren(vo.getClass().getName(), vo.getId()));
	}

	@Override
	public boolean supports(Class<? extends IVOEntity> voClass) {
		return IHierarchicalVO.class.isAssignableFrom(voClass);
	}

}
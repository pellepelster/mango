package io.pelle.mango.server.hierarchy;

import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.hierarchy.IHierachicalService;
import io.pelle.mango.db.dao.BaseVODAO;
import io.pelle.mango.db.dao.IVODAODecorator;
import io.pelle.mango.db.util.EntityVOMapper;

import org.springframework.beans.factory.annotation.Autowired;

public class HierarchicalVODecorator implements IVODAODecorator {

	@Autowired
	private BaseVODAO baseVODAO;

	@Autowired
	private IHierachicalService hierachicalService;

	/** {@inheritDoc} */
	@Override
	public void decorateVO(IBaseVO vo) {

		assert vo instanceof IHierarchicalVO : "vo has to be an IHierarchicalVO vo";

		IHierarchicalVO hierarchicalVO = (IHierarchicalVO) vo;

		if (hierarchicalVO.getParentClassName() != null && hierarchicalVO.getParentId() != null) {

			Class<? extends IBaseVO> parentVOClass = EntityVOMapper.getInstance().getVOClass(hierarchicalVO.getParentClassName());

			SelectQuery<? extends IBaseVO> query = SelectQuery.selectFrom(parentVOClass).where(IBaseVO.FIELD_ID.eq(hierarchicalVO.getParentId()));
			IHierarchicalVO parent = (IHierarchicalVO) this.baseVODAO.read(query);

			hierarchicalVO.setParent(parent);
		}

		hierarchicalVO.setHasChildren(this.hierachicalService.hasChildren(vo.getClass().getName(), vo.getId()));
	}

	@Override
	public boolean supports(Class<? extends IBaseVO> voClass) {
		return IHierarchicalVO.class.isAssignableFrom(voClass);
	}

}
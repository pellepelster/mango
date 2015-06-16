package io.pelle.mango.server.hierarchy;

import io.pelle.mango.client.base.db.vos.IBaseHierarchical;
import io.pelle.mango.client.base.db.vos.IHierarchicalVO;
import io.pelle.mango.client.base.modules.dictionary.model.DictionaryModelProvider;
import io.pelle.mango.client.base.modules.dictionary.model.IDictionaryModel;
import io.pelle.mango.client.base.modules.hierarchical.BaseHierarchicalConfiguration;
import io.pelle.mango.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import io.pelle.mango.client.base.modules.hierarchical.VOHierarchy;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.query.CountQuery;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.entity.IBaseEntityService;
import io.pelle.mango.client.hierarchy.DictionaryHierarchicalNodeVO;
import io.pelle.mango.client.hierarchy.IHierachicalService;
import io.pelle.mango.client.web.modules.dictionary.base.DictionaryUtil;
import io.pelle.mango.db.dao.IBaseEntityDAO;
import io.pelle.mango.db.dao.IBaseVODAO;
import io.pelle.mango.db.dao.IDAOCallback;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.server.entity.EntityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class HierachicalServiceImpl implements IHierachicalService, InitializingBean {

	private static Logger LOG = Logger.getLogger(HierachicalServiceImpl.class);

	@Autowired
	private IBaseEntityDAO baseEntityDAO;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IBaseVODAO baseVODAO;

	private List<Class<? extends IHierarchicalVO>> hierarchicalClasses;

	private List<HierarchicalConfigurationVO> hierarchicalConfigurations = new ArrayList<HierarchicalConfigurationVO>();

	private Map<String, List<VOHierarchy>> voHierarchies = null;

	@Override
	public List<DictionaryHierarchicalNodeVO> getRootNodes(String id) {
		List<DictionaryHierarchicalNodeVO> result = new ArrayList<DictionaryHierarchicalNodeVO>();

		for (VOHierarchy voHierarchy : getRootHierarchies(id)) {
			result.addAll(getChildNodes(voHierarchy, null, null));
		}

		return result;
	}

	@Override
	public HierarchicalConfigurationVO getConfigurationById(String id) {
		for (HierarchicalConfigurationVO hierarchicalConfiguration : this.hierarchicalConfigurations) {
			if (hierarchicalConfiguration.getId().equals(id)) {
				return hierarchicalConfiguration;
			}
		}

		throw new RuntimeException(String.format("unknown hierarchical configuration '%s'", id)); //$NON-NLS-1$
	}

	@Override
	public List<HierarchicalConfigurationVO> getConfigurations() {
		return hierarchicalConfigurations;
	}

	@Override
	public Boolean hasChildren(String voClassName, Long voId) {
		long count = 0;

		for (Class<? extends IHierarchicalVO> hierarchicalClass : this.hierarchicalClasses) {

			CountQuery<? extends IHierarchicalVO> query = CountQuery.countFrom(hierarchicalClass);
			query.addWhereAnd(EntityUtils.createStringAttributeDescriptor(hierarchicalClass, IHierarchicalVO.PARENT_CLASS_FIELD_NAME).eq(voClassName));
			query.addWhereAnd(EntityUtils.createLongAttributeDescriptor(hierarchicalClass, IHierarchicalVO.PARENT_ID_FIELD_NAME).eq(voId));

			count += this.baseVODAO.count(query);

			if (count > 0) {
				return true;
			}
		}

		return count > 0;
	}

	@Autowired(required = false)
	public void setHierarchicalConfigurations(List<BaseHierarchicalConfiguration> hierarchicalConfigurations) {
		for (BaseHierarchicalConfiguration hierarchicalConfiguration : hierarchicalConfigurations) {
			this.hierarchicalConfigurations.add(hierarchicalConfiguration.getHierarchyConfigurationVO());
		}
	}

	private Map<String, List<VOHierarchy>> getAndInitVOHierarchies() {

		if (this.voHierarchies == null) {

			this.voHierarchies = new HashMap<String, List<VOHierarchy>>();

			if (hierarchicalConfigurations.isEmpty()) {
				LOG.error("no hierarchical configurations found, maybe you forgot to register the hierarchical configuration bean(s)"); //$NON-NLS-1$
			}

			for (HierarchicalConfigurationVO hierarchicalConfiguration : this.hierarchicalConfigurations) {

				for (Map.Entry<String, List<String>> dictionaryHierarchy : hierarchicalConfiguration.getDictionaryHierarchy().entrySet()) {

					IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(dictionaryHierarchy.getKey());

					Class<? extends IHierarchicalVO> voClass = getHierarchicalClass(dictionaryModel.getVOClass().getName());
					List<Class<? extends IHierarchicalVO>> parentClasses = new ArrayList<Class<? extends IHierarchicalVO>>();

					for (String parentDictionaryName : dictionaryHierarchy.getValue()) {
						if (parentDictionaryName == null) {
							parentClasses.add(null);
						} else {
							String parentClassName = DictionaryModelProvider.getDictionary(parentDictionaryName).getVOClass().getName();
							parentClasses.add(getHierarchicalClass(parentClassName));
						}
					}

					if (!this.voHierarchies.containsKey(hierarchicalConfiguration.getId())) {
						this.voHierarchies.put(hierarchicalConfiguration.getId(), new ArrayList<VOHierarchy>());
					}

					this.voHierarchies.get(hierarchicalConfiguration.getId()).add(new VOHierarchy(dictionaryModel, voClass, parentClasses));
				}

			}
		}

		return this.voHierarchies;
	}

	private List<VOHierarchy> getVOHierarchy(String hierarchicalConfigurationId) {
		if (getAndInitVOHierarchies().containsKey(hierarchicalConfigurationId)) {
			return getAndInitVOHierarchies().get(hierarchicalConfigurationId);
		} else {
			return Collections.emptyList();
		}
	}

	private List<VOHierarchy> getVOHierarchyByParents(String id, String parentClassName) {
		List<VOHierarchy> result = new ArrayList<VOHierarchy>();

		for (VOHierarchy voHierarchy : this.getVOHierarchy(id)) {
			Class<?> parentClass = getHierarchicalClass(parentClassName);

			if (voHierarchy.getParents().contains(parentClass)) {
				result.add(voHierarchy);
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends IHierarchicalVO> getHierarchicalClass(String className) {
		Class<?> clazz = null;

		try {
			clazz = Class.forName(className);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (IHierarchicalVO.class.isAssignableFrom(clazz)) {
			return (Class<? extends IHierarchicalVO>) clazz;
		} else {
			throw new RuntimeException(String.format("'%s' ist not an IHierarchicalVO", className)); //$NON-NLS-1$
		}
	}

	private List<VOHierarchy> getRootHierarchies(String id) {
		List<VOHierarchy> result = new ArrayList<VOHierarchy>();

		for (VOHierarchy voHierarchy : this.getVOHierarchy(id)) {
			if (voHierarchy.getParents().isEmpty()) {
				result.add(voHierarchy);
			}
		}

		return result;

	}

	@Override
	public List<DictionaryHierarchicalNodeVO> getChildNodes(String id, Long voId, String voClassName) {

		if (voId == null && voClassName == null) {
			return getRootNodes(id);
		} else {
			List<DictionaryHierarchicalNodeVO> result = new ArrayList<DictionaryHierarchicalNodeVO>();

			for (VOHierarchy voHierarchy : getVOHierarchyByParents(id, voClassName)) {
				result.addAll(getChildNodes(voHierarchy, voId, voClassName));

			}

			return result;
		}

	}

	private List<DictionaryHierarchicalNodeVO> getChildNodes(VOHierarchy voHierarchy, Long parentId, String parentClassName) {

		List<DictionaryHierarchicalNodeVO> result = new ArrayList<DictionaryHierarchicalNodeVO>();

		@SuppressWarnings({ "unchecked" })
		SelectQuery<IHierarchicalVO> query = (SelectQuery<IHierarchicalVO>) SelectQuery.selectFrom(voHierarchy.getClazz());

		query.addWhereAnd(EntityUtils.createStringAttributeDescriptor(voHierarchy.getClazz(), IHierarchicalVO.PARENT_CLASS_FIELD_NAME).eq(parentClassName));
		query.addWhereAnd(EntityUtils.createLongAttributeDescriptor(voHierarchy.getClazz(), IHierarchicalVO.PARENT_ID_FIELD_NAME).eq(parentId));

		for (IHierarchicalVO vo : this.baseEntityService.filter(query)) {

			DictionaryHierarchicalNodeVO hierarchicalNode = new DictionaryHierarchicalNodeVO();

			hierarchicalNode.setLabel(DictionaryUtil.getLabel(voHierarchy.getDictionaryModel().getLabelControls(), vo));
			hierarchicalNode.setDictionaryName(voHierarchy.getDictionaryModel().getName());
			hierarchicalNode.setVoId(vo.getId());
			hierarchicalNode.setVoClassName(vo.getClass().getName());
			hierarchicalNode.setHasChildren(hasChildren(vo.getClass().getName(), vo.getId()));

			result.add(hierarchicalNode);
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void afterPropertiesSet() throws Exception {

		this.hierarchicalClasses = new ArrayList<Class<? extends IHierarchicalVO>>();

		for (Class<? extends IBaseVO> voClass : EntityVOMapper.getInstance().getVOClasses()) {
			if (IHierarchicalVO.class.isAssignableFrom(voClass)) {
				this.hierarchicalClasses.add((Class<? extends IHierarchicalVO>) voClass);
			}
		}

		baseEntityDAO.registerCallback(new IDAOCallback<IBaseEntity>() {

			@Override
			public void onUpdate(IBaseEntity voEntity) {
			}

			@Override
			public void onDeleteAll(Class voEntityClass) {
			}

			@Override
			public void onDelete(IBaseEntity voEntity) {
			}

			@Override
			public void onCreate(IBaseEntity voEntity) {
			}

			@Override
			public void onNewInstance(IBaseEntity voEntity, Map<String, String> properties) {

				if (voEntity instanceof IBaseHierarchical && !StringUtils.isEmpty(properties.get(IHierarchicalVO.PARENT_CLASS_FIELD_NAME))
						&& !StringUtils.isEmpty(properties.get(properties.containsKey(IHierarchicalVO.PARENT_ID_FIELD_NAME)))) {

					String parentClassName = properties.get(IHierarchicalVO.PARENT_CLASS_FIELD_NAME);
					long parentId = Long.parseLong(properties.get(IHierarchicalVO.PARENT_ID_FIELD_NAME));

					IBaseHierarchical baseHierarchical = (IBaseHierarchical) voEntity;
					baseHierarchical.setParentClassName(parentClassName);
					baseHierarchical.setParentId(parentId);
				}

			}
		});

	}
}

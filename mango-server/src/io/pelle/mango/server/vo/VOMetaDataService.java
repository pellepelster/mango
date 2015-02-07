package io.pelle.mango.server.vo;

import io.pelle.mango.client.base.db.vos.IMobileBaseVO;
import io.pelle.mango.client.base.vo.IBaseEntity;
import io.pelle.mango.client.base.vo.IBaseVO;
import io.pelle.mango.client.base.vo.IEntityVOMapper;
import io.pelle.mango.db.util.BeanUtils;
import io.pelle.mango.db.util.EntityVOMapper;
import io.pelle.mango.server.DirectedGraph;
import io.pelle.mango.server.TopologicalSort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class VOMetaDataService {

	private final static Logger LOG = Logger.getLogger(VOMetaDataService.class);

	private List<Class<? extends IBaseVO>> voClasses = new ArrayList<Class<? extends IBaseVO>>();

	private List<Class<? extends IBaseEntity>> entityClasses = new ArrayList<Class<? extends IBaseEntity>>();

	@Autowired
	public void setEntityVOMappers(List<IEntityVOMapper> entityVOMappers) {

		List<Class<? extends IBaseVO>> voClasses = new ArrayList<Class<? extends IBaseVO>>();

		for (IEntityVOMapper entityVOMapper : entityVOMappers) {
			voClasses.addAll(entityVOMapper.getVOClasses());
		}

		buildMetaData(voClasses);
	}

	@SuppressWarnings("unchecked")
	private void buildMetaData(List<Class<? extends IBaseVO>> tmpVoClasses) {

		for (Class<?> voClass : tmpVoClasses) {
			if (IMobileBaseVO.class.isAssignableFrom(voClass)) {
				LOG.info(String.format("ignoring mobile vo '%s'", voClass.getName()));
			} else if (voClass.getPackage() != null && voClass.getPackage().getName() != null && voClass.getPackage().getName().contains(".test.")) {
				LOG.info(String.format("ignoring test vo '%s'", voClass.getName()));
			} else {
				LOG.info(String.format("found '%s'", voClass.getName()));
				this.voClasses.add((Class<? extends IBaseVO>) voClass);
			}
		}

		LOG.info(String.format("%d classes found", this.voClasses.size()));

		DirectedGraph<Class<? extends IBaseVO>> directedGraph = new DirectedGraph<Class<? extends IBaseVO>>();

		for (Class<? extends IBaseVO> voClass : this.voClasses) {
			directedGraph.addNode(voClass);
		}

		for (Class<? extends IBaseVO> voClass : this.voClasses) {

			Set<Class<? extends IBaseVO>> referencedClasses = BeanUtils.getReferencedVOs(voClass);

			for (Class<? extends IBaseVO> referencedClass : referencedClasses) {
				if (!voClass.equals(referencedClass)) {
					directedGraph.addEdge(voClass, referencedClass);
				}
			}
		}

		try {
			this.voClasses = TopologicalSort.sort(directedGraph);
			Collections.reverse(this.voClasses);
		} catch (Exception e) {
			LOG.error("error sorting vo classes", e);
		}
		
		for(Class<? extends IBaseVO> voClass : voClasses) {
			entityClasses.add(EntityVOMapper.getInstance().getEntityClass(voClass));
		}
	}

	public Collection<Class<? extends IBaseVO>> getVOClasses() {
		return this.voClasses;
	}

	public Collection<Class<? extends IBaseEntity>> getEntityClasses() {
		return this.entityClasses;
	}

	public Class<? extends IBaseEntity> getEntityClassForName(String entityName) {

		for (Class<? extends IBaseEntity> entityClass : entityClasses) {

			if (entityClass.getSimpleName().toLowerCase().equals(entityName.toLowerCase())) {
				return entityClass;
			}
		}

		return null;
	}

}
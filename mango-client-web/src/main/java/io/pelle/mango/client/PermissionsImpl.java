
package io.pelle.mango.client;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.gwt.user.client.rpc.AsyncCallback;

import io.pelle.mango.client.base.modules.dictionary.model.IBaseModel;
import io.pelle.mango.client.base.modules.dictionary.model.containers.ICustomCompositeModel;
import io.pelle.mango.client.base.util.CollectionUtils;
import io.pelle.mango.client.base.util.CustomComposite;
import io.pelle.mango.client.base.util.Predicates;
import io.pelle.mango.client.base.vo.IAttributeDescriptor;
import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.client.security.MangoGroupVO;
import io.pelle.mango.client.security.MangoOperationVO;
import io.pelle.mango.client.security.MangoPermissionVO;
import io.pelle.mango.client.web.MangoClientWeb;
import io.pelle.mango.client.web.modules.dictionary.base.BaseDictionaryElement;
import io.pelle.mango.client.web.util.BaseErrorAsyncCallback;

@CustomComposite("permissions")
public class PermissionsImpl extends io.pelle.mango.client.web.modules.dictionary.container.BaseCustomComposite {

	private List<MangoPermissionVO> availablePermissions;

	public PermissionsImpl(ICustomCompositeModel composite, BaseDictionaryElement<? extends IBaseModel> parent) {
		super(composite, parent);
	}

	@Override
	public void update() {
		super.update();
	}

	public void getAvailablePermissions(final AsyncCallback<List<MangoPermissionVO>> callback) {

		if (availablePermissions != null) {
			callback.onSuccess(availablePermissions);
		} else {
			MangoClientWeb.getInstance().getRemoteServiceLocator().getSecurityService().getAvailablePermissions(new BaseErrorAsyncCallback<List<MangoPermissionVO>>() {

				@Override
				public void onSuccess(List<MangoPermissionVO> result) {
					availablePermissions = result;
					callback.onSuccess(availablePermissions);
				}
			});
		}

	}

	@Override
	public void populateLoadFilter(SelectQuery<?> selectQuery) {
		selectQuery.join(new IAttributeDescriptor<?>[] { MangoGroupVO.PERMISSIONS, MangoPermissionVO.OPERATIONS });
	}

	@SuppressWarnings("unchecked")
	public List<MangoPermissionVO> getPermissions() {
		return (List<MangoPermissionVO>) getVOWrapper().get(getModel().getAttributeName());
	}

	public void addPermission(String permissionId, String operationId) {

		final Predicate<MangoPermissionVO> permissionPredicate = Predicates.attributeEquals(MangoPermissionVO.PERMISSIONID, permissionId);
		final Predicate<MangoOperationVO> operationPedicate = Predicates.attributeEquals(MangoOperationVO.OPERATIONID, operationId);

		MangoPermissionVO permission = new MangoPermissionVO();
		permission.setPermissionId(permissionId);

		MangoOperationVO operation = new MangoOperationVO();
		operation.setOperationId(operationId);

		permission = CollectionUtils.addIfNotExists(getPermissions(), permissionPredicate, permission);

		CollectionUtils.addIfNotExists(permission.getOperations(), operationPedicate, operation);
	}
}

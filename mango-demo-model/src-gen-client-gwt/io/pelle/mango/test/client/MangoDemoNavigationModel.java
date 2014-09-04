package io.pelle.mango.test.client;

public class MangoDemoNavigationModel {
	public static class RootNavigationNode extends io.pelle.mango.client.base.modules.navigation.NavigationTreeElement {
	public io.pelle.mango.test.client.Navigation NAVIGATION = new io.pelle.mango.test.client.Navigation();

		public RootNavigationNode() {
			super("ROOT");

			getChildren().add(NAVIGATION);

			setLabel(RootNavigationNode.class.getName());
		}
	}

	public static RootNavigationNode ROOT = new RootNavigationNode();

}

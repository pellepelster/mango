package io.pelle.mango.gwt.commons.toastr;

public final class Toastr {

	public static native void setTapToDismiss(boolean value)/*-{
															$wnd.toastr.options.tapToDismiss = value;
															}-*/;

	public static native void setCloseButton(boolean value)/*-{
															$wnd.toastr.options.closeButton = value;
															}-*/;

	public static native void setTimeOut(int value)/*-{
													$wnd.toastr.options.timeOut = value;
													}-*/;

	public static native void setHideDuration(int value)/*-{
														$wnd.toastr.options.hideDuration = value;
														}-*/;

	public static native void setExtendedTimeOut(int value)/*-{
															$wnd.toastr.options.extendedTimeOut = value;
															}-*/;

	public static native void setOnHidden(ToastrCallback callback)/*-{
																	$wnd.toastr.options.onHidden = function() {
																	fn.@io.pelle.mango.gwt.commons.toastr.ToastrCallback::run()();
																	};
																	}-*/;

	public static native void setOnShow(ToastrCallback callback)/*-{
																$wnd.toastr.options.onShow = function() {
																fn.@io.pelle.mango.gwt.commons.toastr.ToastrCallback::run()();
																};
																}-*/;

	public static native void setOnClick(ToastrCallback callback)/*-{
																	$wnd.toastr.options.onClick = function() {
																	fn.@io.pelle.mango.gwt.commons.toastr.ToastrCallback::run()();
																	};
																	}-*/;

	public static native void warn(String text)/*-{
												$wnd.toastr.warning(text);
												}-*/;

	public static native void warn(String title, String text)/*-{
																$wnd.toastr.warning(text, title);
																}-*/;

	public static native void info(String text)/*-{
												$wnd.toastr.info(text);
												}-*/;

	public static native void info(String title, String text)/*-{
																$wnd.toastr.info(text, title);
																}-*/;

	public static native void error(String text)/*-{
												$wnd.toastr.error(text);
												}-*/;

	public static native void error(String title, String text)/*-{
																$wnd.toastr.error(text, title);
																}-*/;

	public static native void success(String text)/*-{
													$wnd.toastr.success(text);
													}-*/;

	public static native void success(String title, String text)/*-{
																$wnd.toastr.success(text, title);
																}-*/;

	public static native void clear()/*-{
										$wnd.toastr.clear();
										}-*/;

	public static void setPosition(ToastPosition position) {
		setPositionClass(position.getValue());
	}

	public static void setHideMethod(HideMethod method) {
		setHideMethod(method.getValue());
	}

	public static void setShowMethod(ShowMethod method) {
		setShowMethod(method.getValue());
	}

	public static void setShowEasing(ShowEasing method) {
		setShowEasing(method.getValue());
	}

	public static void setHideEasing(ShowEasing method) {
		setHideEasing(method.getValue());
	}

	private static native void setShowMethod(String value)/*-{
															$wnd.toastr.options.showMethod = value;
															}-*/;

	private static native void setHideMethod(String value)/*-{
															$wnd.toastr.options.hideMethod = value;
															}-*/;

	private static native void setPositionClass(String value)/*-{
																$wnd.toastr.options.positionClass = value;
																}-*/;

	private static native void setShowEasing(String value)/*-{
															$wnd.toastr.options.showEasing = value;
															}-*/;

	private static native void setHideEasing(String value)/*-{
															$wnd.toastr.options.hideEasing = value;
															}-*/;

}
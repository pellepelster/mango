package io.pelle.mango.client.gwt.utils;

import io.pelle.mango.client.gwt.GwtStyles;

import java.math.BigDecimal;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Widget;

public class FadeAnimation extends Animation {

	private Element element;
	private double opacityIncrement;
	private double targetOpacity;
	private double baseOpacity;

	public static void adaptMouseOver(Widget widget, Element fadeElement) {

		final FadeAnimation fadeAnimation = new FadeAnimation(fadeElement);
		widget.addHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				fadeAnimation.fade(GwtStyles.FADE_IN_DURATION, GwtStyles.ENABLED_OPACITY);
			}
		}, MouseOverEvent.getType());

		widget.addHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				fadeAnimation.fade(GwtStyles.FADE_OUT_DURATION, GwtStyles.DISABLED_OPACITY);
			}
		}, MouseOutEvent.getType());

	}

	private FadeAnimation(Element element) {
		this.element = element;
	}

	@Override
	protected void onUpdate(double progress) {
		element.getStyle().setOpacity(baseOpacity + progress * opacityIncrement);
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		element.getStyle().setOpacity(targetOpacity);
	}

	public void fade(int duration, double targetOpacity) {
		if (targetOpacity > 1.0) {
			targetOpacity = 1.0;
		}
		if (targetOpacity < 0.0) {
			targetOpacity = 0.0;
		}

		this.targetOpacity = targetOpacity;

		String opacityStr = element.getStyle().getOpacity();

		try {
			baseOpacity = new BigDecimal(opacityStr).doubleValue();
			opacityIncrement = targetOpacity - baseOpacity;
			run(duration);
		} catch (NumberFormatException e) {
			// set opacity directly
			onComplete();
		}
	}

}
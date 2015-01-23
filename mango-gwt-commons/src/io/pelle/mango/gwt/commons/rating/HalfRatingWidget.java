package io.pelle.mango.gwt.commons.rating;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public class HalfRatingWidget extends BaseRatingWidget implements HasValue<Float> {

	public HalfRatingWidget() {
		this(false, false);
	}

	public HalfRatingWidget(boolean readonly, boolean showClear) {
		super(readonly, false, showClear);
	}

	private float getFractionRating(int rating) {
		return (float) rating / 2.0f;
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Float> handler) {

		setRatingChangedHandler(new RatingChangedHandler() {

			@Override
			public void ratingChanged(int rating) {
				ValueChangeEvent.fire(HalfRatingWidget.this, getFractionRating(rating));
			}
		});

		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public Float getValue() {
		return getFractionRating(getRating());
	}

	@Override
	public void setValue(Float value) {
		setValue(value, false);
	}

	public static BigDecimal round(BigDecimal value, BigDecimal increment, RoundingMode roundingMode) {
		if (increment.signum() == 0) {
			// 0 increment does not make much sense, but prevent division by 0
			return value;
		} else {
			BigDecimal divided = value.divide(increment, 0, roundingMode);
			BigDecimal result = divided.multiply(increment);
			return result;
		}
	}

	@Override
	public void setValue(Float value, boolean fireEvents) {

		setRating(Math.round(value * 2), false);

		if (fireEvents) {
			ValueChangeEvent.fire(this, value);
		}
	}
}

package io.pelle.mango.gwt.commons.rating;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public class FullRatingWidget extends BaseRatingWidget implements HasValue<Integer> {

	public FullRatingWidget() {
		this(false, false);
	}

	public FullRatingWidget(boolean readonly, boolean showClear) {
		super(readonly, true, showClear);
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {

		setRatingChangedHandler(new RatingChangedHandler() {

			@Override
			public void ratingChanged(int rating) {
				ValueChangeEvent.fire(FullRatingWidget.this, rating);
			}
		});

		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public Integer getValue() {
		return getRating();
	}

	@Override
	public void setValue(Integer value) {
		setValue(value, false);
	}

	@Override
	public void setValue(Integer value, boolean fireEvents) {

		setRating(value, false);

		if (fireEvents) {
			ValueChangeEvent.fire(this, getRating());
		}
	}

}

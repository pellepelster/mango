package io.pelle.mango.gwt.commons.rating;

import io.pelle.mango.gwt.commons.rating.standard.StandardRatingWidgetResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * @author Austin_Rappa
 *
 */
public abstract class BaseRatingWidget extends Composite implements ClickHandler, MouseOverHandler, MouseOutHandler {

	protected interface RatingChangedHandler {
		void ratingChanged(int rating);
	}

	private RatingChangedHandler ratingChangedHandler;

	private FlowPanel panel = new FlowPanel();

	private Image clearImage = new Image();

	private int rating = 0;

	private final int ratingMax;

	private int hoverIndex = 0;

	private boolean readonly = false;

	private boolean onlyFullStars;

	private boolean showClear = false;

	private StandardRatingWidgetResources STANDARD_RESOURCES = ((StandardRatingWidgetResources) GWT.create(StandardRatingWidgetResources.class));

	private StandardRatingWidgetResources resources;;

	public BaseRatingWidget() {
		this(false, false, false);
	}

	public BaseRatingWidget(boolean readonly, boolean onlyFullStars, boolean showClear) {
		this.readonly = readonly;
		this.onlyFullStars = onlyFullStars;
		this.showClear = showClear;

		if (onlyFullStars) {
			ratingMax = 5;
		} else {
			ratingMax = 10;
		}

		createWidget();

	}

	private void createWidget() {

		initWidget(panel);
		panel.setStyleName("starrating");
		this.addMouseOutHandler(this);

		for (int i = 0; i < ratingMax; i++) {

			Image image = new Image();

			image.setStyleName("star");
			image.setTitle("" + (i + 1));
			image.addClickHandler(this);
			image.addMouseOverHandler(this);

			panel.add(image);
		}

		if (!this.isReadOnly() && showClear) {

			panel.getElement().getStyle().setCursor(Style.Cursor.POINTER);

			clearImage.setResource(getResources().clear());

			clearImage.setTitle("clear");
			clearImage.addClickHandler(this);
			clearImage.addMouseOverHandler(this);

			panel.add(clearImage);
		}

		this.updateStarImages();
	}

	private HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}

	private void updateStarImages() {
		for (int i = 0; i < ratingMax; i++) {
			Image image = (Image) panel.getWidget(i);
			image.setResource(this.getImageResource(i));
		}
	}

	private ImageResource getImageResource(int index) {
		ImageResource resource = null;

		if (onlyFullStars) {
			if (index >= this.getHoverIndex()) {
				if (index >= rating) {
					resource = getResources().starUnselected();
				} else {
					resource = getResources().starSelected();
				}
			} else {
				resource = getResources().starHover();
			}
		} else {
			if (index % 2 == 0) {
				if (index >= this.getHoverIndex()) {
					if (index >= rating) {
						resource = getResources().starUnselectedLeft();
					} else {
						resource = getResources().starSelectedLeft();
					}
				} else {
					resource = getResources().starHoverLeft();
				}
			} else {
				if (index >= this.getHoverIndex()) {
					if (index >= rating) {
						resource = getResources().starUnselectedRight();
					} else {
						resource = getResources().starSelectedRight();
					}
				} else {
					resource = getResources().starHoverRight();
				}
			}
		}

		return resource;
	}

	public void setHoverIndex(int hoverIndex) {
		this.hoverIndex = hoverIndex;
	}

	public int getHoverIndex() {
		return hoverIndex;
	}

	public void setReadOnly(boolean read_only) {
		this.readonly = read_only;
	}

	public boolean isReadOnly() {
		return readonly;
	}

	public void onMouseOver(MouseOverEvent event) {
		if (!this.isReadOnly()) {
			Image image = (Image) event.getSource();
			if (image.equals(clearImage)) {
				this.setHoverIndex(0);
			} else {
				this.setHoverIndex(Integer.parseInt(image.getTitle()));
			}
			this.updateStarImages();
		}
	}

	public void onClick(ClickEvent event) {
		if (!this.isReadOnly()) {
			Image image = (Image) event.getSource();
			if (image.equals(clearImage)) {
				this.setRating(0, true);
			} else {
				this.setRating(Integer.parseInt(image.getTitle()), true);
			}
		}
	}

	public void onMouseOut(MouseOutEvent event) {
		this.setHoverIndex(0);
		this.updateStarImages();
	}

	protected int getRating() {
		return rating;
	}

	protected void setRating(int rating, boolean fireChangedHandler) {
		this.rating = rating;
		updateStarImages();

		if (fireChangedHandler && ratingChangedHandler != null) {
			ratingChangedHandler.ratingChanged(rating);
		}
	}

	public void setRatingChangedHandler(RatingChangedHandler ratingChangedHandler) {
		this.ratingChangedHandler = ratingChangedHandler;
	}

	public void setResources(StandardRatingWidgetResources resources) {
		this.resources = resources;
		updateStarImages();
		clearImage.setResource(getResources().clear());
	}

	private StandardRatingWidgetResources getResources() {
		if (resources != null) {
			return resources;
		}
		return STANDARD_RESOURCES;

	}
}

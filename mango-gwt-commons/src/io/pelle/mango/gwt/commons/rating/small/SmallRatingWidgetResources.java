package io.pelle.mango.gwt.commons.rating.small;

import io.pelle.mango.gwt.commons.rating.standard.StandardRatingWidgetResources;

import com.google.gwt.resources.client.ImageResource;

public interface SmallRatingWidgetResources extends StandardRatingWidgetResources {

	@Source("starSelected.png")
	ImageResource starSelected();

	@Source("starSelectedLeft.png")
	ImageResource starSelectedLeft();

	@Source("starSelectedRight.png")
	ImageResource starSelectedRight();

	@Source("starUnselected.png")
	ImageResource starUnselected();

	@Source("starUnselectedLeft.png")
	ImageResource starUnselectedLeft();

	@Source("starUnselectedRight.png")
	ImageResource starUnselectedRight();

	@Source("starHover.png")
	ImageResource starHover();

	@Source("starHoverLeft.png")
	ImageResource starHoverLeft();

	@Source("starHoverRight.png")
	ImageResource starHoverRight();

	@Source("clear.png")
	ImageResource clear();

}

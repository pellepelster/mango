package io.pelle.mango.gwt.commons;

import io.pelle.mango.gwt.commons.rating.BaseRatingWidget;
import io.pelle.mango.gwt.commons.rating.FractionRatingWidget;
import io.pelle.mango.gwt.commons.rating.FullRatingWidget;
import io.pelle.mango.gwt.commons.rating.large.LargeRatingWidgetResources;
import io.pelle.mango.gwt.commons.rating.small.SmallRatingWidgetResources;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class MangoGwtCommons implements EntryPoint {

	private FractionRatingWidget ratingWidgetSum2;
	private FullRatingWidget ratingWidgetSum1;

	@Override
	public void onModuleLoad() {

		TabPanel tabPanel = new TabPanel();
		tabPanel.setHeight("100%");
		tabPanel.setWidth("100%");

		tabPanel.add(createRatingPanel(), "Rating Widget");

		tabPanel.selectTab(0);
		RootLayoutPanel.get().add(tabPanel);
	}

	private List<BaseRatingWidget> ratingWidgets = new ArrayList<BaseRatingWidget>();
	private Label ratingLabelSum2;
	private Label ratingLabelSum1;

	private void updateSumWidgets() {

		float sum = 0;

		for (BaseRatingWidget ratingWidget : ratingWidgets) {

			if (ratingWidget instanceof FullRatingWidget) {
				sum += ((FullRatingWidget) ratingWidget).getValue();
			} else if (ratingWidget instanceof FractionRatingWidget) {
				sum += ((FractionRatingWidget) ratingWidget).getValue();
			}
		}

		sum = sum / ratingWidgets.size();

		ratingWidgetSum2.setValue(sum);
		ratingLabelSum2.setText("" + sum);
	}

	private Panel createRatingPanel() {

		Grid grid = new Grid(6, 3);
		grid.setCellSpacing(20);
		int row = 0;

		// full stars
		grid.setWidget(row, 0, new Label("Full stars"));

		FullRatingWidget ratingWidgetFull = new FullRatingWidget();
		ratingWidgets.add(ratingWidgetFull);
		grid.setWidget(row, 1, ratingWidgetFull);

		final Label ratingLabelFull = new Label();
		ratingLabelFull.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelFull);

		ratingWidgetFull.addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				ratingLabelFull.setText("" + event.getValue());
				updateSumWidgets();
			}
		});
		row++;

		// full stars
		grid.setWidget(row, 0, new Label("Full stars (with clear)"));

		FullRatingWidget ratingWidgetFullClear = new FullRatingWidget();
		ratingWidgets.add(ratingWidgetFullClear);
		grid.setWidget(row, 1, ratingWidgetFullClear);

		final Label ratingLabelFullClear = new Label();
		ratingLabelFullClear.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelFullClear);

		ratingWidgetFullClear.addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				ratingLabelFullClear.setText("" + event.getValue());
				updateSumWidgets();
			}
		});
		row++;

		// half stars
		grid.setWidget(row, 0, new Label("Half stars"));

		FractionRatingWidget ratingWidgetHalf = new FractionRatingWidget();
		ratingWidgets.add(ratingWidgetHalf);
		grid.setWidget(row, 1, ratingWidgetHalf);

		final Label ratingLabelHalf = new Label();
		ratingLabelHalf.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelHalf);

		ratingWidgetHalf.addValueChangeHandler(new ValueChangeHandler<Float>() {

			@Override
			public void onValueChange(ValueChangeEvent<Float> event) {
				ratingLabelHalf.setText("" + event.getValue());
				updateSumWidgets();
			}
		});

		row++;

		// half stars (large)
		grid.setWidget(row, 0, new Label("Half stars (large)"));

		FractionRatingWidget ratingWidgetHalfLarge = new FractionRatingWidget();
		ratingWidgetHalfLarge.setResources((LargeRatingWidgetResources) GWT.create(LargeRatingWidgetResources.class));
		ratingWidgets.add(ratingWidgetHalfLarge);
		grid.setWidget(row, 1, ratingWidgetHalfLarge);

		final Label ratingLabelHalfLarge = new Label();
		ratingLabelHalfLarge.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelHalfLarge);

		ratingWidgetHalfLarge.addValueChangeHandler(new ValueChangeHandler<Float>() {

			@Override
			public void onValueChange(ValueChangeEvent<Float> event) {
				ratingLabelHalfLarge.setText("" + event.getValue());
				updateSumWidgets();
			}
		});

		row++;

		// half stars
		grid.setWidget(row, 0, new Label("Half stars (small)"));

		FractionRatingWidget ratingWidget4 = new FractionRatingWidget();
		ratingWidget4.setResources((SmallRatingWidgetResources) GWT.create(SmallRatingWidgetResources.class));
		ratingWidgets.add(ratingWidget4);
		grid.setWidget(row, 1, ratingWidget4);

		final Label ratingLabel4 = new Label();
		ratingLabel4.setWidth("4em");
		grid.setWidget(row, 2, ratingLabel4);

		ratingWidget4.addValueChangeHandler(new ValueChangeHandler<Float>() {

			@Override
			public void onValueChange(ValueChangeEvent<Float> event) {
				ratingLabel4.setText("" + event.getValue());
				updateSumWidgets();
			}
		});

		row++;

		// full stars
		grid.setWidget(row, 0, new Label("Full stars (readonly)"));

		ratingWidgetSum1 = new FullRatingWidget(true);
		grid.setWidget(row, 1, ratingWidgetSum1);

		ratingLabelSum1 = new Label();
		ratingLabelSum1.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelSum1);

		row++;

		// half stars
		grid.setWidget(row, 0, new Label("Half stars (readonly)"));

		ratingWidgetSum2 = new FractionRatingWidget(true);
		grid.setWidget(row, 1, ratingWidgetSum2);

		ratingLabelSum2 = new Label();
		ratingLabelSum2.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelSum2);

		row++;

		return grid;

	}
}

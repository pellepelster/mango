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
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class MangoGwtCommons implements EntryPoint {

	private FractionRatingWidget ratingWidgetSumHalf;

	private FullRatingWidget ratingWidgetSumFull;

	private NumberFormat HALF_FORMAT = NumberFormat.getFormat("0.0");

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

	private Label ratingLabelSumHalf;

	private Label ratingLabelSumFull;

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

		ratingWidgetSumHalf.setValue(sum);
		ratingLabelSumHalf.setText(HALF_FORMAT.format(sum));

		ratingLabelSumFull.setText("" + Math.round(sum));
		ratingWidgetSumFull.setValue(Math.round(sum));
	}

	private Panel createRatingPanel() {

		FlexTable grid = new FlexTable();
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
				ratingLabelHalf.setText(HALF_FORMAT.format(event.getValue()));
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
				ratingLabelHalfLarge.setText(HALF_FORMAT.format(event.getValue()));
				updateSumWidgets();
			}
		});

		row++;

		// half stars
		grid.setWidget(row, 0, new Label("Half stars (small)"));

		FractionRatingWidget ratingWidgetHalfSmall = new FractionRatingWidget();
		ratingWidgetHalfSmall.setResources((SmallRatingWidgetResources) GWT.create(SmallRatingWidgetResources.class));
		ratingWidgets.add(ratingWidgetHalfSmall);
		grid.setWidget(row, 1, ratingWidgetHalfSmall);

		final Label ratingLabelHalfSmall = new Label();
		ratingLabelHalfSmall.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelHalfSmall);

		ratingWidgetHalfSmall.addValueChangeHandler(new ValueChangeHandler<Float>() {

			@Override
			public void onValueChange(ValueChangeEvent<Float> event) {
				ratingLabelHalfSmall.setText(HALF_FORMAT.format(event.getValue()));
				updateSumWidgets();
			}
		});

		row++;

		// full stars
		grid.setWidget(row, 0, new Label("Full stars (readonly)"));

		ratingWidgetSumFull = new FullRatingWidget(true, false);
		grid.setWidget(row, 1, ratingWidgetSumFull);

		ratingLabelSumFull = new Label();
		ratingLabelSumFull.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelSumFull);

		row++;

		// half stars
		grid.setWidget(row, 0, new Label("Half stars (readonly)"));

		ratingWidgetSumHalf = new FractionRatingWidget(true, false);
		grid.setWidget(row, 1, ratingWidgetSumHalf);

		ratingLabelSumHalf = new Label();
		ratingLabelSumHalf.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelSumHalf);

		row++;

		return grid;

	}
}

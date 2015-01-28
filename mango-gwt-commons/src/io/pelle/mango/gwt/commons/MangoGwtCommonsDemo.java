package io.pelle.mango.gwt.commons;

import io.pelle.mango.gwt.commons.rating.BaseRatingWidget;
import io.pelle.mango.gwt.commons.rating.FullRatingWidget;
import io.pelle.mango.gwt.commons.rating.HalfRatingWidget;
import io.pelle.mango.gwt.commons.rating.large.LargeRatingWidgetResources;
import io.pelle.mango.gwt.commons.rating.small.SmallRatingWidgetResources;
import io.pelle.mango.gwt.commons.toastr.HideMethod;
import io.pelle.mango.gwt.commons.toastr.ShowMethod;
import io.pelle.mango.gwt.commons.toastr.ToastPosition;
import io.pelle.mango.gwt.commons.toastr.Toastr;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MangoGwtCommonsDemo implements EntryPoint {

	private HalfRatingWidget ratingWidgetSumHalf;

	private FullRatingWidget ratingWidgetSumFull;

	private NumberFormat HALF_FORMAT = NumberFormat.getFormat("0.0");

	@Override
	public void onModuleLoad() {

		final TabLayoutPanel tabPanel = new TabLayoutPanel(2, Unit.EM);
		tabPanel.setHeight("100%");
		tabPanel.setWidth("100%");

		Panel toastrPanel = createToastrPanel();
		tabPanel.add(createRatingPanel(), "Rating Widget");
		tabPanel.add(toastrPanel, "Toastr");

		String tabParamter = Window.Location.getParameter("tab");
		if (tabParamter != null) {
			if (tabParamter.equals("toastr")) {
				tabPanel.selectTab(toastrPanel);
			}

		} else {
			tabPanel.selectTab(0);
		}
		RootLayoutPanel.get().add(tabPanel);
	}

	private List<BaseRatingWidget> ratingWidgets = new ArrayList<BaseRatingWidget>();

	private Label ratingLabelSumHalf;

	private Label ratingLabelSumFull;

	private boolean hasCloseButton = false;

	private boolean hasTapToDismiss = false;

	private ShowMethod showMethod = ShowMethod.FADE_IN;

	private HideMethod hideMethod = HideMethod.FADE_OUT;

	private ToastPosition position = ToastPosition.TOP_LEFT;

	private void updateSumWidgets() {

		float sum = 0;

		for (BaseRatingWidget ratingWidget : ratingWidgets) {

			if (ratingWidget instanceof FullRatingWidget) {
				sum += ((FullRatingWidget) ratingWidget).getValue();
			} else if (ratingWidget instanceof HalfRatingWidget) {
				sum += ((HalfRatingWidget) ratingWidget).getValue();
			}
		}

		sum = sum / ratingWidgets.size();

		ratingWidgetSumHalf.setValue(sum);
		ratingLabelSumHalf.setText(HALF_FORMAT.format(sum));

		ratingLabelSumFull.setText("" + Math.round(sum));
		ratingWidgetSumFull.setValue(Math.round(sum));
	}

	private void addCodeExample(FlexTable flexTable, int row, String codeExample) {
		flexTable.getFlexCellFormatter().setColSpan(row, 0, 3);
		flexTable.setWidget(row, 0, new HTML("<pre>" + SafeHtmlUtils.htmlEscape(codeExample) + "</pre>"));

	}

	private String getMessageText() {

		String text = "";

		text += "hasCloseButton = " + Boolean.toString(hasCloseButton) + "<br />";
		text += "hasTapToDismiss = " + Boolean.toString(hasTapToDismiss) + "<br />";
		text += "showMethod = " + showMethod.toString() + "<br />";
		text += "hideMethod = " + hideMethod.toString() + "<br />";
		text += "position = " + position.toString() + "<br />";

		return text;
	}

	private Panel createToastrPanel() {

		Grid grid = new Grid(3, 3);
		grid.setWidth("100%");
		grid.setHeight("100%");

		VerticalPanel centerPanel = new VerticalPanel();
		centerPanel.setSpacing(10);
		String text = "<h4>Try the buttons to show the toastr messages in different positions and message types. Due to the architecture of toastr itself messages will only start appearing in new positions if all old messages from the previous position are gone.</h4>";
		centerPanel.add(new HTMLPanel(text));

		Grid centerGrid = new Grid(6, 3);
		centerGrid.setCellSpacing(10);
		centerPanel.add(centerGrid);

		// setCloseButton
		CheckBox setCloseButton = new CheckBox("Enable close button");
		centerGrid.setWidget(0, 1, setCloseButton);
		final Label setCloseButtonValueLabel = new Label(getSetCloseButtonCode());
		centerGrid.setWidget(0, 2, setCloseButtonValueLabel);

		setCloseButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				hasCloseButton = event.getValue();
				Toastr.setCloseButton(hasCloseButton);
				setCloseButtonValueLabel.setText(getSetCloseButtonCode());
			}
		});

		// setTapToDismiss
		CheckBox setTapToDismiss = new CheckBox("Tap do dismiss message");
		centerGrid.setWidget(1, 1, setTapToDismiss);
		final Label setTapToDismissValueLabel = new Label(getSetTapToDismissCode());
		centerGrid.setWidget(1, 2, setTapToDismissValueLabel);

		setTapToDismiss.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				hasTapToDismiss = event.getValue();
				Toastr.setTapToDismiss(hasTapToDismiss);
				setTapToDismissValueLabel.setText(getSetTapToDismissCode());
			}
		});

		// setShowMethod
		centerGrid.setWidget(2, 0, new Label("Show Method"));
		final ListBox setShowMethodListBox = new ListBox();
		setShowMethodListBox.addItem(ShowMethod.FADE_IN.toString());
		setShowMethodListBox.addItem(ShowMethod.SLIDE_DOWN.toString());
		centerGrid.setWidget(2, 1, setShowMethodListBox);
		final Label setShowMethodValueLabel = new Label(getSetShowMethodCode());
		centerGrid.setWidget(2, 2, setShowMethodValueLabel);
		setShowMethodListBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String itemText = setShowMethodListBox.getItemText(setShowMethodListBox.getSelectedIndex());
				showMethod = ShowMethod.valueOf(itemText);
				Toastr.setShowMethod(showMethod);
				setShowMethodValueLabel.setText(getSetShowMethodCode());
			}
		});

		// setHideMethod
		centerGrid.setWidget(3, 0, new Label("Hide Method"));
		final ListBox setHideMethodListBox = new ListBox();
		setHideMethodListBox.addItem(HideMethod.FADE_OUT.toString());
		centerGrid.setWidget(3, 1, setHideMethodListBox);
		final Label setHideMethodValueLabel = new Label(getSetHideMethodCode());
		centerGrid.setWidget(3, 2, setHideMethodValueLabel);
		setHideMethodListBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String itemText = setHideMethodListBox.getItemText(setHideMethodListBox.getSelectedIndex());
				hideMethod = HideMethod.valueOf(itemText);
				Toastr.setHideMethod(hideMethod);
				setHideMethodValueLabel.setText(getSetHideMethodCode());
			}
		});

		// setPosition
		centerGrid.setWidget(4, 0, new Label("Position"));
		final ListBox setPositionListBox = new ListBox();
		setPositionListBox.addItem(ToastPosition.TOP_LEFT.toString());
		setPositionListBox.addItem(ToastPosition.TOP_FULL_WIDTH.toString());
		setPositionListBox.addItem(ToastPosition.TOP_RIGHT.toString());
		setPositionListBox.addItem(ToastPosition.BOTTOM_FULL_WIDTH.toString());
		setPositionListBox.addItem(ToastPosition.BOTTOM_LEFT.toString());
		setPositionListBox.addItem(ToastPosition.BOTTOM_RIGHT.toString());
		centerGrid.setWidget(4, 1, setPositionListBox);
		final Label setPositionValueLabel = new Label(getsetPositionCode());
		centerGrid.setWidget(4, 2, setPositionValueLabel);
		setPositionListBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				String itemText = setPositionListBox.getItemText(setPositionListBox.getSelectedIndex());
				position = ToastPosition.valueOf(itemText);
				Toastr.setPosition(position);
				setPositionValueLabel.setText(getsetPositionCode());
			}
		});

		// success
		Button successButton = new Button("Toastr.sucess()");
		successButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Toastr.success("Sucess", getMessageText());
			}
		});
		centerPanel.add(successButton);

		// info
		Button infoButton = new Button("Toastr.info()");
		infoButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Toastr.info("Info", getMessageText());
			}
		});
		centerPanel.add(infoButton);

		// warn
		Button warningButton = new Button("Toastr.warn()");
		warningButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Toastr.warn("Warning", getMessageText());
			}
		});
		centerPanel.add(warningButton);

		// error
		Button errorButton = new Button("Toastr.error()");
		errorButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Toastr.error("Error", getMessageText());
			}
		});
		centerPanel.add(errorButton);

		grid.setWidget(1, 1, centerPanel);
		grid.getCellFormatter().setWidth(1, 1, "80%");

		return grid;
	}

	private String getSetCloseButtonCode() {
		return "Toastr.setCloseButton(" + Boolean.toString(hasCloseButton) + ")";
	}

	private String getSetTapToDismissCode() {
		return "Toastr.setTapToDismiss(" + Boolean.toString(hasTapToDismiss) + ")";
	}

	private String getSetShowMethodCode() {
		return "Toastr.setShowMethod(" + showMethod.toString() + ")";
	}

	private String getsetPositionCode() {
		return "Toastr.setPosition(" + position.toString() + ")";
	}

	private String getSetHideMethodCode() {
		return "Toastr.setHideMethod(" + hideMethod.toString() + ")";
	}

	private Panel createRatingPanel() {

		ScrollPanel panel = new ScrollPanel();
		FlexTable grid = new FlexTable();
		panel.add(grid);
		grid.setCellSpacing(20);
		int row = 0;

		// full stars
		grid.setWidget(row, 0, new HTML("<h2>Full stars</h2>"));

		FullRatingWidget ratingWidgetFull = new FullRatingWidget();
		ratingWidgets.add(ratingWidgetFull);
		grid.setWidget(row, 1, ratingWidgetFull);

		final Label ratingLabelFull = new Label("0");
		ratingLabelFull.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelFull);

		ratingWidgetFull.addValueChangeHandler(new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				GWT.log("rating: " + event.getValue());
				ratingLabelFull.setText("" + event.getValue());
				updateSumWidgets();
			}
		});
		row++;

		addCodeExample(grid, row,
				"FullRatingWidget rating = new FullRatingWidget();\n\nrating.addValueChangeHandler(new ValueChangeHandler<Integer>() {\n	public void onValueChange(ValueChangeEvent<Integer> event) {\n		GWT.log(\"rating: \" + event.getValue());\n	}\n});");
		row++;

		// full stars with clear
		grid.setWidget(row, 0, new HTML("<h2>Full stars (with clear)</h2>"));

		FullRatingWidget ratingWidgetFullClear = new FullRatingWidget(false, true);
		ratingWidgets.add(ratingWidgetFullClear);
		grid.setWidget(row, 1, ratingWidgetFullClear);

		final Label ratingLabelFullClear = new Label("0");
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

		addCodeExample(grid, row, "FullRatingWidget rating = new FullRatingWidget(false, true);\n\n[...]");
		row++;

		// half stars
		grid.setWidget(row, 0, new HTML("<h2>Half stars</h2>"));

		HalfRatingWidget ratingWidgetHalf = new HalfRatingWidget();
		ratingWidgets.add(ratingWidgetHalf);
		grid.setWidget(row, 1, ratingWidgetHalf);

		final Label ratingLabelHalf = new Label("0");
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

		addCodeExample(grid, row,
				"HalfRatingWidget rating = new HalfRatingWidget();\n\nrating.addValueChangeHandler(new ValueChangeHandler<Float>() {\n	public void onValueChange(ValueChangeEvent<Float> event) {\n		GWT.log(\"rating: \" + event.getValue());\n	}\n});");
		row++;

		// half stars (large)
		grid.setWidget(row, 0, new HTML("<h2>Half stars (large)</h2>"));

		HalfRatingWidget ratingWidgetHalfLarge = new HalfRatingWidget();
		ratingWidgetHalfLarge.setResources((LargeRatingWidgetResources) GWT.create(LargeRatingWidgetResources.class));
		ratingWidgets.add(ratingWidgetHalfLarge);
		grid.setWidget(row, 1, ratingWidgetHalfLarge);

		final Label ratingLabelHalfLarge = new Label("0");
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

		addCodeExample(grid, row, "HalfRatingWidget rating = new HalfRatingWidget();\n\nrating.setResources((LargeRatingWidgetResources) GWT.create(LargeRatingWidgetResources.class));");
		row++;

		// half stars (small)
		grid.setWidget(row, 0, new HTML("<h2>Half stars (small)</h2>"));

		HalfRatingWidget ratingWidgetHalfSmall = new HalfRatingWidget();
		ratingWidgetHalfSmall.setResources((SmallRatingWidgetResources) GWT.create(SmallRatingWidgetResources.class));
		ratingWidgets.add(ratingWidgetHalfSmall);
		grid.setWidget(row, 1, ratingWidgetHalfSmall);

		final Label ratingLabelHalfSmall = new Label("0");
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

		addCodeExample(grid, row, "HalfRatingWidget rating = new HalfRatingWidget();\n\nrating.setResources((SmallRatingWidgetResources) GWT.create(LargeRatingWidgetResources.class));");
		row++;

		// full stars (readonly)
		grid.setWidget(row, 0, new HTML("<h2>Full stars (readonly)</h2>"));

		ratingWidgetSumFull = new FullRatingWidget(true);
		grid.setWidget(row, 1, ratingWidgetSumFull);

		ratingLabelSumFull = new Label("0");
		ratingLabelSumFull.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelSumFull);
		row++;

		addCodeExample(grid, row, "FullRatingWidget rating = new FullRatingWidget(true);\n\n[...]");
		row++;

		// half stars (readonly)
		grid.setWidget(row, 0, new HTML("<h2>Half stars (readonly)</h2>"));

		ratingWidgetSumHalf = new HalfRatingWidget(true, false);
		grid.setWidget(row, 1, ratingWidgetSumHalf);

		ratingLabelSumHalf = new Label("0");
		ratingLabelSumHalf.setWidth("4em");
		grid.setWidget(row, 2, ratingLabelSumHalf);
		row++;

		addCodeExample(grid, row, "HalfRatingWidget rating = new HalfRatingWidget(true);\n\n[...]");

		return panel;

	}
}

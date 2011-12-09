/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.util.Html;
import org.geomajas.gwt.client.util.HtmlBuilder;
import org.geomajas.gwt.client.util.WidgetLayout;
import org.geomajas.layer.feature.Feature;
import org.geomajas.plugin.staticsecurity.command.dto.GetUsersRequest;
import org.geomajas.plugin.staticsecurity.command.dto.GetUsersResponse;
import org.geomajas.widget.utility.gwt.client.widget.InScreenWindow;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.command.dto.AssignTaskRequest;
import org.ktunaxa.referral.server.command.dto.GetReferralResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;

/**
 * Implementation of {@link AbstractCollapsibleListBlock} that handles {@link TaskDto} type objects. Instances of this
 * class will form the list of tasks on task tabs. Each block can collapse and expand. When collapsed only the
 * task title is visible.
 *
 * @author Pieter De Graef
 * @author Joachim Van der Auwera
 */
public class TaskBlock extends AbstractCollapsibleListBlock<TaskDto> {

	private static final String BLOCK_STYLE = "taskBlock";
	private static final String BLOCK_CONTENT_STYLE = "taskBlockContent";
	private static final String BLOCK_CONTENT_TABLE_STYLE = "taskBlockContentTable";
	private static final String TITLE_STYLE = "taskBlockTitle";
	private static final String BLOCK_STYLE_COLLAPSED = "taskBlockCollapsed";
	private static final String TITLE_STYLE_COLLAPSED = "taskBlockTitleCollapsed";
	private static final String STYLE_VARIABLE_NAME = "text-align:right; width:120px";
	private static final String STYLE_VARIABLE_VALUE = "text-align:left";

	private static final String IMAGE_MINIMIZE = "[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/minimize.gif";
	private static final String IMAGE_MAXIMIZE = "[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/maximize.gif";

	private static final String IMAGE_CLAIM = "[ISOMORPHIC]/images/task/claim.png";
	private static final String IMAGE_START = "[ISOMORPHIC]/images/task/start.png";
	private static final String IMAGE_ASSIGN = "[ISOMORPHIC]/images/task/assign.png";

	private static final String FIELD_LABEL = "label";
	private static final String FIELD_VALUE = "value";

	private static final int DISABLED_OPACITY = 60;

	private HLayout title;

	private HTMLFlow content;

	private Img titleImage = new Img(IMAGE_MINIMIZE, 16, 16);
	private IButton startButton = new IButton();
	private IButton claimButton = new IButton();

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public TaskBlock(TaskDto task) {
		super(task);
		buildGui(task);
	}

	// ------------------------------------------------------------------------
	// CollapsableBlock implementation:
	// ------------------------------------------------------------------------

	/** Expand the task block, displaying everything. */
	public void expand() {
		setStyleName(BLOCK_STYLE);
		title.setStyleName(TITLE_STYLE);
		titleImage.setSrc(IMAGE_MINIMIZE);
		content.setVisible(true);
	}

	/** Collapse the task block, leaving only the title visible. */
	public void collapse() {
		setStyleName(BLOCK_STYLE_COLLAPSED);
		title.setStyleName(TITLE_STYLE_COLLAPSED);
		titleImage.setSrc(IMAGE_MAXIMIZE);
		content.setVisible(false);
	}

	/**
	 * Search the task for a given text string. This method will search through the title, content, checkedContent
	 * and name of the user.
	 * 
	 * @param text
	 *            The text to search for
	 * @return Returns true if the text has been found somewhere.
	 */
	public boolean containsText(String text) {
		if (text != null) {
			String lcText = text.toLowerCase();
			String compare;
			compare = getObject().getName();
			if (null != compare && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getDescription();
			if (null != compare && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getAssignee();
			if (null != compare && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			for (String test : getObject().getVariables().values()) {
				if (null != test && test.toLowerCase().contains(lcText)) {
					return true;
				}
			}
		}
		return false;
	}

	public Button getEditButton() {
		return null;
	}

	@Override
	public void setDisabled(boolean disabled) {
		super.setDisabled(disabled);
		if (disabled) {
			this.setOpacity(DISABLED_OPACITY);
			this.content.setOpacity(DISABLED_OPACITY);
		}
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private void buildGui(final TaskDto task) {
		Map<String, String> variables = task.getVariables();

		setStyleName(BLOCK_STYLE);

		title = new HLayout(LayoutConstant.MARGIN_LARGE);
		title.setSize(LayoutConstant.BLOCK_TITLE_WIDTH, LayoutConstant.BLOCK_TITLE_HEIGHT);
		title.setLayoutLeftMargin(LayoutConstant.MARGIN_LARGE);
		title.setStyleName(TITLE_STYLE);
		title.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (content.isVisible()) {
					collapse();
				} else {
					expand();
				}
			}
		});
		title.setCursor(Cursor.HAND);
		titleImage.setLayoutAlign(VerticalAlignment.CENTER);
		title.addMember(titleImage);
		HTMLFlow titleText = new HTMLFlow("<div class='taskBlockTitleText'>" +
				variables.get(KtunaxaBpmConstant.VAR_REFERRAL_ID) +
				": " + HtmlBuilder.htmlEncode(task.getName()) +
				"</div>");
		titleText.setSize(LayoutConstant.BLOCK_TITLE_WIDTH, LayoutConstant.BLOCK_TITLE_HEIGHT);
		title.addMember(titleText);
		addMember(title);

		HLayout infoLayout = new HLayout(LayoutConstant.MARGIN_SMALL);
		infoLayout.setLayoutRightMargin(LayoutConstant.MARGIN_SMALL);
		infoLayout.setLayoutTopMargin(LayoutConstant.MARGIN_SMALL);
		HTMLFlow info = new HTMLFlow(
				HtmlBuilder.divClassHtmlContent("taskBlockInfo",
				HtmlBuilder.htmlEncode(task.getDescription()) +
				"<br />Referral: " + 
				HtmlBuilder.tagClass(Html.Tag.SPAN, "taskBlockInfoBold", 
						variables.get(KtunaxaBpmConstant.VAR_REFERRAL_NAME))));
		info.setSize(LayoutConstant.BLOCK_INFO_WIDTH, LayoutConstant.BLOCK_INFO_HEIGHT);
		infoLayout.addMember(info);
		infoLayout.addMember(new LayoutSpacer());

		final String me = UserContext.getInstance().getUser();

		startButton.setIcon(IMAGE_START);
		startButton.setShowDisabledIcon(true);
		startButton.setIconWidth(LayoutConstant.ICON_BUTTON_LARGE_ICON_WIDTH);
		startButton.setIconHeight(LayoutConstant.ICON_BUTTON_LARGE_ICON_HEIGHT);
		startButton.setWidth(LayoutConstant.ICON_BUTTON_LARGE_WIDTH);
		startButton.setHeight(LayoutConstant.ICON_BUTTON_LARGE_HEIGHT);
		startButton.setPrompt("Start");
		startButton.setHoverWidth(LayoutConstant.ICON_BUTTON_LARGE_HOVER_WIDTH);
		startButton.setLayoutAlign(VerticalAlignment.CENTER);
		startButton.setShowRollOver(false);
		setStartButtonStatus(task);
		startButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent clickEvent) {
				assign(getObject(), me, true);
			}
		});
		infoLayout.addMember(startButton);

		IButton assignButton = new IButton();
		assignButton.setIcon(IMAGE_ASSIGN);
		assignButton.setShowDisabledIcon(true);
		assignButton.setIconWidth(LayoutConstant.ICON_BUTTON_LARGE_ICON_WIDTH);
		assignButton.setIconHeight(LayoutConstant.ICON_BUTTON_LARGE_ICON_HEIGHT);
		assignButton.setWidth(LayoutConstant.ICON_BUTTON_LARGE_WIDTH);
		assignButton.setHeight(LayoutConstant.ICON_BUTTON_LARGE_HEIGHT);
		assignButton.setPrompt("Assign");
		assignButton.setHoverWidth(LayoutConstant.ICON_BUTTON_LARGE_HOVER_WIDTH);
		assignButton.setLayoutAlign(VerticalAlignment.CENTER);
		assignButton.setShowRollOver(false);
		if (!UserContext.getInstance().isReferralManager()) {
			// disable when already assigned
			assignButton.setDisabled(true);
		}
		assignButton.addClickHandler(new ClickHandler() {

			public void onClick(final ClickEvent clickEvent) {
				GwtCommand command = new GwtCommand(GetUsersRequest.COMMAND);
				GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetUsersResponse>() {
					public void execute(GetUsersResponse response) {
						assignWindow(task, response, clickEvent);
					}
				});
			}
		});
		infoLayout.addMember(assignButton);

		claimButton.setIcon(IMAGE_CLAIM);
		claimButton.setShowDisabledIcon(true);
		claimButton.setIconWidth(LayoutConstant.ICON_BUTTON_LARGE_ICON_WIDTH);
		claimButton.setIconHeight(LayoutConstant.ICON_BUTTON_LARGE_ICON_HEIGHT);
		claimButton.setWidth(LayoutConstant.ICON_BUTTON_LARGE_WIDTH);
		claimButton.setHeight(LayoutConstant.ICON_BUTTON_LARGE_HEIGHT);
		claimButton.setPrompt("Claim");
		claimButton.setHoverWidth(LayoutConstant.ICON_BUTTON_LARGE_HOVER_WIDTH);
		claimButton.setLayoutAlign(VerticalAlignment.CENTER);
		claimButton.setShowRollOver(false);
		claimButton.setDisabled(task.isHistory() | (null != task.getAssignee()));
		claimButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent clickEvent) {
				assign(getObject(), me, false);
			}
		});
		infoLayout.addMember(claimButton);
		addMember(infoLayout);
		List<String> rows = new ArrayList<String>();
		String engagementLevel = variables.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL);
		if (task.isHistory()) {
			rows.add(HtmlBuilder.trHtmlContent(
					HtmlBuilder.tdStyle(STYLE_VARIABLE_NAME, "Assignee: "),
					HtmlBuilder.tdStyle(STYLE_VARIABLE_VALUE, task.getAssignee())));
			rows.add(HtmlBuilder.trHtmlContent(
					HtmlBuilder.tdStyle(STYLE_VARIABLE_NAME, "Started: "),
					HtmlBuilder.tdStyle(STYLE_VARIABLE_VALUE, task.getStartTime() + ""))); // NOSONAR cfr GWT
			rows.add(HtmlBuilder.trHtmlContent(
					HtmlBuilder.tdStyle(STYLE_VARIABLE_NAME, "Assignee: "),
					HtmlBuilder.tdStyle(STYLE_VARIABLE_VALUE, task.getEndTime() + ""))); // NOSONAR cfr GWT
		} else {
			rows.add(HtmlBuilder.trHtmlContent(
					HtmlBuilder.tdStyle(STYLE_VARIABLE_NAME, "Assignee: "),
					HtmlBuilder.tdStyle(STYLE_VARIABLE_VALUE, task.getAssignee())));
			rows.add(HtmlBuilder.trHtmlContent(
					HtmlBuilder.tdStyle(STYLE_VARIABLE_NAME, "Created: "),
					HtmlBuilder.tdStyle(STYLE_VARIABLE_VALUE, task.getCreateTime() + ""))); // NOSONAR cfr GWT
			rows.add(HtmlBuilder.trHtmlContent(
					HtmlBuilder.tdStyle(STYLE_VARIABLE_NAME, "Completion deadline: "),
					HtmlBuilder.tdStyle(STYLE_VARIABLE_VALUE,
							variables.get(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE))));
			rows.add(HtmlBuilder.trHtmlContent(
					HtmlBuilder.tdStyle(STYLE_VARIABLE_NAME, "E-mail: "),
					HtmlBuilder.tdStyle(STYLE_VARIABLE_VALUE, variables.get(KtunaxaBpmConstant.VAR_EMAIL))));
		}
		if (null != engagementLevel) {
			String engagementContent = engagementLevel + " (prov " +
					variables.get(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL) + ")";
			rows.add(HtmlBuilder.trHtmlContent(
					HtmlBuilder.tdStyle(STYLE_VARIABLE_NAME, "Engagement level: "),
					HtmlBuilder.tdStyle(STYLE_VARIABLE_VALUE, engagementContent)));
		}
// 		Simple 'list to array' code block. Exact array.length unknown until after 
//		the engagementLevel has been checked for null.
		String[] array = new String[rows.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = rows.get(i);
		} 
		String htmlContent = HtmlBuilder.tableClassHtmlContent(BLOCK_CONTENT_TABLE_STYLE, array);
		content = new HTMLFlow(htmlContent);
		content.setStyleName(BLOCK_CONTENT_STYLE);
		content.setWidth100();
		addMember(content);
	}

	private void assignWindow(TaskDto task, GetUsersResponse response, ClickEvent clickEvent) {
		final Window assignWindow = new InScreenWindow();
		assignWindow.setTitle("Assign task for " + task.getVariables().get(KtunaxaBpmConstant.VAR_REFERRAL_ID));
		assignWindow.setAutoSize(true);
		assignWindow.setCanDragReposition(true);
		assignWindow.setCanDragResize(false);
		assignWindow.setShowMinimizeButton(false);
		assignWindow.setModalMaskOpacity(WidgetLayout.modalMaskOpacity);
		assignWindow.setShowModalMask(true);
		assignWindow.setIsModal(true);
		assignWindow.addCloseClickHandler(new CloseClickHandler() {
			public void onCloseClick(CloseClientEvent closeClientEvent) {
				assignWindow.destroy();
			}
		});
		assignWindow.setLeft(clickEvent.getX());
		assignWindow.setTop(clickEvent.getY());
		VLayout layout = new VLayout(WidgetLayout.marginLarge);

		HTMLFlow label = new HTMLFlow(HtmlBuilder.htmlEncode(task.getName()));
		label.setWidth100();
		layout.addMember(label);

		// add select box with users
		final SelectItem users = new SelectItem("User", "User");
		users.setDefaultToFirstOption(true);
		users.setOptionDataSource(getUsersSelectDataSource(response));
		users.setDisplayField(FIELD_LABEL);
		users.setValueField(FIELD_VALUE);
		DynamicForm usersForm = new DynamicForm();
		usersForm.setFields(users);
		layout.addMember(usersForm);

		HLayout buttons = new HLayout(WidgetLayout.marginSmall);
		buttons.setWidth100();
		buttons.setHeight(1);
		LayoutSpacer spacer = new LayoutSpacer();
		spacer.setWidth(60);
		buttons.addMember(spacer);
		Button assignButton = new Button("Assign");
		assignButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				assign(getObject(), users.getValueAsString(), false);
				assignWindow.destroy();
			}
		});
		buttons.addMember(assignButton);
		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				assignWindow.destroy();
			}
		});
		buttons.addMember(cancelButton);
		layout.addMember(buttons);

		assignWindow.addItem(layout);
		assignWindow.draw();
	}

	private DataSource getUsersSelectDataSource(GetUsersResponse response) {
		DataSource dataSource = new DataSource();
		dataSource.setClientOnly(true);
		DataSourceField label = new DataSourceTextField(FIELD_LABEL);
		DataSourceField regex = new DataSourceTextField(FIELD_VALUE);
		dataSource.setFields(label, regex);

		String me = UserContext.getInstance().getUser();
		for (String user : response.getUsers()) {
			if (!me.equals(user)) {
				Record record = new Record();
				record.setAttribute(FIELD_LABEL, user);
				record.setAttribute(FIELD_VALUE, user);
				dataSource.addData(record);
			}
		}

		return dataSource;
	}

	private void setClaimButtonStatus(TaskDto task) {
		// disable when history or already assigned
		claimButton.setDisabled(task.isHistory() | (null != task.getAssignee()));
	}

	private void setStartButtonStatus(TaskDto task) {
		String me = UserContext.getInstance().getUser();
		// disable when history or assigned to someone else
		startButton.setDisabled(task.isHistory() | (null != task.getAssignee() && !me.equals(task.getAssignee())));
	}

	private void assign(final TaskDto task, final String assignee, final boolean start) {
		AssignTaskRequest request = new AssignTaskRequest();
		request.setTaskId(task.getId());
		request.setAssignee(assignee);
		GwtCommand command = new GwtCommand(AssignTaskRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<GetReferralResponse>() {

			public void execute(GetReferralResponse response) {
				setStartButtonStatus(task);
				setClaimButtonStatus(task);
				if (start) {
					start(task, response.getReferral());
				} else {
					TaskBlock.this.setDisabled(true);
				}
			}
		});
	}

	private void start(TaskDto task, Feature referral) {
		MapLayout mapLayout = MapLayout.getInstance();
		mapLayout.setReferralAndTask(referral, task);
	}
}

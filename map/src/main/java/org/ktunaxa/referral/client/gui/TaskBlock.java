/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.util.HtmlBuilder;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedEvent;
import org.ktunaxa.referral.client.referral.event.CurrentReferralChangedHandler;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.command.dto.AssignTaskRequest;
import org.ktunaxa.referral.server.command.dto.GetReferralResponse;
import org.ktunaxa.referral.server.dto.TaskDto;

import java.util.Map;

/**
 * Implementation of the CollapsableBlock abstraction that handles {@link TaskDto} type objects. Instances of this
 * class will form the list of tasks on task tabs. Each block can collapse and expand. When collapsed only the
 * task title is visible.
 *
 * @author Pieter De Graef
 * @author Joachim Van der Auwera
 */
public class TaskBlock extends AbstractCollapsibleListBlock<TaskDto> {

	private static final String TEXT_ALIGN_LEFT = "text-align:left";
	private static final String TEXT_ALIGN_RIGHT = "text-align:right";
	private static final String BLOCK_STYLE = "taskBlock";
	private static final String TITLE_STYLE = "taskBlockTitle";
	private static final String BLOCK_STYLE_COLLAPSED = "taskBlockCollapsed";
	private static final String TITLE_STYLE_COLLAPSED = "taskBlockTitleCollapsed";

	private static final String IMAGE_MINIMIZE = "[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/minimize.gif";
	private static final String IMAGE_MAXIMIZE = "[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/maximize.gif";

	private static final String IMAGE_CLAIM = "[ISOMORPHIC]/images/task/claim.png";
	private static final String IMAGE_START = "[ISOMORPHIC]/images/task/start.png";
	private static final String IMAGE_ASSIGN = "[ISOMORPHIC]/images/task/assign.png";

	private HLayout title;

	private HTMLFlow content;

	private Img titleImage = new Img(IMAGE_MINIMIZE, 16, 16);
	private IButton startButton = new IButton();
	private IButton claimButton = new IButton();
	private boolean needToDisplayCurrentTask;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public TaskBlock(TaskDto task) {
		super(task);
		buildGui(task);
		MapLayout.getInstance().addCurrentReferralChangedHandler(new CurrentReferralChangedHandler() {
			
			public void onCurrentReferralChanged(CurrentReferralChangedEvent event) {
				if (needToDisplayCurrentTask) {
					MapLayout.getInstance().focusCurrentTask();
				}
				needToDisplayCurrentTask = false;
			}
		});
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
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getDescription();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
		}
		return false;
	}

	public Button getEditButton() {
		return null;
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private void buildGui(TaskDto task) {
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
		HTMLFlow info = new HTMLFlow("<div class='taskBlockInfo'>" +
				HtmlBuilder.htmlEncode(task.getDescription()) +
				"<br />Referral: " + HtmlBuilder.htmlEncode(variables.get(KtunaxaBpmConstant.VAR_REFERRAL_NAME)) +
				"</div>");
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
		if (!UserContext.getInstance().isReferralAdmin()) {
			// disable when already assigned
			assignButton.setDisabled(true);
		}
		assignButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				SC.say("assign task, temp to KtBpmAdmin " + getObject().getId());
				// @todo select user
				assign(getObject(), me, false); // @todo assign selected user
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
		String[] rows = null;
		if (task.isHistory()) {
			rows = new String[4];
			rows[0] = HtmlBuilder.trHtmlContent(new String[] {
							HtmlBuilder.tdStyle(TEXT_ALIGN_RIGHT, "Assignee: "),
							HtmlBuilder.tdStyle(TEXT_ALIGN_LEFT, task.getAssignee())
						});
			rows[1] = HtmlBuilder.trHtmlContent(new String[] {
							HtmlBuilder.tdStyle(TEXT_ALIGN_RIGHT, "Started: "),
							HtmlBuilder.tdStyle(TEXT_ALIGN_LEFT, task.getStartTime() + "")
						});
			rows[2] = HtmlBuilder.trHtmlContent(new String[] {
							HtmlBuilder.tdStyle(TEXT_ALIGN_RIGHT, "Assignee: "),
							HtmlBuilder.tdStyle(TEXT_ALIGN_LEFT, task.getEndTime() + "")
						});
		} else {
			rows = new String[5];
			rows[0] = HtmlBuilder.trHtmlContent(new String[] {
					HtmlBuilder.tdStyle(TEXT_ALIGN_RIGHT, "Assignee: "),
					HtmlBuilder.tdStyle(TEXT_ALIGN_LEFT, task.getAssignee())
				});
			rows[1] = HtmlBuilder.trHtmlContent(new String[] {
					HtmlBuilder.tdStyle(TEXT_ALIGN_RIGHT, "Created: "),
					HtmlBuilder.tdStyle(TEXT_ALIGN_LEFT, task.getCreateTime() + "")
				});
			rows[2] = HtmlBuilder.trHtmlContent(new String[] {
					HtmlBuilder.tdStyle(TEXT_ALIGN_RIGHT, "Completion deadline: "),
					HtmlBuilder.tdStyle(TEXT_ALIGN_LEFT, variables.get(KtunaxaBpmConstant.VAR_COMPLETION_DEADLINE))
				});
			rows[3] = HtmlBuilder.trHtmlContent(new String[] {
					HtmlBuilder.tdStyle(TEXT_ALIGN_RIGHT, "E-mail: "),
					HtmlBuilder.tdStyle(TEXT_ALIGN_LEFT, variables.get(KtunaxaBpmConstant.VAR_EMAIL))
				});
		}
		String engagementLevel = variables.get(KtunaxaBpmConstant.VAR_ENGAGEMENT_LEVEL);
		if (null != engagementLevel) {
			String engagementContent = engagementLevel + " (prov " + 
			variables.get(KtunaxaBpmConstant.VAR_PROVINCE_ENGAGEMENT_LEVEL) + ")";
			rows[rows.length - 1] = HtmlBuilder.trHtmlContent(new String[] {
					HtmlBuilder.tdStyle(TEXT_ALIGN_RIGHT, "Engagement level: "),
					HtmlBuilder.tdStyle(TEXT_ALIGN_LEFT, engagementContent)
				});
		}
		String htmlContent = HtmlBuilder.tableClassHtmlContent("taskBlockContent", rows);
		content = new HTMLFlow(htmlContent);
		content.setWidth100();
		addMember(content);
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
				// @todo do I need to modify the state of the task in the list, possibly remove, refresh, whatever?
				if (start) {
					start(task, response.getReferral());
				} else {
					SC.ask("Do you want to claim the task?<br /><br />" +
							" You are currently logged in as "+ UserContext.getInstance().getUser(), 
							new BooleanCallback() {

						public void execute(Boolean value) {
							if (value != null && value) {
								MapLayout.getInstance().focusBpm();
							}
						}
					});
					
				}
			}
		});
	}

	private void start(TaskDto task, Feature referral) {
		MapLayout mapLayout = MapLayout.getInstance();
		needToDisplayCurrentTask = true;
		mapLayout.setReferralAndTask(referral, task); 
	}
}
/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.layer.feature.Feature;
import org.ktunaxa.bpm.KtunaxaBpmConstant;
import org.ktunaxa.referral.client.security.UserContext;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.command.dto.AssignTaskRequest;
import org.ktunaxa.referral.server.command.dto.ReferralResponse;
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

	private static final String BLOK_STYLE = "taskBlock";
	private static final String TITLE_STYLE = "taskBlockTitle";
	private static final String BLOK_STYLE_COLLAPSED = "taskBlockCollapsed";
	private static final String TITLE_STYLE_COLLAPSED = "taskBlockTitleCollapsed";

	private static final String IMAGE_MINIMIZE = "[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/minimize.gif";
	private static final String IMAGE_MAXIMIZE = "[ISOMORPHIC]/skins/ActivitiBlue/images/headerIcons/maximize.gif";

	private HLayout title;

	private HTMLFlow content;

	private Img titleImage = new Img(IMAGE_MINIMIZE, 16, 16);

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
		setStyleName(BLOK_STYLE);
		title.setStyleName(TITLE_STYLE);
		titleImage.setSrc(IMAGE_MINIMIZE);
		//infoLayout.setVisible(true);
		content.setVisible(true);
	}

	/** Collapse the task block, leaving only the title visible. */
	public void collapse() {
		setStyleName(BLOK_STYLE_COLLAPSED);
		title.setStyleName(TITLE_STYLE_COLLAPSED);
		titleImage.setSrc(IMAGE_MAXIMIZE);
		//infoLayout.setVisible(false);
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
		setStyleName(BLOK_STYLE);

		title = new HLayout(10);
		title.setSize("100%", "22");
		title.setLayoutLeftMargin(10);
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
		HTMLFlow titleText = new HTMLFlow("<div class='taskBlockTitleText'>" + task.getName() + "</div>");
		titleText.setSize("100%", "22");
		title.addMember(titleText);
		addMember(title);

		HLayout infoLayout = new HLayout(5);
		infoLayout.setLayoutRightMargin(5);
		infoLayout.setLayoutTopMargin(5);
		HTMLFlow info = new HTMLFlow("<div class='taskBlockInfo'>" + task.getDescription() + "</div>");
		info.setSize("100%", "24");
		infoLayout.addMember(info);
		infoLayout.addMember(new LayoutSpacer());

		final String me = UserContext.getInstance().getUser();

		Button startButton = new Button("Start");
		startButton.setLayoutAlign(VerticalAlignment.CENTER);
		startButton.setShowRollOver(false);
		if (null != task.getAssignee() && !me.equals(task.getAssignee())) {
			// disable when assigned to someone else
			startButton.setDisabled(true);
		}
		startButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				assign(getObject(), me, true);
			}
		});
		infoLayout.addMember(startButton);
		Button assignButton = new Button("Assign");
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
		Button claimButton = new Button("Claim");
		claimButton.setLayoutAlign(VerticalAlignment.CENTER);
		claimButton.setShowRollOver(false);
		if (null != task.getAssignee()) {
			// disable when already assigned
			claimButton.setDisabled(true);
		}
		claimButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				assign(getObject(), me, false);
			}
		});
		infoLayout.addMember(claimButton);

		addMember(infoLayout);

		Map<String, String> variables = task.getVariables();
		content = new HTMLFlow("<div class='taskBlockContent'>Assignee " + task.getAssignee()
				+ "<br />Created " + task.getCreateTime()
				+ "<br />Due " + task.getDueDate()
				+ "<br />Completion deadline" + variables.get(KtunaxaBpmConstant.REFERRAL_CONTEXT_COMPLETION_DEADLINE)
				+ "<br />Referral " + variables.get(KtunaxaBpmConstant.REFERRAL_CONTEXT_REFERRAL_ID)
				+ "<br />Description " + variables.get(KtunaxaBpmConstant.REFERRAL_CONTEXT_DESCRIPTION)
				+ "<br />E-mail " + variables.get(KtunaxaBpmConstant.REFERRAL_CONTEXT_EMAIL)
				+ "<br />Engagement level " + variables.get(KtunaxaBpmConstant.REFERRAL_CONTEXT_ENGAGEMENT_LEVEL)
				+ "</div>");
		content.setWidth100();
		addMember(content);
	}

	private void assign(final TaskDto task, final String assignee, final boolean start) {
		AssignTaskRequest request = new AssignTaskRequest();
		request.setTaskId(task.getId());
		request.setAssignee(assignee);
		GwtCommand command = new GwtCommand(AssignTaskRequest.COMMAND);
		command.setCommandRequest(request);
		GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<ReferralResponse>() {
			public void execute(ReferralResponse response) {
				// @todo do I need to modify the state of the task in the list, possibly remove, refresh, whatever?
				if (start) {
					start(task, response.getReferral());
				}
			}
		});
	}

	private void start(TaskDto task, Feature referral) {
		Canvas top = this.getParentElement();
		boolean topIsTop = false;
		while (!topIsTop) {
			Canvas canvas = top.getParentElement();
			if (null == canvas) {
				topIsTop = true;
			} else {
				top = canvas;
			}
		}
		if (top instanceof MapLayout) {
			MapLayout mapLayout = (MapLayout) top;
			mapLayout.setReferralAndTask(referral, task);
			mapLayout.focusCurrentTask();
		} else {
			throw new IllegalStateException("MapLayout is no longer at the top of the tree...");
		}
	}
}
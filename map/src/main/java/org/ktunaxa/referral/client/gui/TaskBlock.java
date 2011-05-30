/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Implementation of the CollapsableBlock abstraction that handles {@link TaskDto} type objects. Instances of this
 * class will form the list of tasks on task tabs. Each block can collapse and expand. When collapsed only the
 * task title is visible.
 *
 * @author Pieter De Graef
 * @author Joachim Van der Auwera
 */
public class TaskBlock extends AbstractCollapsibleListBlock<TaskDto> {

	private HLayout title;

	private HLayout infoLayout;

	private HTMLFlow content;

	private Button editButton;

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

	/** Expand the task block, displaying (almost) everything. */
	public void expand() {
		setStyleName("taskBlock");
		title.setStyleName("taskBlockTitle");
		infoLayout.setVisible(true);
		content.setVisible(true);
	}

	/** Collapse the task block, leaving only the title visible. */
	public void collapse() {
		setStyleName("taskBlockCollapsed");
		title.setStyleName("taskBlockTitleCollapsed");
		infoLayout.setVisible(false);
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
		return editButton;
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private void buildGui(TaskDto task) {
		setStyleName("taskBlock");

		title = new HLayout(10);
		title.setSize("100%", "22");
		title.setLayoutLeftMargin(10);
		title.setStyleName("taskBlockTitle");
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
		Img checked = new Img("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_remove_files.png", 16, 16);
		/*
		if (task.isIncludeInReport()) {
			checked.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_add_files.png");
		}
		*/
		checked.setLayoutAlign(VerticalAlignment.CENTER);
		title.addMember(checked);
		HTMLFlow titleText = new HTMLFlow("<div class='taskBlockTitleText'>" + task.getName() + "</div>");
		titleText.setSize("100%", "22");
		title.addMember(titleText);
		addMember(title);

		infoLayout = new HLayout(5);
		infoLayout.setLayoutRightMargin(5);
		infoLayout.setLayoutTopMargin(5);
		HTMLFlow info = new HTMLFlow("<div class='taskBlockInfo'>Assignee " + task.getAssignee() + " created "
				+ task.getCreateTime() + " due " + task.getDueDate() + "</div>");
		info.setSize("100%", "24");
		infoLayout.addMember(info);
		editButton = new Button("Do something");
		editButton.setLayoutAlign(VerticalAlignment.CENTER);
		infoLayout.addMember(new LayoutSpacer());
		infoLayout.addMember(editButton);
		addMember(infoLayout);

		content = new HTMLFlow("<div class='taskBlockContent'>" + task.getDescription() + "</div>");
		content.setWidth100();
		addMember(content);
	}
}
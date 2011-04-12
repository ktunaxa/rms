/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.dto.CommentDto;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;

/**
 * Implementation of the CollapsableBlock abstraction that handles {@link CommentDto} type objects. Instances of this
 * class will form the list of comments on the comment page. Each block can collapse and expand. When collapsed only the
 * comment title is visible.
 * 
 * @author Pieter De Graef
 */
public class CommentBlock extends AbstractCollapsibleListBlock<CommentDto> {

	private HLayout title;

	private HLayout infoLayout;

	private HTMLFlow content;

	private Button editButton;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public CommentBlock(CommentDto comment) {
		super(comment);
		buildGui(comment);
	}

	// ------------------------------------------------------------------------
	// CollapsableBlock implementation:
	// ------------------------------------------------------------------------

	/** Expand the comment block, displaying (almost) everything. */
	public void expand() {
		setStyleName("commentBlock");
		title.setStyleName("commentBlockTitle");
		infoLayout.setVisible(true);
		content.setVisible(true);
	}

	/** Collapse the comment block, leaving only the title visible. */
	public void collapse() {
		setStyleName("commentBlockCollapsed");
		title.setStyleName("commentBlockTitleCollapsed");
		infoLayout.setVisible(false);
		content.setVisible(false);
	}

	/**
	 * Search the comment for a given text string. This method will search through the title, content, checkedContent
	 * and name of the user.
	 * 
	 * @param text
	 *            The text to search for
	 * @return Returns true if the text has been found somewhere.
	 */
	public boolean containsText(String text) {
		if (text != null) {
			String lcText = text.toLowerCase();
			String compare = getObject().getTitle();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getContent();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getReportContent();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getCreatedBy();
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

	private void buildGui(CommentDto comment) {
		setStyleName("commentBlock");

		title = new HLayout(10);
		title.setSize("100%", "22");
		title.setLayoutLeftMargin(10);
		title.setStyleName("commentBlockTitle");
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
		if (comment.isIncludeInReport()) {
			checked.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_add_files.png");
		}
		checked.setLayoutAlign(VerticalAlignment.CENTER);
		title.addMember(checked);
		HTMLFlow titleText = new HTMLFlow("<div class='commentBlockTitleText'>" + comment.getTitle() + "</div>");
		titleText.setSize("100%", "22");
		title.addMember(titleText);
		addMember(title);

		infoLayout = new HLayout(5);
		infoLayout.setLayoutRightMargin(5);
		infoLayout.setLayoutTopMargin(5);
		HTMLFlow info = new HTMLFlow("<div class='commentBlockInfo'>Posted by " + comment.getCreatedBy() + " @ "
				+ comment.getCreationDate() + "</div>");
		info.setSize("100%", "24");
		infoLayout.addMember(info);
		editButton = new Button("Edit comment");
		editButton.setLayoutAlign(VerticalAlignment.CENTER);
		infoLayout.addMember(new LayoutSpacer());
		infoLayout.addMember(editButton);
		addMember(infoLayout);

		content = new HTMLFlow("<div class='commentBlockContent'>" + comment.getContent() + "</div>");
		content.setWidth100();
		addMember(content);
	}
}
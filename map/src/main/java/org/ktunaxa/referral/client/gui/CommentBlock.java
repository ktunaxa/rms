/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import java.util.Date;

import org.geomajas.layer.feature.attribute.AssociationValue;
import org.ktunaxa.referral.client.widget.attribute.AbstractAttributeBlock;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;

/**
 * Implementation of the {@link AbstractAttributeBlock} abstraction that handles comment type objects. Instances of this
 * class will form the list of comments on the comment page. Each block can collapse and expand. When collapsed only the
 * comment title is visible.
 * 
 * @author Pieter De Graef
 */
public class CommentBlock extends AbstractAttributeBlock {

	public static final String COMMENT_BLOCK_STYLE = "commentBlock";
	public static final String COMMENT_BLOCK_COLLAPSED_STYLE = "commentBlockCollapsed";
	public static final String COMMENT_BLOCK_TITLE_STYLE = "commentBlockTitle";
	public static final String COMMENT_BLOCK_TITLE_COLLAPSED_STYLE = "commentBlockTitleCollapsed";
	public static final String ICON_EDIT = "[ISOMORPHIC]/geomajas/osgeo/edit.png";
	public static final String ICON_REMOVE = "[ISOMORPHIC]/geomajas/silk/remove.png";
	public static final String ICON_ADD_FILES =
			"[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_add_files.png";
	public static final String ICON_REMOVE_FILES =
			"[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_remove_files.png";

	private HLayout title;

	private HTMLFlow content;

	private HTMLFlow titleText;

	private HTMLFlow info;

	private Img checked;

	private IButton editButton;

	private IButton deleteButton;

	// ------------------------------------------------------------------------
	// Constructors:
	// ------------------------------------------------------------------------

	public CommentBlock(AssociationValue value) {
		super(value);
		buildGui();
	}

	public String getDeleteMessage() {
		return "Are you sure you want to delete this comment ?";
	}
	
	// ------------------------------------------------------------------------
	// CollapsableBlock implementation:
	// ------------------------------------------------------------------------

	/** Expand the comment block, displaying (almost) everything. */
	public void expand() {
		setStyleName(COMMENT_BLOCK_STYLE);
		title.setStyleName(COMMENT_BLOCK_TITLE_STYLE);
		//infoLayout.setVisible(true);
		content.setVisible(true);
	}

	/** Collapse the comment block, leaving only the title visible. */
	public void collapse() {
		setStyleName(COMMENT_BLOCK_COLLAPSED_STYLE);
		title.setStyleName(COMMENT_BLOCK_TITLE_COLLAPSED_STYLE);
		//infoLayout.setVisible(false);
		content.setVisible(false);
	}

	@Override
	protected boolean evaluate(String filter) {
		if (filter != null) {
			String lcText = filter.toLowerCase();
			String compare = getCommentTitle();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getCommentContent();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getCommentReportContent();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getCommentCreatedBy();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
		}
		return false;
	}

	public IButton getEditButton() {
		return editButton;
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	private void buildGui() {
		setStyleName(COMMENT_BLOCK_STYLE);

		title = new HLayout(LayoutConstant.MARGIN_LARGE);
		title.setSize(LayoutConstant.BLOCK_TITLE_WIDTH, LayoutConstant.BLOCK_TITLE_HEIGHT);
		title.setLayoutLeftMargin(LayoutConstant.MARGIN_LARGE);
		title.setStyleName(COMMENT_BLOCK_TITLE_STYLE);
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
		checked = new Img(ICON_REMOVE_FILES, 16, 16);
		if (isCommentIncludeInReport()) {
			checked.setSrc(ICON_ADD_FILES);
		}
		checked.setLayoutAlign(VerticalAlignment.CENTER);
		title.addMember(checked);
		titleText = new HTMLFlow("<div class='commentBlockTitleText'>" + getCommentTitle() + "</div>");
		titleText.setSize(LayoutConstant.BLOCK_TITLE_WIDTH, LayoutConstant.BLOCK_TITLE_HEIGHT);
		title.addMember(titleText);
		addMember(title);

		HLayout infoLayout = new HLayout(LayoutConstant.MARGIN_SMALL);
		infoLayout.setLayoutRightMargin(LayoutConstant.MARGIN_SMALL);
		infoLayout.setLayoutTopMargin(LayoutConstant.MARGIN_SMALL);
		info = new HTMLFlow("<div class='commentBlockInfo'>Posted by " + getCommentCreatedBy() + " @ "
				+ getCommentCreationDate().toString() + "</div>");
		info.setSize(LayoutConstant.BLOCK_INFO_WIDTH, LayoutConstant.BLOCK_INFO_HEIGHT);
		infoLayout.addMember(info);
		LayoutSpacer space = new LayoutSpacer();
		space.setWidth(LayoutConstant.SPACER_LARGE);
		infoLayout.addMember(space);

		editButton = new IButton();
		editButton.setIcon(ICON_EDIT);
		editButton.setIconWidth(LayoutConstant.ICON_BUTTON_SMALL_ICON_WIDTH);
		editButton.setIconHeight(LayoutConstant.ICON_BUTTON_SMALL_ICON_HEIGHT);
		editButton.setWidth(LayoutConstant.ICON_BUTTON_SMALL_WIDTH);
		editButton.setHeight(LayoutConstant.ICON_BUTTON_SMALL_HEIGHT);
		editButton.setTooltip("Edit comment");
		infoLayout.addMember(editButton);
		
		deleteButton = new IButton();
		deleteButton.setIcon(ICON_REMOVE);
		deleteButton.setIconWidth(LayoutConstant.ICON_BUTTON_SMALL_ICON_WIDTH);
		deleteButton.setIconHeight(LayoutConstant.ICON_BUTTON_SMALL_ICON_HEIGHT);
		deleteButton.setWidth(LayoutConstant.ICON_BUTTON_SMALL_WIDTH);
		deleteButton.setHeight(LayoutConstant.ICON_BUTTON_SMALL_HEIGHT);
		deleteButton.setTooltip("Delete comment");
		deleteButton.setHoverWrap(false);
		infoLayout.addMember(deleteButton);
		addMember(infoLayout);

		content = new HTMLFlow("<div class='commentBlockContent'>" + getCommentContent() + "</div>");
		content.setWidth100();
		addMember(content);
	}
	
	private String getCommentTitle() {
		return (String) getValue().getAttributeValue(KtunaxaConstant.ATTRIBUTE_COMMENT_TITLE);
	}

	private String getCommentCreatedBy() {
		return (String) getValue().getAttributeValue(KtunaxaConstant.ATTRIBUTE_COMMENT_CREATED_BY);
	}

	private Date getCommentCreationDate() {
		return (Date) getValue().getAttributeValue(KtunaxaConstant.ATTRIBUTE_COMMENT_CREATION_DATE);
	}

	private String getCommentContent() {
		return (String) getValue().getAttributeValue(KtunaxaConstant.ATTRIBUTE_COMMENT_CONTENT);
	}

	private String getCommentReportContent() {
		return (String) getValue().getAttributeValue(KtunaxaConstant.ATTRIBUTE_COMMENT_REPORT_CONTENT);
	}

	private Boolean isCommentIncludeInReport() {
		return (Boolean) getValue().getAttributeValue(KtunaxaConstant.ATTRIBUTE_COMMENT_INCLUDE_IN_REPORT);
	}

	@Override
	protected boolean valueEquals(AbstractAttributeBlock other) {
		AssociationValue otherValue = other.getValue();
		AssociationValue value = getValue();
		if (otherValue == null || value == null) {
			return false;
		} else if (otherValue.getId().isEmpty() || value.getId().isEmpty()) {
			return value.getAttributeValue(KtunaxaConstant.ATTRIBUTE_COMMENT_TITLE).equals(
					otherValue.getAttributeValue(KtunaxaConstant.ATTRIBUTE_COMMENT_TITLE));
		} else {
			return otherValue.getId().getValue().equals(value.getId().getValue());
		}
	}


	@Override
	public void addEditHandler(ClickHandler clickHandler) {
		editButton.addClickHandler(clickHandler);
	}

	@Override
	public void addDeleteHandler(ClickHandler clickHandler) {
		deleteButton.addClickHandler(clickHandler);
	}

	@Override
	public void redrawValue() {
		titleText.setContents("<div class='commentBlockTitleText'>" + getCommentTitle() + "</div>");
		info.setContents("<div class='commentBlockInfo'>Posted by " + getCommentCreatedBy() + " @ "
				+ getCommentCreationDate().toString() + "</div>");
		content.setContents("<div class='commentBlockContent'>" + getCommentContent() + "</div>");
		if (isCommentIncludeInReport()) {
			checked.setSrc(ICON_ADD_FILES);
		} else {
			checked.setSrc(ICON_REMOVE_FILES);
		}
	}
}
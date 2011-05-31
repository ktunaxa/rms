/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import java.util.Date;

import org.geomajas.layer.feature.attribute.AssociationValue;
import org.ktunaxa.referral.client.widget.attribute.AbstractAttributeBlock;

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
 * Block that shows document information.
 * 
 * @author Jan De Moerloose
 * 
 */
public class DocumentBlock extends AbstractAttributeBlock {

	private HLayout title;

	private HLayout infoLayout;

	private HTMLFlow content;
	
	private HTMLFlow titleText;
	
	private HTMLFlow info;

	private Button editButton;
	
	private Img checked;

	public DocumentBlock(AssociationValue value) {
		super(value);
		buildGui();
	}

	@Override
	public void expand() {
		setStyleName("documentBlock");
		title.setStyleName("documentBlockTitle");
		infoLayout.setVisible(true);
		content.setVisible(true);
	}

	@Override
	public void collapse() {
		setStyleName("documentBlockCollapsed");
		title.setStyleName("documentBlockTitleCollapsed");
		infoLayout.setVisible(false);
		content.setVisible(false);
	}
	
	public void redrawValue() {
		titleText.setContents("<div class='documentBlockTitleText'>" + getDocumentTitle() + "</div>");
		info.setContents("<div class='documentBlockInfo'>Posted by " + getAddedBy() + " @ " + getAdditionDate()
				+ "</div>");
		content.setContents("<div class='documentBlockType'>" + getTypeTitle() + "</div>");
		if (isIncludeInReport()) {
			checked.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_add_files.png");
		} else {
			checked.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_remove_files.png");
		}
	}

	private void buildGui() {
		setStyleName("documentBlock");

		title = new HLayout(10);
		title.setSize("100%", "22");
		title.setLayoutLeftMargin(10);
		title.setStyleName("documentBlockTitle");
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
		checked = new Img("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_remove_files.png", 16, 16);
		if (isIncludeInReport()) {
			checked.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_add_files.png");
		}
		checked.setLayoutAlign(VerticalAlignment.CENTER);
		title.addMember(checked);
		titleText = new HTMLFlow("<div class='documentBlockTitleText'>" + getDocumentTitle() + "</div>");
		titleText.setSize("100%", "22");
		title.addMember(titleText);
		addMember(title);

		infoLayout = new HLayout(5);
		infoLayout.setHeight(24);
		infoLayout.setLayoutRightMargin(5);
		infoLayout.setLayoutTopMargin(5);
		info = new HTMLFlow("<div class='documentBlockInfo'>Posted by " + getAddedBy() + " @ "
				+ getAdditionDate() + "</div>");
		info.setSize("*", "24");
		infoLayout.addMember(info);

		editButton = new Button("Edit document");
		LayoutSpacer space = new LayoutSpacer();
		space.setWidth(20);
		infoLayout.addMember(space);
		infoLayout.addMember(editButton);
		addMember(infoLayout);

		content = new HTMLFlow("<div class='documentBlockType'>" + getTypeTitle() + "</div>");
		content.setHeight(20);
		content.setWidth100();
		addMember(content);
	}

	private String getTypeTitle() {
		AssociationValue type = (AssociationValue) getValue().getAttributeValue("type");
		if (type != null) {
			return (String) type.getAttributeValue("title");
		}
		return "";
	}

	private String getDocumentTitle() {
		return (String) getValue().getAttributeValue("title");
	}

	private String getDescription() {
		return (String) getValue().getAttributeValue("description");
	}

	private Date getAdditionDate() {
		return (Date) getValue().getAttributeValue("additionDate");
	}

	private String getAddedBy() {
		return (String) getValue().getAttributeValue("addedBy");
	}

	private Boolean isIncludeInReport() {
		return (Boolean) getValue().getAttributeValue("includeInReport");
	}

	@Override
	protected boolean evaluate(String filter) {
		if (filter != null) {
			String lcText = filter.toLowerCase();
			String compare = getDocumentTitle();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getDescription();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getAddedBy();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getTypeTitle();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addEditHandler(ClickHandler clickHandler) {
		editButton.addClickHandler(clickHandler);
	}

}

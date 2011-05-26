/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import java.util.Date;

import org.geomajas.layer.feature.attribute.AssociationValue;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.server.dto.DocumentDto;
import org.ktunaxa.referral.server.dto.DocumentTypeDto;

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
public class DocumentBlock extends AbstractCollapsibleListBlock<DocumentDto> {

	private HLayout title;

	private HLayout infoLayout;

	private HTMLFlow content;

	private Button editButton;

	public DocumentBlock(AssociationValue value) {
		this(valueToDto(value));

	}

	public DocumentBlock(DocumentDto document) {
		super(document);
		buildGui(document);
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

	@Override
	public boolean containsText(String text) {
		if (text != null) {
			String lcText = text.toLowerCase();
			String compare = getObject().getTitle();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getDescription();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getAddedBy();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
			compare = getObject().getType().getTitle();
			if (compare != null && compare.toLowerCase().contains(lcText)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Button getEditButton() {
		return editButton;
	}

	private void buildGui(DocumentDto document) {
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
		Img checked = new Img("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_remove_files.png", 16, 16);
		if (document.isIncludeInReport()) {
			checked.setSrc("[ISOMORPHIC]/skins/ActivitiBlue/images/MultiUploadItem/icon_add_files.png");
		}
		checked.setLayoutAlign(VerticalAlignment.CENTER);
		title.addMember(checked);
		HTMLFlow titleText = new HTMLFlow("<div class='documentBlockTitleText'>" + document.getTitle() + "</div>");
		titleText.setSize("100%", "22");
		title.addMember(titleText);
		addMember(title);

		infoLayout = new HLayout(5);
		infoLayout.setLayoutRightMargin(5);
		infoLayout.setLayoutTopMargin(5);
		HTMLFlow info = new HTMLFlow("<div class='documentBlockInfo'>Posted by " + document.getAddedBy() + " @ "
				+ document.getAdditionDate() + "</div>");
		info.setSize("100%", "24");
		infoLayout.addMember(info);

		editButton = new Button("Edit document");
		editButton.setLayoutAlign(VerticalAlignment.CENTER);
		infoLayout.addMember(new LayoutSpacer());
		infoLayout.addMember(editButton);
		addMember(infoLayout);

		content = new HTMLFlow("<div class='documentBlockType'>" + document.getType().getTitle() + "</div>");
		content.setWidth100();
		addMember(content);
		setHeight(100);
	}

	private static DocumentDto valueToDto(AssociationValue value) {
		DocumentDto dto = new DocumentDto();
		dto.setAddedBy((String) value.getAttributeValue("addedBy"));
		dto.setAdditionDate((Date) value.getAttributeValue("additionDate"));
		dto.setConfidential((Boolean) value.getAttributeValue("confidential"));
		dto.setDescription((String) value.getAttributeValue("description"));
		dto.setDocumentId((String) value.getAttributeValue("documentId"));
		dto.setId((Long) value.getId().getValue());
		dto.setIncludeInReport((Boolean) value.getAttributeValue("includeInReport"));
		dto.setTitle((String) value.getAttributeValue("title"));
		DocumentTypeDto documentTypeDto = new DocumentTypeDto();
		AssociationValue documentType = (AssociationValue) value.getAttributeValue("type");
		documentTypeDto.setDescription((String) documentType.getAttributeValue("description"));
		documentTypeDto.setId((Long) documentType.getId().getValue());
		documentTypeDto.setTitle((String) documentType.getAttributeValue("title"));
		dto.setType(documentTypeDto);
		return dto;
	}

}

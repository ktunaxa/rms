/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */
package org.ktunaxa.referral.client.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geomajas.gwt.client.widget.attribute.FeatureForm;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.attribute.AssociationValue;
import org.geomajas.layer.feature.attribute.OneToManyAttribute;
import org.ktunaxa.referral.client.widget.AbstractCollapsibleListBlock;
import org.ktunaxa.referral.client.widget.DetailView;
import org.ktunaxa.referral.client.widget.ListLayout;
import org.ktunaxa.referral.client.widget.ListView;
import org.ktunaxa.referral.server.dto.DocumentDto;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.events.ItemChangedHandler;

/**
 * Form for editing documents in mapping dashboard.
 * 
 * @author Jan De Moerloose
 */
public class DocumentsForm implements FeatureForm<Canvas> {

	private ListLayout<DocumentDto> documentsLayout;

	public DocumentsForm() {
		Map<String, Comparator<AbstractCollapsibleListBlock<DocumentDto>>> sortAttributes;
		sortAttributes = new HashMap<String, Comparator<AbstractCollapsibleListBlock<DocumentDto>>>();
		sortAttributes.put("title", new TitleComparator());
		sortAttributes.put("description", new DescriptionComparator());
		sortAttributes.put("type", new TypeComparator());
		ListView<DocumentDto> listView = new ListView<DocumentDto>(sortAttributes);
		DetailView<DocumentDto> detailView = new DetailView<DocumentDto>();
		documentsLayout = new ListLayout<DocumentDto>(listView, detailView);
		documentsLayout.setWidth100();
	}

	public Canvas getWidget() {
		return documentsLayout;
	}

	public void setDisabled(boolean disabled) {

	}

	public boolean isDisabled() {
		return false;
	}

	public boolean validate() {
		return false;
	}

	public void addItemChangedHandler(ItemChangedHandler handler) {
	}

	public void toForm(String name, Attribute<?> attribute) {
		if (KtunaxaConstant.DOCUMENTS_ATTRIBUTE_NAME.equals(name)) {
			OneToManyAttribute oneToMany = (OneToManyAttribute) attribute;
			List<AbstractCollapsibleListBlock<DocumentDto>> list = 
				new ArrayList<AbstractCollapsibleListBlock<DocumentDto>>();
			for (AssociationValue value : oneToMany.getValue()) {
				list.add(new DocumentBlock(value));
			}
			documentsLayout.populate(list);
		}
	}

	public void fromForm(String name, Attribute<?> attribute) {
		// TODO Auto-generated method stub

	}

	public void clear() {
		// TODO Auto-generated method stub

	}

	/**
	 * Compares documents by type.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class TypeComparator implements Comparator<AbstractCollapsibleListBlock<DocumentDto>> {

		public int compare(AbstractCollapsibleListBlock<DocumentDto> o1, AbstractCollapsibleListBlock<DocumentDto> o2) {
			return o1.getObject().getType().getTitle().compareTo(o2.getObject().getType().getTitle());
		}

	}

	/**
	 * Compares documents by description.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class DescriptionComparator implements Comparator<AbstractCollapsibleListBlock<DocumentDto>> {

		public int compare(AbstractCollapsibleListBlock<DocumentDto> o1, AbstractCollapsibleListBlock<DocumentDto> o2) {
			return o1.getObject().getDescription().compareTo(o2.getObject().getDescription());
		}

	}

	/**
	 * Compares documents by title.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class TitleComparator implements Comparator<AbstractCollapsibleListBlock<DocumentDto>> {

		public int compare(AbstractCollapsibleListBlock<DocumentDto> o1, AbstractCollapsibleListBlock<DocumentDto> o2) {
			return o1.getObject().getTitle().compareTo(o2.getObject().getTitle());
		}

	}

}

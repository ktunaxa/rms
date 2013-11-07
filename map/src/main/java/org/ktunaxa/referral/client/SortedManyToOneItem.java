package org.ktunaxa.referral.client;

import org.geomajas.configuration.AssociationAttributeInfo;
import org.geomajas.gwt.client.widget.attribute.AttributeProvider;
import org.geomajas.gwt.client.widget.attribute.DefaultManyToOneItem;
import org.geomajas.gwt.client.widget.attribute.ManyToOneDataSource;


public class SortedManyToOneItem extends DefaultManyToOneItem {
	
	public SortedManyToOneItem() {
		
	}
	
	public SortedManyToOneItem(String name) {
		getItem().setName(name);
	}

	/** {@inheritDoc} */
	public void init(AssociationAttributeInfo attributeInfo, AttributeProvider attributeProvider) {
		super.init(attributeInfo, attributeProvider);
		if(attributeInfo.getFeature() != null) {
			String sortName = attributeInfo.getFeature().getSortAttributeName();
			String displayName = attributeInfo.getFeature().getDisplayAttributeName();
			getItem().setDisplayField(displayName);
			if(sortName != null) {
				getItem().setSortField(sortName);
			} else {
				getItem().setSortField(displayName);
			}
		}
	}
	
}

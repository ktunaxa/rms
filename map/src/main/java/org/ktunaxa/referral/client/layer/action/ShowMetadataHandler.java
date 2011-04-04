/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */
package org.ktunaxa.referral.client.layer.action;

import java.util.LinkedHashMap;

import org.ktunaxa.referral.client.layer.ReferenceSubLayer;
import org.ktunaxa.referral.server.dto.ReferenceLayerTypeDto;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * Handler that shows the metadata in a panel.
 * 
 * @author Jan De Moerloose
 * 
 */
public class ShowMetadataHandler implements ClickHandler {

	private ReferenceSubLayer subLayer;

	public ShowMetadataHandler(ReferenceSubLayer subLayer) {
		this.subLayer = subLayer;
	}

	public void onClick(MenuItemClickEvent event) {
		VLayout panel = new VLayout();
		panel.setWidth(500);
		panel.setHeight100();
		panel.addMember(new MetadataLine("Name", subLayer.getDto().getName()));
		panel.addMember(new MetadataLine("Layer type", subLayer.getDto().getType().getDescription()));
		panel.addMember(new MetadataLine("Minimum scale", subLayer.getDto().getScaleMin()));
		panel.addMember(new MetadataLine("Maximum scale", subLayer.getDto().getScaleMax()));
		panel.addMember(new MetadataLine("Default visible", subLayer.getDto().isVisibleByDefault() ? "yes" : "no"));
		Window window = new Window();
		window.setTitle("Layer Metadata");
		window.addItem(panel);
		window.centerInPage();
		window.setAutoSize(true);
		window.setCanDragReposition(true);  
		window.setCanDragResize(true);
		window.show();
	}

	/**
	 * A line of the sublayer metadata.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class MetadataLine extends HLayout {

		public MetadataLine(String name, String value) {
			setHeight100();
			setWidth100();
			setStyleName("layerBlock");

			HTMLFlow nameFlow = new HTMLFlow("<div style='line-height:26px;'>" + name + "</div>");
			nameFlow.setWidth("50%");
			nameFlow.setLayoutAlign(VerticalAlignment.CENTER);
			nameFlow.setLayoutAlign(Alignment.LEFT);
			addMember(nameFlow);

			HTMLFlow valueFlow = new HTMLFlow("<div style='line-height:26px;'>" + value + "</div>");
			valueFlow.setWidth("50%");
			valueFlow.setLayoutAlign(VerticalAlignment.CENTER);
			valueFlow.setLayoutAlign(Alignment.RIGHT);
			addMember(valueFlow);
		}

	}

}
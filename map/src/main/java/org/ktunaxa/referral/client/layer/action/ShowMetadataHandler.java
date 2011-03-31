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

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
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
		MetadataForm form = new MetadataForm();
		form.setData();
		form.setMargin(10);
		Window window = new Window();
		window.setTitle("Layer Metadata");
		window.addItem(form);
		window.centerInPage();
		window.setAutoSize(true);
		window.show();
	}

	/**
	 * A form to show the sublayer metadata.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public class MetadataForm extends VLayout {

		private DynamicForm form;

		/** The name of the layer - used in the GUI. */
		private TextItem name;

		/**
		 * The type/category that this layer belongs to. Some types are base layers other types represent value aspects.
		 */
		private SelectItem type;

		/** The minimum scale at which this layer is still visible. */
		private SliderItem logScaleMin;
		private IntegerItem scaleMin;

		/** The maximum scale at which this layer is still visible. */
		private SliderItem logScaleMax;
		private IntegerItem scaleMax;

		/** Should this layer be visible by default or not? */
		private CheckboxItem visibleByDefault;
		

		public MetadataForm() {
			setHeight100();
			setWidth100();
			name = new TextItem("name", "name");

			type = new SelectItem("type", "Layer type");
			LinkedHashMap<String, String> types = new LinkedHashMap<String, String>();
			for (ReferenceLayerTypeDto type : subLayer.getReferenceLayer().getLayerTypes()) {
				types.put("" + type.getId(), type.getDescription());
			}
			type.setValueMap(types);

			logScaleMin = new SliderItem("logScaleMin");
			logScaleMin.setTitle("Minimum scale level");
			logScaleMin.setWidth(250);
			logScaleMin.setHeight(30);
			logScaleMin.setMinValue(0);
			logScaleMin.setMaxValue(8);
			logScaleMin.setNumValues(8);
			scaleMin = new IntegerItem("scaleMin", "Minimum scale denominator");

			logScaleMax = new SliderItem("logScaleMax");
			logScaleMax.setTitle("Maximum scale level");
			logScaleMax.setWidth(250);
			logScaleMax.setHeight(30);
			logScaleMax.setMinValue(0);
			logScaleMax.setMaxValue(8);
			logScaleMax.setNumValues(8);
			scaleMax = new IntegerItem("scaleMax", "Maximum scale denominator");

			visibleByDefault = new CheckboxItem("visibleByDefault", "Default visible");
			form = new DynamicForm();
			form.setFields(name, type, scaleMin, logScaleMin, scaleMax, logScaleMax, visibleByDefault);
			addMember(form);
		}

		public void setData() {
			name.setValue(subLayer.getDto().getName());
			type.setValue(subLayer.getDto().getId());
			logScaleMin.setValue(Math.log10(subLayer.getScaleMinDenominator()));
			logScaleMax.setValue(Math.log10(subLayer.getScaleMaxDenominator()));
			scaleMin.setValue(subLayer.getScaleMinDenominator());
			scaleMax.setValue(subLayer.getScaleMaxDenominator());
			visibleByDefault.setValue(subLayer.getDto().isVisibleByDefault());
		}
		

	}

}

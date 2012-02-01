/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ktunaxa.referral.client.referral;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import org.geomajas.command.CommandResponse;
import org.geomajas.command.dto.PersistTransactionRequest;
import org.geomajas.command.dto.PersistTransactionResponse;
import org.geomajas.configuration.AbstractAttributeInfo;
import org.geomajas.configuration.AbstractReadOnlyAttributeInfo;
import org.geomajas.configuration.FeatureInfo;
import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.gwt.client.map.MapModel;
import org.geomajas.gwt.client.map.feature.Feature;
import org.geomajas.gwt.client.map.feature.FeatureTransaction;
import org.geomajas.gwt.client.map.layer.VectorLayer;
import org.geomajas.layer.feature.Attribute;
import org.geomajas.layer.feature.attribute.AssociationValue;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.widget.utility.gwt.client.wizard.WizardPage;
import org.geomajas.widget.utility.gwt.client.wizard.WizardView;
import org.ktunaxa.referral.client.gui.LayoutConstant;

import java.util.Collection;
import java.util.List;

/**
 * Page to confirm creation of a new referral.
 * 
 * @author Jan De Moerloose
 */
public class ReferralConfirmPage extends WizardPage<ReferralData> {

	private VLayout widget;

	private VLayout summaryLayout;

	public ReferralConfirmPage() {
		super();

		widget = new VLayout();
		widget.setMembersMargin(LayoutConstant.MARGIN_LARGE);
		widget.setWidth100();
		widget.setHeight100();

		summaryLayout = new VLayout();
		summaryLayout.setWidth100();
		summaryLayout.setHeight100();
		summaryLayout.setStyleName("summary");
		widget.addMember(summaryLayout);
	}

	public String getTitle() {
		return "Confirm";
	}

	public String getExplanation() {
		return "Confirm the referral creation or cancel the creation procedure and go back to the task board";
	}

	@Override
	public boolean doValidate() {
		return true;
	}

	@Override
	public void savePage(final WizardView wizardView, final Runnable successCallback, final Runnable failureCallback) {
		SC.ask("Are you sure you want to create the referral ?", new BooleanCallback() {

			public void execute(Boolean value) {
				if (value != null && value) {
					wizardView.setLoading(true);
					ReferralData data = getWizardData();
					VectorLayer layer = data.getLayer();

					Feature[] old;
					if (null == data.getFeature().getId()) {
						old = new Feature[0];
					} else {
						old = new Feature[] {data.getFeature()};
					}

					final FeatureTransaction ft = new FeatureTransaction(layer, old,
							new Feature[] {data.getFeature()});
					PersistTransactionRequest request = new PersistTransactionRequest();
					request.setFeatureTransaction(ft.toDto());
					final MapModel mapModel = layer.getMapModel();
					// assume layer crs
					request.setCrs(mapModel.getCrs());

					GwtCommand command = new GwtCommand(PersistTransactionRequest.COMMAND);
					command.setCommandRequest(request);

					GwtCommandDispatcher.getInstance().execute(command,
							new AbstractCommandCallback<PersistTransactionResponse>() {

						public void execute(PersistTransactionResponse response) {
							mapModel.applyFeatureTransaction(new FeatureTransaction(ft.getLayer(),
									response.getFeatureTransaction()));
							Feature newFeature =
									new Feature(response.getFeatureTransaction().getNewFeatures()[0], ft.getLayer());
							getWizardData().setFeature(newFeature);
							wizardView.setLoading(false);
							successCallback.run();
						}

						@Override
						public void onCommunicationException(Throwable error) {
							super.onCommunicationException(error);
							wizardView.setLoading(false);
							failureCallback.run();
						}

						@Override
						public void onCommandException(CommandResponse response) {
							super.onCommandException(response);
							wizardView.setLoading(false);
							failureCallback.run();
						}
					});
				}
			}
		});
	}

	private String valueToString(Object value) {
		if (null != value) {
			if (value instanceof List) {
				List list = (List) value;
				if (1 == list.size()) {
					// just one item, no brackets
					return valueToString(list.get(0));
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append("[");
					boolean started = false;
					for (Object item : list) {
						if (!started) {
							sb.append(",");
						}
						sb.append(valueToString(item));
						started = true;
					}
					sb.append("]");
					return sb.toString();
				}
			} else if (value instanceof AssociationValue) {
				AssociationValue asso = (AssociationValue) value;
				// is there a better way ?
				Collection<Attribute<?>> attributes = asso.getAllAttributes().values();
				if (null != attributes && attributes.size() > 0) {
					return valueToString(attributes.iterator().next().getValue());
				} else {
					return valueToString(asso.getId().getValue());
				}
			}
			return value.toString();
		}
		return "";
	}

	public Canvas asWidget() {
		return widget;
	}

	@Override
	public void clear() {
		summaryLayout.setMembers();
	}

	@Override
	public void show() {
		summaryLayout.setMembers();
		boolean even = true;
		FeatureInfo featureInfo = getWizardData().getLayer().getLayerInfo().getFeatureInfo();
		for (AbstractAttributeInfo info : featureInfo.getAttributes()) {
			if (info instanceof AbstractReadOnlyAttributeInfo) {
				Object value = getWizardData().getFeature().getAttributeValue(info.getName());
				SummaryLine line = new SummaryLine(((AbstractReadOnlyAttributeInfo) info).getLabel(),
						valueToString(value));
				line.setStyleName(even ? "summaryLine" : "summaryLineDark");
				summaryLayout.addMember(line);
				even = !even;
			}
		}
	}

	/**
	 * A line of the form summary.
	 * 
	 * @author Jan De Moerloose
	 */
	private class SummaryLine extends HLayout {

		public SummaryLine(String name, String value) {
			setHeight100();
			setWidth100();
			setStyleName("layerBlock");

			HTMLFlow nameFlow = new HTMLFlow("<div>" + name + "</div>");
			nameFlow.setWidth("50%");
			nameFlow.setLayoutAlign(VerticalAlignment.CENTER);
			nameFlow.setLayoutAlign(Alignment.LEFT);
			addMember(nameFlow);

			HTMLFlow valueFlow = new HTMLFlow("<div>" + value + "</div>");
			valueFlow.setWidth("50%");
			valueFlow.setLayoutAlign(VerticalAlignment.CENTER);
			valueFlow.setLayoutAlign(Alignment.RIGHT);
			addMember(valueFlow);
		}

	}

}
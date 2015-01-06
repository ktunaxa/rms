package org.ktunaxa.referral.client;

import org.geomajas.gwt.client.command.AbstractCommandCallback;
import org.geomajas.gwt.client.command.GwtCommand;
import org.geomajas.gwt.client.command.GwtCommandDispatcher;
import org.geomajas.plugin.staticsecurity.client.StaticSecurityTokenRequestHandler;
import org.geomajas.plugin.staticsecurity.client.util.SsecLayout;
import org.ktunaxa.referral.server.command.dto.TestEmailRequest;
import org.ktunaxa.referral.server.command.dto.TestEmailResponse;
import org.ktunaxa.referral.server.command.dto.TestLDAPLoginRequest;
import org.ktunaxa.referral.server.command.dto.TestLDAPLoginResponse;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SanityCheckEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		GwtCommandDispatcher.getInstance().setTokenRequestHandler(new StaticSecurityTokenRequestHandler());
		SsecLayout.tokenRequestWindowLogo = "[ISOMORPHIC]/images/logoKtunaxaReferrals.png";
		SsecLayout.tokenRequestWindowLogoWidth = "300";
		SsecLayout.tokenRequestWindowLogoHeight = "120";
		SsecLayout.tokenRequestWindowWidth = "400";
		SsecLayout.tokenRequestWindowHeight = "240";

		HLayout layout = new HLayout(20);

		final DynamicForm form = new DynamicForm();
		form.setWidth(250);

		final TextItem emailItem = new TextItem();
		emailItem.setWidth(300);
		emailItem.setTitle("Email");
		emailItem.setDefaultValue("KGarner@ktunaxa.org");

		final TextItem userItem = new TextItem();
		userItem.setTitle("LDAP User");
		userItem.setWidth(300);
		userItem.setDefaultValue("KGarner");

		final PasswordItem passwordItem = new PasswordItem();
		passwordItem.setTitle("LDAP Password");
		passwordItem.setWidth(300);

		form.setFields(new FormItem[] { emailItem, userItem, passwordItem });

		VLayout buttonLayout = new VLayout(10);

		IButton shortMessageButton = new IButton("Send test mail");
		shortMessageButton.setAutoFit(true);
		shortMessageButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				TestEmailRequest request = new TestEmailRequest();
				request.setTo(emailItem.getValueAsString());
				GwtCommand command = new GwtCommand(TestEmailRequest.COMMAND);
				command.setCommandRequest(request);
				GwtCommandDispatcher.getInstance().execute(command, new AbstractCommandCallback<TestEmailResponse>() {

					@Override
					public void execute(TestEmailResponse response) {
						SC.say("Email successfully sent");
					}
				});
			}
		});

		IButton longMessageButton = new IButton("Test LDAP login");
		longMessageButton.setAutoFit(true);
		longMessageButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				TestLDAPLoginRequest request = new TestLDAPLoginRequest();
				request.setUser(userItem.getValueAsString());
				request.setPassword(passwordItem.getValueAsString());
				GwtCommand command = new GwtCommand(TestLDAPLoginRequest.COMMAND);
				command.setCommandRequest(request);
				GwtCommandDispatcher.getInstance().execute(command,
						new AbstractCommandCallback<TestLDAPLoginResponse>() {

							@Override
							public void execute(TestLDAPLoginResponse response) {
								if (response.getUserName() != null) {
									SC.say("LDAP login successfull");
								} else {
									SC.say("Login failed");
								}
							}
						});
			}
		});

		buttonLayout.addMember(shortMessageButton);
		buttonLayout.addMember(longMessageButton);
		layout.addMember(form);
		layout.addMember(buttonLayout);
		layout.setMargin(100);
		layout.draw();
		GwtCommandDispatcher.getInstance().login();
	}

}

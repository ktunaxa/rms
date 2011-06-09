/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.client.gui;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import org.geomajas.gwt.client.widget.CardLayout;
import org.ktunaxa.referral.client.form.AbstractTaskForm;
import org.ktunaxa.referral.client.form.DiscussEvaluationForm;
import org.ktunaxa.referral.client.form.EmptyForm;
import org.ktunaxa.referral.client.form.EvaluateOrFinishForm;
import org.ktunaxa.referral.client.form.ProvincialResultForm;
import org.ktunaxa.referral.client.form.ReviewReferralForm;
import org.ktunaxa.referral.client.form.ValueSelectForm;
import org.ktunaxa.referral.server.dto.TaskDto;

/**
 * Block which displays the current task with the ability to set variables and finish the task.
 *
 * @author Joachim Van der Auwera
 */
public class CurrentTaskBlock extends CardLayout {

	private static final String KEY_NO = "no";
	private static final String KEY_CURRENT = "curr";

	// form names should match task names for the form
	private static final String FORM_EMPTY = "empty";
	private static final String FORM_DISCUSS_EVALUATION = "discussEvaluationResult.form";
	private static final String FORM_EVALUATE_OR_FINISH = "evaluateOrFinish.form";
	private static final String FORM_PROVINCIAL_RESULT = "provincialResult.form";
	private static final String FORM_REVIEW_REFERRAL = "reviewReferral.form";
	private static final String FORM_VALUE_SELECT = "valueSelect.form";
	private static final String[] TASKS_WITH_FORM = {
			FORM_DISCUSS_EVALUATION,
			FORM_EVALUATE_OR_FINISH,
			FORM_PROVINCIAL_RESULT,
			FORM_REVIEW_REFERRAL,
			FORM_VALUE_SELECT
	};

	private final CardLayout taskForms = new CardLayout();

	public CurrentTaskBlock() {
		super();

		VLayout noTask = new VLayout();
		HTMLFlow noTaskText = new HTMLFlow("No current task, please select one.");
		noTaskText.setWidth100();
		noTask.addChild(noTaskText);

		VLayout currentTask = new VLayout();
		taskForms.addCard(FORM_EMPTY, new EmptyForm());
		taskForms.addCard(FORM_DISCUSS_EVALUATION, new DiscussEvaluationForm());
		taskForms.addCard(FORM_EVALUATE_OR_FINISH, new EvaluateOrFinishForm());
		taskForms.addCard(FORM_PROVINCIAL_RESULT, new ProvincialResultForm());
		taskForms.addCard(FORM_REVIEW_REFERRAL, new ReviewReferralForm());
		taskForms.addCard(FORM_VALUE_SELECT, new ValueSelectForm());
		taskForms.setHeight100();
		taskForms.setWidth100();
		currentTask.addChild(taskForms);
		HLayout finishLayout = new HLayout();
		finishLayout.setHeight(20);
		finishLayout.addChild(new LayoutSpacer());
		finishLayout.addChild(new Button("Finish task"));
		currentTask.addChild(finishLayout);

		addCard(KEY_NO, noTask);
		addCard(KEY_CURRENT, currentTask);
		showCard(KEY_NO);
	}

	public void refresh(TaskDto task) {
		if (null == task) {
			showCard(KEY_NO);
		} else {
			String formKey = FORM_EMPTY;
			for (int i = TASKS_WITH_FORM.length - 1 ; i >= 0 ; i--) {
				if (null != task.getFormKey() && task.getFormKey().endsWith(TASKS_WITH_FORM[i])) {
					formKey = TASKS_WITH_FORM[i];
				}
			}
			taskForms.showCard(formKey);
			((AbstractTaskForm) taskForms.getCurrentCard()).refresh(task);
			showCard(KEY_CURRENT);
		}
		Canvas canvas = getCurrentCard();
		if (canvas instanceof Layout) {
			((Layout) canvas).reflow();
		}
	}
}

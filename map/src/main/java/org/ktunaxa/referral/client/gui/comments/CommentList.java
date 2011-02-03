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

package org.ktunaxa.referral.client.gui.comments;

import org.ktunaxa.referral.server.dto.CommentDto;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Temporary test class....
 * 
 * @author Pieter De Graef
 */
public class CommentList extends ListGrid {

	public CommentList() {
		setShowAllRecords(true);

		ListGridField contentField = new ListGridField("title", "Text");
		ListGridField addToReportField = new ListGridField("addToReport", "Add To Report?");
		addToReportField.setType(ListGridFieldType.BOOLEAN);
		setFields(contentField, addToReportField);
		setData(getCommentData());
	}

	private CommentRecord[] getCommentData() {
		CommentDto c1 = new CommentDto();
		c1.setTitle("Comment that expresses my deepest lorum ipsum feelings.");
		c1.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae dolor rutrum sapien dapibus eleifend sed vitae massa. Suspendisse tortor tortor, porttitor quis blandit eget, dictum sed ipsum. Curabitur luctus turpis in diam accumsan convallis. Cras ultrices, ligula id adipiscing facilisis, arcu justo consequat quam, id pulvinar mauris magna ut tortor. Fusce vestibulum ligula at augue feugiat non elementum metus gravida. Nulla elit diam, interdum at lacinia id, pharetra eget tortor. Praesent tincidunt euismod posuere. Nulla sagittis adipiscing lacus, at suscipit leo fringilla sit amet. Cras pellentesque consequat turpis vel interdum. Nunc ligula nunc, porta quis aliquet non, luctus a odio. Sed feugiat pulvinar libero nec commodo. Nulla sed mi nisl. Nulla vulputate pulvinar leo, at accumsan odio fermentum et.");
		c1.setCheckedContent("Let's take a shorter version of the above, shall we? Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae dolor rutrum sapien dapibus eleifend sed vitae massa. Suspendisse tortor tortor, porttitor quis blandit eget, dictum sed ipsum. Curabitur luctus turpis in diam accumsan convallis. Cras ultrices, ligula id adipiscing facilisis, arcu justo consequat quam, id pulvinar mauris magna ut tortor. Fusce vestibulum ligula at augue feugiat non elementum metus gravida. Nulla elit diam, interdum at lacinia id, pharetra eget tortor. Praesent tincidunt euismod posuere. Nulla sagittis adipiscing lacus, at suscipit leo fringilla sit amet. Cras pellentesque consequat turpis vel interdum. Nunc ligula nunc, porta quis aliquet non, luctus a odio.");
		c1.setIncludeInReport(true);
		CommentDto c2 = new CommentDto();
		c2.setTitle("Comment that is here just for testing purposes.");
		c2.setContent("2Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae dolor rutrum sapien dapibus eleifend sed vitae massa. Suspendisse tortor tortor, porttitor quis blandit eget, dictum sed ipsum. Curabitur luctus turpis in diam accumsan convallis. Cras ultrices, ligula id adipiscing facilisis, arcu justo consequat quam, id pulvinar mauris magna ut tortor. Fusce vestibulum ligula at augue feugiat non elementum metus gravida. Nulla elit diam, interdum at lacinia id, pharetra eget tortor. Praesent tincidunt euismod posuere. Nulla sagittis adipiscing lacus, at suscipit leo fringilla sit amet. Cras pellentesque consequat turpis vel interdum. Nunc ligula nunc, porta quis aliquet non, luctus a odio. Sed feugiat pulvinar libero nec commodo. Nulla sed mi nisl. Nulla vulputate pulvinar leo, at accumsan odio fermentum et.");
		c2.setIncludeInReport(false);
		return new CommentRecord[] { new CommentRecord(c1), new CommentRecord(c2), new CommentRecord(c2),
				new CommentRecord(c2), new CommentRecord(c2), new CommentRecord(c2), new CommentRecord(c2),
				new CommentRecord(c2), new CommentRecord(c2), new CommentRecord(c2), new CommentRecord(c2) };
	}

	public class CommentRecord extends ListGridRecord {

		private CommentDto comment;

		public CommentRecord() {
		}

		public CommentRecord(CommentDto comment) {
			this.comment = comment;
			setAttribute("title", comment.getTitle());
			if (comment.getCheckedContent() != null) {
				setAttribute("content", comment.getCheckedContent());
			} else {
				setAttribute("content", comment.getContent());
			}
			setAttribute("includeInReport", comment.isIncludeInReport());
		}

		public String getTitle() {
			return getAttribute("title");
		}

		public String getContent() {
			return getAttribute("content");
		}

		public String isIncludeInReport() {
			return getAttribute("includeInReport");
		}
	}
}
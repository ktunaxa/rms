# Ktunaxa Referral Management System

This is the referral management system for the [Ktunaxa Nation Council](http://www.ktunaxa.org/).

When you create a referral in the system, a workflow is started to precess the referral. The referral can include the geometry as point, shape file or Geomark URL.

The user interface allows you to see the open tasks for the referrals and the task assigned to the logged in user.

The referrals are displayed on the map. Full map navigation is provided. You can search referral either on attributes or on the geometry.

Reference data can be added in the system (offline) and used for evaluating the validity of the referrals. 

At the end of the process, a report with the evaluation result is produced and e-mailed to the contact.

During the evaluation, comments and documents can be added in the system. The documents are stored in a document management system. You can indicate which comments and which documents need to be included in the result e-mail.

![screenshot](https://github.com/ktunaxa/rms/raw/master/screenshot.png)

The application is built in Java using the [Geomajas](http://geomajas.org) spatial application framework for displaying the map, using [Activiti](http://www.activit.org/) for the workflows, [Hibernate](http://hibernate.org/) [spatial](http://www.hibernatespatial.org/) for persistence, [FreeMarker](http://freemarker.sourceforge.net/) for template handling, [Alfresco](http://www.alfresco.com/) as document management system (accessed using CMIS), [GWT](http://code.google.com/webtoolkit/) for the UI, [Jasperreports](http://jasperforge.org/projects/jasperreports) for the reporting.

The application is available under the [AGPLv3](http://www.gnu.org/licenses/agpl.html) license.

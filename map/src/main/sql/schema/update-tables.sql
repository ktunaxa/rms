-- KTU-161 content field in comment table is now allowed to be NULL
ALTER TABLE referral_comment ALTER COLUMN content DROP NOT NULL;

-- KTU-113 also need sender e-mail
ALTER TABLE template ADD COLUMN mail_sender character varying(254) NOT NULL;
ALTER TABLE template ADD COLUMN subject text;
ALTER TABLE template ADD COLUMN string_content text;
ALTER TABLE template ALTER COLUMN content DROP NOT NULL;

INSERT INTO template (id, mime_type, mail_sender, title, subject, description, string_content) values
(1, 'text', 'bla@ktunaxa.org', 'notify.level0', '${referralId} ${referralName} Received, but no action.', 
'Referral ${referralId} level 0 notification',
E'Referral ${referralId} ${referralName}\n\nWe have received this referral but do not think we need to take action to process it. For us it has engagement level 0.\n\nKind regards,\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, subject, description, string_content) values
(2, 'text', 'bla@ktunaxa.org', 'notify.change.engagementLevel', '${referralId} ${referralName} Received and started with different engagement level.',
'Referral ${referralId} engagement level notification',
E'Referral ${referralId} ${referralName}\n\nThanks for submitting this referral. After investigation, we believe the engagement level needs to be changed from ${provinceEngagementLevel} to ${engagementLevel}. Our seasoning for this:\n\n${engagementComment}\n\nPlease confirm receipt of this message or let us know of any problems with this decision.\n\nWe will process your referral by ${completionDeadline}.\n\nKind regards,\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, subject, description, string_content) values
(3, 'text', 'bla@ktunaxa.org', 'notify.start', '${referralId} ${referralName} Received and started.',
'Start processing referral ${referralId}',
E'Referral ${referralId} ${referralName}\n\nThanks for submitting this referral.\n\nWe will process your referral by ${completionDeadline}.\n\nKind regards,\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, subject, description, string_content) values
(4, 'text', 'bla@ktunaxa.org', 'notify.result', 'Evaluation of referral: ${referralId} ${referralName}.',
'Referral ${referralId} result',
E'Referral ${referralId} ${referralName}\n\nOur evaluation of this referral is attached.\n\nKind regards,\nKtunaxa Nation Council');

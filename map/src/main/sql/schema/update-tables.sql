-- KTU-161 content field in comment table is now allowed to be NULL
ALTER TABLE referral_comment ALTER COLUMN content DROP NOT NULL;

-- KTU-113 also need sender e-mail
ALTER TABLE template ADD COLUMN mail_sender character varying(254) NOT NULL;
ALTER TABLE template ADD COLUMN string_content text;
ALTER TABLE template ALTER COLUMN content DROP NOT NULL;

INSERT INTO template (id, mime_type, mail_sender, title, description, string_content) values
(1, 'text', 'bla@ktunaxa.org', 'notify.level0', '',
'Referral ${referralId} ${referralName}\nWe have received this referral but do not think we need to take action to process it. For us it has engagement level 0.\nKind regards\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, description, string_content) values
(2, 'text', 'bla@ktunaxa.org', 'notify.change.engagementLevel', '',
'Referral ${referralId} ${referralName}\nThanks for submitting this referral. After investigation, we believe the engagement level needs to be changed from {provinceEngagementLevel} to ${engagementLevel}. Our seasoning for this:\n${engagementComment}\nPlease confirm receipt of this message or let us know of any problems with this decision.\nWe will process your referral by ${completionDeadline}.\nKind regards\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, description, string_content) values
(3, 'text', 'bla@ktunaxa.org', 'notify.start', '',
'Referral ${referralId} ${referralName}\nThanks for submitting this referral.\nWe will process your referral by ${completionDeadline}.\nKind regards\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, description, string_content) values
(4, 'text', 'bla@ktunaxa.org', 'notify.result', '',
'''Referral ${referralId} ${referralName}\nOur evaluation of this referral is attached.\nKind regards\nKtunaxa Nation Council');

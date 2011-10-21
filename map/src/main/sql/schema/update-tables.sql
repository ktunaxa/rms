-- KTU-161 content field in comment table is now allowed to be NULL
ALTER TABLE referral_comment ALTER COLUMN content DROP NOT NULL;


-- KTU-113 also need sender e-mail
-- ALTER TABLE template ADD COLUMN mail_sender character varying(254) NOT NULL;
-- ALTER TABLE template ADD COLUMN subject text;
-- ALTER TABLE template ADD COLUMN string_content text;
ALTER TABLE template ALTER COLUMN content DROP NOT NULL;


-- KTU-254 split status in status, decision and provincialDecision
-- CREATE TABLE referral_decision(
-- 	id serial PRIMARY KEY,
-- 	title character varying(254) NOT NULL,
-- 	description character varying(254) NOT NULL
-- );
-- ALTER TABLE referral_decision OWNER TO referral_group;
-- GRANT ALL ON TABLE referral_decision TO referral_group;
-- INSERT INTO referral_decision (id, title, description) values (1, 'Unknown', 'The decision is not yet known.');
-- INSERT INTO referral_decision (id, title, description) values (2, 'Approved', 'The referral was approved.');
-- INSERT INTO referral_decision (id, title, description) values (3, 'Denied', 'The referral was denied.');

-- ALTER TABLE referral ADD COLUMN stop_reason character varying(254);
-- ALTER TABLE referral ADD COLUMN decision_id integer NOT NULL DEFAULT 1;
-- ALTER TABLE referral ADD CONSTRAINT fk_referral_decision FOREIGN KEY (decision_id) REFERENCES referral_decision (id);
-- ALTER TABLE referral ADD COLUMN provincial_decision_id integer NOT NULL DEFAULT 1;
-- ALTER TABLE referral ADD CONSTRAINT fk_provincial_decision FOREIGN KEY (provincial_decision_id) REFERENCES referral_decision (id);
-- DELETE FROM referral_status where id = 3 OR id = 4;
-- INSERT INTO referral_status (id, title, description) values (3, 'Finished', 'Referral has been fully processed.');
-- INSERT INTO referral_status (id, title, description) values (4, 'Stopped', 'Referral processing was stopped.');


-- KTU-244 add "other" as external agency type
-- INSERT INTO referral_external_agency_type (id, title, description) values (5, 'Other', 'Other');


-- KTU-255 add fields for producing the final report
-- ALTER TABLE referral ADD COLUMN final_report_introduction text;
-- ALTER TABLE referral ADD COLUMN final_report_conclusion text;

-- KTU-161 content field in comment table is now allowed to be NULL
-- ALTER TABLE referral_comment ALTER COLUMN content DROP NOT NULL;


-- KTU-113 also need sender e-mail
-- ALTER TABLE template ADD COLUMN mail_sender character varying(254) NOT NULL;
-- ALTER TABLE template ADD COLUMN subject text;
-- ALTER TABLE template ADD COLUMN string_content text;
-- ALTER TABLE template ALTER COLUMN content DROP NOT NULL;


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
-- ALTER TABLE referral ADD CONSTRAINT fk_referral_provincial_decision FOREIGN KEY (provincial_decision_id) REFERENCES referral_decision (id);
-- DELETE FROM referral_status where id = 3 OR id = 4;
-- INSERT INTO referral_status (id, title, description) values (3, 'Finished', 'Referral has been fully processed.');
-- INSERT INTO referral_status (id, title, description) values (4, 'Stopped', 'Referral processing was stopped.');


-- KTU-244 add "other" as external agency type
-- INSERT INTO referral_external_agency_type (id, title, description) values (5, 'Other', 'Other');


-- KTU-255 add fields for producing the final report
-- ALTER TABLE referral ADD COLUMN final_report_introduction text;
-- ALTER TABLE referral ADD COLUMN final_report_conclusion text;


-- update field lengths to be larger for textarea fields
-- ALTER TABLE referral ALTER COLUMN project_description TYPE character varying(2048);
-- ALTER TABLE referral ALTER COLUMN project_background TYPE character varying(2048);
-- ALTER TABLE referral ALTER COLUMN contact_address TYPE character varying(2048);


-- KTU-259 increase length of content field in referral_comment
-- ALTER TABLE referral_comment ALTER COLUMN content TYPE character varying(2048);
-- ALTER TABLE referral_comment ALTER COLUMN report_content TYPE character varying(2048);
-- ALTER TABLE document_comment ALTER COLUMN content TYPE character varying(2048);


-- KTU-262 update referral types
-- DELETE from referral_type where id=18;
-- UPDATE referral_type SET title='Roads/Bridges' WHERE id=25;
-- INSERT INTO referral_type (id, title, description) values (27, 'Crown Grant', '');
-- INSERT INTO referral_type (id, title, description) values (28, 'Mineral Exploration', '');
-- INSERT INTO referral_type (id, title, description) values (29, 'Oil and Gas Production', '');
-- INSERT INTO referral_type (id, title, description) values (30, 'Oil and Gas Exploration', '');
-- INSERT INTO referral_type (id, title, description) values (31, 'Oil and Gas Infrastructure', '');
-- INSERT INTO referral_type (id, title, description) values (32, 'Zoning, DPA or OCP Changes', '');
-- INSERT INTO referral_type (id, title, description) values (33, 'Subdivision Application', '');
-- INSERT INTO referral_type (id, title, description) values (34, 'Water Diversion', '');
-- INSERT INTO referral_type (id, title, description) values (35, 'Changes in/about stream/waterbody', '');
-- INSERT INTO referral_type (id, title, description) values (36, 'Waste Discharge', '');
-- INSERT INTO referral_type (id, title, description) values (37, 'Pest Management', '');
-- INSERT INTO referral_type (id, title, description) values (38, 'Assignment (name change)', '');
-- INSERT INTO referral_type (id, title, description) values (39, 'Follow up letter', '');
-- INSERT INTO referral_type (id, title, description) values (40, 'Multiple', '');
-- INSERT INTO referral_type (id, title, description) values (41, 'Other', '');

-- KTU-285 field to order comments in final report
-- ALTER TABLE referral_comment ADD COLUMN position integer DEFAULT 2;
-- AAD-18 remove size constraint from comment
-- ALTER TABLE referral_comment ALTER COLUMN "content" type character varying;
-- ALTER TABLE referral_comment ALTER COLUMN "report_content" type character varying;
-- ALTER TABLE document_comment ALTER COLUMN "content" type character varying;
-- ALTER TABLE document_comment ALTER COLUMN "report_content" type character varying;

-- AAD-20 add 2 layers
--INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (84, 'Grassland Eco System', 9, '1:150000', '1:1', false, 84); 
--INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (85, 'Riparian', 9, '1:150000', '1:1', false, 85); 
-- AAD-22 add 1 layer
--INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (86, 'Guidance Zones', 6, '1:150000', '1:1', false, 86); 

-- KTU-113 also need sender e-mail
ALTER TABLE template ADD COLUMN cc text;
alter table referral alter column calendar_year set NOT NULL;
alter table referral alter column calendar_year  set DEFAULT (to_char((('now'::text)::date)::timestamp with time zone, 'yy'::text))::integer



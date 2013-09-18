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
-- ALTER TABLE template ADD COLUMN cc text;
-- alter table referral alter column calendar_year set NOT NULL;
-- alter table referral alter column calendar_year set DEFAULT (to_char((('now'::text)::date)::timestamp with time zone, 'yy'::text))::integer;
-- INSERT INTO referral_type (title, description) values (E'Alternative Energy: Investigation',E'The exploration phase of a potential future alternative energy facility. Alternative energy could include water power, wind power, ocean power, geothermal power etc. Investigation may include a license of occupation, geothermal test hole etc.');
-- INSERT INTO referral_type (title, description) values (E'Alternative Energy: Production',E'Includes permits required for the production phase of an alternative energy facility. (Water power, wind power, ocean power, geothermal). Could include a lease, a water license etc.');
-- INSERT INTO referral_type (title, description) values (E'Environment: Contaminated Sites and Hazardous Waste',E'Site designation or delisting, clean up (remediation), hazardous waste facility approval…');
-- INSERT INTO referral_type (title, description) values (E'Environment: Guide Outfitting',E'Anything related to a guide outfitter operation. Could include new cabins, transfers, renewals etc.');
-- INSERT INTO referral_type (title, description) values (E'Environment: Park Planning or Operations',E'Park management planning, forest health or firee management within parks, or facility development, vegetation management etc within a park, and generally conducted by Parks.');
-- INSERT INTO referral_type (title, description) values (E'Environment: Park Use Permit',E'Applications by external individuals forr activities within a park. E.g. food vending, research, film production etc. ');
-- INSERT INTO referral_type (title, description) values (E'Environment: Pest Management',E'E.g. Vegetation control, mosquito control, forest pest management');
-- INSERT INTO referral_type (title, description) values (E'Environment: Trapline',E'Anything related to traplines including cabins, transfers, renewals etc.');
-- INSERT INTO referral_type (title, description) values (E'Environment: Waste Discharge',E'Includes sewage discarge, solid waste (lanfills), and air pollution permitting.');
-- INSERT INTO referral_type (title, description) values (E'Environment: Wildlife/Hunting/Angling',E'Anything related to hunting/fishing regulations, species management plans, guidelines/BMPs re wildlife, habitat areas.');
-- INSERT INTO referral_type (title, description) values (E'Forestry: FSP, WLP',E'New Forest Stewardhsip Plans / Woodlot License Plans, or changes/extenstions to existing plans');
-- INSERT INTO referral_type (title, description) values (E'Forestry: General',E'Anything forestry related that doesn\'t fall into the other forestry categories (e.g. licence to cut - LtC)');
-- INSERT INTO referral_type (title, description) values (E'Forestry: Provincial Planning/Administration',E'Annual Allowable Cut (AAC), private land deletion, Tree Farm Licence transfer, Forest Management Planning etc.');
-- INSERT INTO referral_type (title, description) values (E'Forestry: Roads, bridges and cutblocks',E'Typically we receive these directly from industry - notifications of planned road development and cutblocks');
-- INSERT INTO referral_type (title, description) values (E'Lands: Adventure Tourism',E'Tenures for things like adventure races, heli-skiing, commercial (guided) hiking, mountain biking…');
-- INSERT INTO referral_type (title, description) values (E'Lands: Commercial/Industrial',E'Tenures for e.g. ofice buildings, malls, golf courses, campgrounds, processing facility, machine shops, mills etc');
-- INSERT INTO referral_type (title, description) values (E'Lands: Communication Sites',E'Tenures for sites required for transmission/reception of signals - e.g. radio, television, microwave, satellite, cell phone');
-- INSERT INTO referral_type (title, description) values (E'Lands: Community & Institutional ',E'Tenures for e.g. regional park, community center, hospital, town hall etc.');
-- INSERT INTO referral_type (title, description) values (E'Lands: Crown Grant / Sales',E'Transfer of crown (public) land to private land');
-- INSERT INTO referral_type (title, description) values (E'Lands: Private Moorage/ Marrinas',E'Tenures for lands required for personal docks (i.e. not public or commercial docks), or marinas and yacht clubs. Can include associated restaurants, gas docks etc.');
-- INSERT INTO referral_type (title, description) values (E'Lands: General and Renewals',E'E.g. Other tenures (film, agriculture, aquaculture), assignments, tenure renewals, etc');
-- INSERT INTO referral_type (title, description) values (E'Lands: Resorts',E'Any approvals for resorts - e.g. operating agreement, master development agreement.');
-- INSERT INTO referral_type (title, description) values (E'Lands: Roadways & Bridges',E'Tenures for roads and bridges (e.g. s.80 roadway) - not major highways (see Transportation/Infrastructure) or forest roads/bridges (see Forestry)');
-- INSERT INTO referral_type (title, description) values (E'Lands: Utilities',E'Tenures for utilities e.g. distribution lines, pipelines, flow lines, sewer and water systems, electrical transmission and distribution lines etc');
-- INSERT INTO referral_type (title, description) values (E'Local Govt: OCP, Zoning ',E'New or changes to an Official Community Plan (OCP) or zoning bylaw, includes changes to accommodate a subdivision');
-- INSERT INTO referral_type (title, description) values (E'Mining: Aggregate and Quarry',E'Permits, NoW, or leases for gravel, sand, landscaping rock etc.');
-- INSERT INTO referral_type (title, description) values (E'Mining: Mineral and Coal Exploration',E'Licences, notices of work, bulk sample for exploration purposes for mineral or coal mining');
-- INSERT INTO referral_type (title, description) values (E'Mining: Mineral and Coal Production',E'Leases or other approvals for mineral or coal mining - production');
-- INSERT INTO referral_type (title, description) values (E'Mining: Placer',E'Permits, NoW, leases for placer (usually gold) operations');
-- INSERT INTO referral_type (title, description) values (E'Misc: Multiple',E'');
-- INSERT INTO referral_type (title, description) values (E'Misc: Other',E'');
-- INSERT INTO referral_type (title, description) values (E'Oil and Gas: All',E'Any permits related to exploration, infrastructure or production of oil and gas including coal bed methane gas');
-- INSERT INTO referral_type (title, description) values (E'Range: All',E'Range (grazing) permits, leases, transfers, amendments etc.');
-- INSERT INTO referral_type (title, description) values (E'Recreation Sites and Trails: All',E'Establish or cancel recreation sites or trails + any associated authorizations');
-- INSERT INTO referral_type (title, description) values (E'Transportation/Infrastructure: Highways',E'Includes any highways projects including planning, land acquisition, bridge or road construction, maintenance, designations, approvals. ');
-- INSERT INTO referral_type (title, description) values (E'Transportation/Infrastructure: Subdivision Approvals',E'Provincial approvals for subdivisions');
-- INSERT INTO referral_type (title, description) values (E'Water: Changes in/about stream',E'Applications for modifications to a stream or lake. Usually related to construction or maintenance of a structure like a bridge, retaining wall, rip rap etc.');
-- INSERT INTO referral_type (title, description) values (E'Water: General',E'Any other water related decisions - e.g. transfer of appurtenancy, short term use, notifications, etc');
-- INSERT INTO referral_type (title, description) values (E'Water: Licenses',E'Enables use of water for a variety of purposes (domestic, agricultural, industrial…). Also includes amendments and transfers of licences.');
-- INSERT INTO referral_type (title, description) values (E'Water: Planning',E'Water use plans, water management plans');

-- AAD-71: update referral types of existing referrals
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Adventure Tourism' and description = '') and t.title = E'Lands: Adventure Tourism';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Clean Energy' and description = '') and t.title = E'Alternative Energy: Investigation';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Commercial' and description = '') and t.title = E'Lands: Commercial/Industrial';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Communication Sites' and description = '') and t.title = E'Lands: Communication Sites';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Community & Institutional' and description = '') and t.title = E'Lands: Community & Institutional ';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Contaminated Sites and Restoration' and description = '') and t.title = E'Environment: Contaminated Sites and Hazardous Waste';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Flood Protection' and description = '') and t.title = E'Water: Changes in/about stream';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Forestry' and description = '') and t.title = E'Forestry: General';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Grazing' and description = '') and t.title = E'Range: All';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Guide Outfitting' and description = '') and t.title = E'Environment: Guide Outfitting';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Mining: Placer' and description = '') and t.title = E'Mining: Placer' and t.description != '';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Mining: Aggregate and Quarry' and description = '') and t.title = E'Mining: Aggregate and Quarry' and t.description != '';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Mining: Mine Development' and description = '') and t.title = E'Mining: Mineral and Coal Production';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Private Moorage' and description = '') and t.title = E'Lands: Private Moorage/ Marrinas';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Public Recreation - Parks' and description = '') and t.title = E'Misc: Other';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Public Recreation - General' and description = '') and t.title = E'Misc: Other';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Roads/Bridges' and description = '') and t.title = E'Lands: Roadways & Bridges';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Utilities' and description = '') and t.title = E'Lands: Utilities';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Crown Grant' and description = '') and t.title = E'Lands: Crown Grant / Sales';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Mineral Exploration' and description = '') and t.title = E'Mining: Mineral and Coal Exploration';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Subdivision Application' and description = '') and t.title = E'Transportation/Infrastructure: Subdivision Approvals';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Water Diversion' and description = '') and t.title = E'Water: Licenses';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Changes in/about stream/waterbody' and description = '') and t.title = E'Water: Changes in/about stream';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Assignment (name change)' and description = '') and t.title = E'Lands: General and Renewals';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Multiple' and description = '') and t.title = E'Misc: Multiple';
update referral set type_id = t.id from referral_type t where referral.type_id = (select id from referral_type where title =E'Other' and description = '') and t.title = E'Misc: Other';
-- MUST HAVE id=1 to initialize option box !!!!
INSERT INTO referral_type (id, title, description) values (1, E'Alternative Energy: Investigation',E'The exploration phase of a potential future alternative energy facility. Alternative energy could include water power, wind power, ocean power, geothermal power etc. Investigation may include a license of occupation, geothermal test hole etc.');
update referral set type_id=1 where type_id=43;
delete from referral_type where id=43; 



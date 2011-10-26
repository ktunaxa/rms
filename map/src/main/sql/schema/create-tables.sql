-- ----------------------------------------------------------------------------
-- Table: REFERENCE LAYER CATEGORY
-- ----------------------------------------------------------------------------
CREATE TABLE reference_layer_type(
	id serial PRIMARY KEY,
	description character varying(254) NOT NULL,
	base_layer boolean NOT NULL default false
); 

ALTER TABLE reference_layer_type OWNER TO referral_group; 
GRANT ALL ON TABLE reference_layer_type TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERENCE LAYER
-- ----------------------------------------------------------------------------
CREATE TABLE reference_layer(
	id serial PRIMARY KEY,
	code integer NOT NULL,
	name character varying(254) NOT NULL,
	type_id integer NOT NULL,
	scale_min character varying(254) NOT NULL default 0,
	scale_max character varying(254) NOT NULL default 0,
	visible_by_default boolean NOT NULL default false,
	CONSTRAINT fk_reference_layer_type FOREIGN KEY (type_id) REFERENCES reference_layer_type (id),
	CONSTRAINT reference_layer_code UNIQUE (code)
); 

CREATE INDEX reference_layer_type_id_idx
  ON reference_layer
  USING btree
  (type_id);


CREATE INDEX reference_layer_code_idx
  ON reference_layer
  USING btree
  (code);


ALTER TABLE reference_layer OWNER TO referral_group; 
GRANT ALL ON TABLE reference_layer TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERENCE_VALUE layer
-- ----------------------------------------------------------------------------
CREATE TABLE reference_value
(
  id serial PRIMARY KEY,
  layer_id integer NOT NULL,
  style_code character varying(254),
  label character varying(254),
  CONSTRAINT fk_reference_value_layer FOREIGN KEY (layer_id) REFERENCES reference_layer (id)
); 

CREATE INDEX reference_value_layer_id_idx
  ON reference_value
  USING btree
  (layer_id);

SELECT AddGeometryColumn('','reference_value','geom','26911','GEOMETRY',2); 

CREATE INDEX reference_value_gist
  ON reference_value
  USING gist
  (geom);


ALTER TABLE reference_value OWNER TO referral_group; 
GRANT ALL ON TABLE reference_value TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERENCE VALUE KEY VALUE
-- ----------------------------------------------------------------------------
CREATE TABLE reference_value_attribute(
	id serial PRIMARY KEY,
	reference_value_id integer NOT NULL,
	the_key character varying(254) NOT NULL,
	the_value character varying(254),
	CONSTRAINT fk_reference_value_attribute_reference FOREIGN KEY (reference_value_id) REFERENCES reference_value (id)
); 

CREATE INDEX reference_value_attribute_reference_value_id_idx
  ON reference_value_attribute
  USING btree
  (reference_value_id);

ALTER TABLE reference_value_attribute OWNER TO referral_group; 
GRANT ALL ON TABLE reference_value_attribute TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERENCE_BASE layer
-- ----------------------------------------------------------------------------
CREATE TABLE reference_base
(
  id serial PRIMARY KEY,
  layer_id integer NOT NULL,
  style_code character varying(254),
  label character varying(254),
  CONSTRAINT fk_reference_base_layer FOREIGN KEY (layer_id) REFERENCES reference_layer (id)
); 

CREATE INDEX reference_base_layer_id_idx
  ON reference_base
  USING btree
  (layer_id);

SELECT AddGeometryColumn('','reference_base','geom','26911','GEOMETRY',2);

CREATE INDEX reference_base_gist
  ON reference_base
  USING gist
  (geom);

ALTER TABLE reference_base OWNER TO referral_group; 
GRANT ALL ON TABLE reference_base TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERENCE VALUE KEY VALUE
-- ----------------------------------------------------------------------------
CREATE TABLE reference_base_attribute(
	id serial PRIMARY KEY,
	reference_base_id integer NOT NULL,
	the_key character varying(254) NOT NULL,
	the_value character varying(254),
	CONSTRAINT fk_reference_base_attribute_reference FOREIGN KEY (reference_base_id) REFERENCES reference_base (id)
); 

CREATE INDEX reference_base_attribute_reference_base_id_idx
  ON reference_base_attribute
  USING btree
  (reference_base_id);

ALTER TABLE reference_base_attribute OWNER TO referral_group; 
GRANT ALL ON TABLE reference_base_attribute TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL STATUS
-- ----------------------------------------------------------------------------
CREATE TABLE referral_status(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	description character varying(254) NOT NULL
); 

ALTER TABLE referral_status OWNER TO referral_group; 
GRANT ALL ON TABLE referral_status TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL DECISION
-- ----------------------------------------------------------------------------
CREATE TABLE referral_decision(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	description character varying(254) NOT NULL
);

ALTER TABLE referral_decision OWNER TO referral_group;
GRANT ALL ON TABLE referral_decision TO referral_group;


-- ----------------------------------------------------------------------------
-- Table: REFERRAL TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE referral_type(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	description character varying(254) NOT NULL
); 

ALTER TABLE referral_type OWNER TO referral_group; 
GRANT ALL ON TABLE referral_type TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL APPLICATION TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE referral_application_type(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	description character varying(254) NOT NULL
); 

ALTER TABLE referral_application_type OWNER TO referral_group; 
GRANT ALL ON TABLE referral_application_type TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL EXTERNAL_AGENCY TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE referral_external_agency_type(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	description character varying(254) NOT NULL
);

ALTER TABLE referral_external_agency_type OWNER TO referral_group;
GRANT ALL ON TABLE referral_external_agency_type TO referral_group;


-- ----------------------------------------------------------------------------
-- Table: REFERRAL PRIORITY
-- ----------------------------------------------------------------------------
CREATE TABLE referral_priority(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	description character varying(254) NOT NULL
);

ALTER TABLE referral_priority OWNER TO referral_group;
GRANT ALL ON TABLE referral_priority TO referral_group;


-- ----------------------------------------------------------------------------
-- Table: REFERRAL DISPOSITION TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE referral_disposition_type(
	id integer PRIMARY KEY,
	code character varying(32) NOT NULL,
	description character varying(254) NOT NULL
); 

ALTER TABLE referral_disposition_type OWNER TO referral_group; 
GRANT ALL ON TABLE referral_disposition_type TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL SEQUENCE FOR NUMBER
-- ----------------------------------------------------------------------------
CREATE SEQUENCE referral_number_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9999
  START 1
  CACHE 1;
ALTER TABLE referral_number_seq OWNER TO referral_group;


-- ----------------------------------------------------------------------------
-- Table: REFERRAL
-- ----------------------------------------------------------------------------
CREATE TABLE referral
(
  id serial PRIMARY KEY,
  primary_classificiation_nr integer NOT NULL, 
  secondary_classificiation_nr integer NOT NULL,
  calendar_year integer NOT NULL,
  number integer DEFAULT nextval('referral_number_seq'),
  applicant_name character varying(254) NOT NULL,
  project_name character varying(254) NOT NULL,
  project_description character varying(2048) NOT NULL,
  project_location character varying(254) NOT NULL,
  project_background character varying(2048),
  external_project_id character varying(254),
  external_file_id character varying(254),
  external_agency_name character varying(254) NOT NULL,
  contact_name character varying(254) NOT NULL,
  contact_email character varying(254) NOT NULL,
  contact_phone character varying(254) NOT NULL,
  contact_address character varying(2048),
  type_id integer NOT NULL,
  application_type_id integer NOT NULL,
  target_referral_id integer,
  confidential boolean NOT NULL DEFAULT false,
  create_date timestamp NOT NULL DEFAULT CURRENT_DATE,
  create_user character varying(254) NOT NULL DEFAULT CURRENT_USER,
  receive_date timestamp without time zone NOT NULL,
  response_date timestamp without time zone NOT NULL,
  response_deadline timestamp without time zone NOT NULL,
  active_retention_period integer NOT NULL,
  semi_active_retention_period integer NOT NULL,
  final_disposition_id integer NOT NULL,
  provincial_assessment_level integer,
  final_assessment_level integer,
  status_id integer NOT NULL,
  stop_reason character varying(254),
  decision_id integer NOT NULL,
  provincial_decision_id integer NOT NULL,
  external_agency_type_id integer NOT NULL default 1,
  priority_id integer NOT NULL default 1,
  final_report_introduction text,
  final_report_conclusion text,
  CONSTRAINT fk_referral_application_type FOREIGN KEY (application_type_id) REFERENCES referral_application_type (id),
  CONSTRAINT fk_referral_status FOREIGN KEY (status_id) REFERENCES referral_status (id),
  CONSTRAINT fk_referral_decision FOREIGN KEY (decision_id) REFERENCES referral_decision (id),
  CONSTRAINT fk_referral_provincial_decision FOREIGN KEY (provincial_decision_id) REFERENCES referral_decision (id),
  CONSTRAINT fk_referral_disposition FOREIGN KEY (final_disposition_id) REFERENCES referral_disposition_type (id),
  CONSTRAINT fk_referral_type FOREIGN KEY (type_id) REFERENCES referral_type (id),
  CONSTRAINT fk_referral_external_agency_type FOREIGN KEY (external_agency_type_id) REFERENCES referral_external_agency_type (id),
  CONSTRAINT fk_referral_priority FOREIGN KEY (priority_id) REFERENCES referral_priority (id)
);

CREATE INDEX referral_application_type_id_idx
  ON referral
  USING btree
  (application_type_id);

CREATE INDEX referral_status_id_idx
  ON referral
  USING btree
  (status_id);

CREATE INDEX referral_type_id_idx
  ON referral
  USING btree
  (type_id);
  
CREATE INDEX referral_final_disposition_id_idx
  ON referral
  USING btree
  (final_disposition_id);

CREATE INDEX referral_external_agency_type_id_idx
  ON referral
  USING btree
  (external_agency_type_id);

CREATE INDEX referral_priority_id_idx
  ON referral
  USING btree
  (priority_id);

SELECT AddGeometryColumn('','referral','geom','26911','GEOMETRY',2);

CREATE INDEX referral_gist
  ON referral
  USING gist
  (geom);

ALTER TABLE referral OWNER TO referral_group; 
GRANT ALL ON TABLE referral TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: DOCUMENT TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE document_type(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	description character varying(254) NOT NULL
); 

ALTER TABLE document_type OWNER TO referral_group; 
GRANT ALL ON TABLE document_type TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: DOCUMENT
-- ----------------------------------------------------------------------------
CREATE TABLE document(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	description character varying(254) NOT NULL,
	display_url character varying(512) NOT NULL,
	download_url character varying(512) NOT NULL,
	type_id integer NOT NULL,
	document_id character varying(254) NOT NULL,
	addition_date timestamp NOT NULL,
	added_by character varying(254),
	confidential boolean NOT NULL DEFAULT false,
	include_in_report boolean NOT NULL DEFAULT false,
	referral_id integer NOT NULL,
	CONSTRAINT fk_document_type FOREIGN KEY (type_id) REFERENCES document_type (id),
	CONSTRAINT fk_document_referral FOREIGN KEY (referral_id) REFERENCES referral (id)
); 

CREATE INDEX document_type_id_idx
  ON document
  USING btree
  (type_id);

CREATE INDEX document_referral_id_idx
  ON document
  USING btree
  (referral_id);

ALTER TABLE document OWNER TO referral_group; 
GRANT ALL ON TABLE document TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL COMMENT
-- ----------------------------------------------------------------------------
CREATE TABLE referral_comment(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	content character varying(2048),
	creation_date timestamp NOT NULL,
	created_by character varying(254),
	include_in_report boolean NOT NULL DEFAULT false,
	report_content character varying(254),
	reference_layer_type_id integer,
	referral_id integer NOT NULL,
	CONSTRAINT fk_referral_comment_referral FOREIGN KEY (referral_id) REFERENCES referral (id),
	CONSTRAINT fk_referral_comment_layer_type FOREIGN KEY (reference_layer_type_id) REFERENCES reference_layer_type (id)
); 

CREATE INDEX referral_comment_referral_id_idx
  ON referral_comment
  USING btree
  (referral_id);

CREATE INDEX referral_comment_reference_layer_type_id_idx
  ON referral_comment
  USING btree
  (reference_layer_type_id);

ALTER TABLE referral_comment OWNER TO referral_group; 
GRANT ALL ON TABLE referral_comment TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: DOCUMENT COMMENT
-- ----------------------------------------------------------------------------
CREATE TABLE document_comment(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	content character varying(2048) NOT NULL,
	creation_date timestamp NOT NULL,
	created_by character varying(254),
	include_in_report boolean NOT NULL DEFAULT false,
	report_content character varying(2048),
	reference_layer_type_id integer,
	document_id integer NOT NULL,
	CONSTRAINT fk_document_comment_document FOREIGN KEY (document_id) REFERENCES document (id),
	CONSTRAINT fk_document_comment_layer_type FOREIGN KEY (reference_layer_type_id) REFERENCES reference_layer_type (id)
); 

CREATE INDEX document_comment_document_id_idx
  ON document_comment
  USING btree
  (document_id);

CREATE INDEX document_comment_reference_layer_type_id_idx
  ON document_comment
  USING btree
  (reference_layer_type_id);

ALTER TABLE document_comment OWNER TO referral_group; 
GRANT ALL ON TABLE document_comment TO referral_group; 


-- ----------------------------------------------------------------------------
-- Table: TEMPLATE
-- ----------------------------------------------------------------------------
CREATE TABLE template(
	id serial PRIMARY KEY,
	title character varying(254) NOT NULL,
	subject character varying(254) NOT NULL,
	description character varying(254) NOT NULL,
	mail_sender character varying(254) NOT NULL,
	string_content text,
	content oid,
	mime_type character varying(254) NOT NULL
); 

ALTER TABLE template OWNER TO referral_group; 
GRANT ALL ON TABLE template TO referral_group;


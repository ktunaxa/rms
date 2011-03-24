-- ----------------------------------------------------------------------------
-- Table: REFERENCE LAYER CATEGORY
-- ----------------------------------------------------------------------------
CREATE TABLE reference_layer_type(
	id serial NOT NULL,
	description character varying NOT NULL,
	base_layer boolean NOT NULL default false,
	CONSTRAINT reference_layer_type_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE reference_layer_type OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE reference_layer_type TO ktunaxa; #--;
GRANT ALL ON TABLE reference_layer_type TO postgres; #--;




-- ----------------------------------------------------------------------------
-- Table: REFERENCE LAYER
-- ----------------------------------------------------------------------------
CREATE TABLE reference_layer(
	id serial NOT NULL,
	name character varying NOT NULL,
	type_id bigint NOT NULL,
	scale_min character varying NOT NULL default 0,
	scale_max character varying NOT NULL default 0,
	visible_by_default boolean NOT NULL default false,
	CONSTRAINT reference_layer_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_layer_type FOREIGN KEY (type_id) REFERENCES reference_layer_type (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE reference_layer OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE reference_layer TO ktunaxa; #--;
GRANT ALL ON TABLE reference_layer TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE_VALUE layer
-- ----------------------------------------------------------------------------
CREATE TABLE reference_value
(
  id serial NOT NULL,
  layer_id bigint NOT NULL,
  style_code character varying,
  label character varying,
  geom geometry,
  CONSTRAINT reference_value_pkey PRIMARY KEY (id),
  CONSTRAINT fk_reference_value_layer FOREIGN KEY (layer_id)
      REFERENCES reference_layer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT enforce_dims_geom CHECK (st_ndims(geom) = 2),
  CONSTRAINT enforce_srid_geom CHECK (st_srid(geom) = 26911)
)
WITH (OIDS=FALSE); #--;
ALTER TABLE reference_value OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE reference_value TO ktunaxa; #--;
GRANT ALL ON TABLE reference_value TO postgres; #--;

-- Index: reference_value_the_geom_gist

-- DROP INDEX reference_value_the_geom_gist; #--;

CREATE INDEX reference_value_the_geom_gist
  ON reference_value
  USING gist
  (geom); #--;

-- ----------------------------------------------------------------------------
-- Table: REFERENCE VALUE KEY VALUE
-- ----------------------------------------------------------------------------
CREATE TABLE reference_value_attribute(
	id serial NOT NULL,
	reference_value_id bigint NOT NULL,
	the_key character varying NOT NULL,
	the_value character varying,
	CONSTRAINT reference_value_attribute_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_value_attribute_reference FOREIGN KEY (reference_value_id) REFERENCES reference_value (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE reference_value_attribute OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE reference_value_attribute TO ktunaxa; #--;
GRANT ALL ON TABLE reference_value_attribute TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE_BASE layer
-- ----------------------------------------------------------------------------
CREATE TABLE reference_base
(
  id serial NOT NULL,
  layer_id bigint NOT NULL,
  style_code character varying,
  label character varying,
  geom geometry,
  CONSTRAINT reference_base_pkey PRIMARY KEY (id),
  CONSTRAINT fk_reference_base_layer FOREIGN KEY (layer_id)
      REFERENCES reference_layer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT enforce_dims_geom CHECK (st_ndims(geom) = 2),
  CONSTRAINT enforce_srid_geom CHECK (st_srid(geom) = 26911)
)
WITH (
  OIDS=FALSE
); #--;
ALTER TABLE reference_base OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE reference_base TO ktunaxa; #--;
GRANT ALL ON TABLE reference_base TO postgres; #--;

-- Index: reference_base_the_geom_gist

-- DROP INDEX reference_base_the_geom_gist; #--;

CREATE INDEX reference_base_the_geom_gist
  ON reference_base
  USING gist
  (geom); #--;


-- ----------------------------------------------------------------------------
-- Table: REFERENCE VALUE KEY VALUE
-- ----------------------------------------------------------------------------
CREATE TABLE reference_base_attribute(
	id serial NOT NULL,
	reference_base_id bigint NOT NULL,
	the_key character varying NOT NULL,
	the_value character varying,
	CONSTRAINT reference_base_attribute_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_base_attribute_reference FOREIGN KEY (reference_base_id) REFERENCES reference_base (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE reference_base_attribute OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE reference_base_attribute TO ktunaxa; #--;
GRANT ALL ON TABLE reference_base_attribute TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: REFERRAL STATUS
-- ----------------------------------------------------------------------------
CREATE TABLE referral_status(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT referral_status_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE referral_status OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE referral_status TO ktunaxa; #--;
GRANT ALL ON TABLE referral_status TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: REFERRAL TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE referral_type(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT referral_type_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE referral_type OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE referral_type TO ktunaxa; #--;
GRANT ALL ON TABLE referral_type TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: REFERRAL APPLICATION TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE referral_application_type(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT referral_application_type_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE referral_application_type OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE referral_application_type TO ktunaxa; #--;
GRANT ALL ON TABLE referral_application_type TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: REFERRAL
-- ----------------------------------------------------------------------------
CREATE TABLE referral
(
  id serial NOT NULL,
  land_referral_id character varying,
  applicant_name character varying NOT NULL,
  project_name character varying NOT NULL,
  project_description character varying NOT NULL,
  project_location character varying NOT NULL,
  project_background character varying,
  external_project_id character varying,
  external_file_id character varying,
  external_agency_name character varying NOT NULL,
  contact_name character varying NOT NULL,
  contact_email character varying NOT NULL,
  contact_phone character varying NOT NULL,
  contact_address character varying,
  type_id bigint NOT NULL,
  application_type_id bigint NOT NULL,
  target_referral_id bigint NOT NULL,
  confidential boolean NOT NULL DEFAULT false,
  receive_date timestamp without time zone NOT NULL,
  response_date timestamp without time zone NOT NULL,
  response_deadline timestamp without time zone NOT NULL,
  active_retention_period integer NOT NULL,
  semi_active_retention_period integer NOT NULL,
  final_disposition integer NOT NULL,
  assessment_level integer,
  record_classification character varying NOT NULL,
  status_id bigint NOT NULL,
  geom geometry,
  CONSTRAINT referral_pkey PRIMARY KEY (id),
  CONSTRAINT fk_referral_application_type FOREIGN KEY (application_type_id)
      REFERENCES referral_application_type (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_referral_status FOREIGN KEY (status_id)
      REFERENCES referral_status (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_referral_type FOREIGN KEY (type_id)
      REFERENCES referral_type (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT enforce_dims_geom CHECK (st_ndims(geom) = 2),
  CONSTRAINT enforce_srid_geom CHECK (st_srid(geom) = 26911)
)
WITH (
  OIDS=FALSE
); #--;
ALTER TABLE referral OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE referral TO ktunaxa; #--;
GRANT ALL ON TABLE referral TO postgres; #--;

-- Index: referral_the_geom_gist

-- DROP INDEX referral_the_geom_gist; #--;

CREATE INDEX referral_the_geom_gist
  ON referral
  USING gist
  (geom); #--;




-- ----------------------------------------------------------------------------
-- Table: DOCUMENT TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE document_type(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT document_type_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE document_type OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE document_type TO ktunaxa; #--;
GRANT ALL ON TABLE document_type TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: DOCUMENT
-- ----------------------------------------------------------------------------
CREATE TABLE document(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
--	keywords character varying,
	type_id bigint NOT NULL,
	document_id character varying NOT NULL,
	addition_date timestamp NOT NULL,
	added_by character varying,
	confidential boolean NOT NULL DEFAULT false,
	include_in_report boolean NOT NULL DEFAULT false,
	referral_id bigint NOT NULL,
	CONSTRAINT document_pkey PRIMARY KEY (id),
	CONSTRAINT fk_document_type FOREIGN KEY (type_id) REFERENCES document_type (id),
	CONSTRAINT fk_document_referral FOREIGN KEY (referral_id) REFERENCES referral (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE document OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE document TO ktunaxa; #--;
GRANT ALL ON TABLE document TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: REFERRAL COMMENT
-- ----------------------------------------------------------------------------
CREATE TABLE referral_comment(
	id serial NOT NULL,
	title character varying NOT NULL,
	content character varying NOT NULL,
	creation_date timestamp NOT NULL,
	created_by character varying,
	include_in_report boolean NOT NULL DEFAULT false,
	report_content character varying,
	reference_layer_type_id bigint,
	referral_id bigint NOT NULL,
	CONSTRAINT referral_comment_pkey PRIMARY KEY (id),
	CONSTRAINT fk_referral_comment_referral FOREIGN KEY (referral_id) REFERENCES referral (id),
	CONSTRAINT fk_referrel_comment_layer_type FOREIGN KEY (reference_layer_type_id) REFERENCES reference_layer_type (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE referral_comment OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE referral_comment TO ktunaxa; #--;
GRANT ALL ON TABLE referral_comment TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: DOCUMENT COMMENT
-- ----------------------------------------------------------------------------
CREATE TABLE document_comment(
	id serial NOT NULL,
	title character varying NOT NULL,
	content character varying NOT NULL,
	creation_date timestamp NOT NULL,
	created_by character varying,
	include_in_report boolean NOT NULL DEFAULT false,
	report_content character varying,
	reference_layer_type_id bigint,
	document_id bigint NOT NULL,
	CONSTRAINT document_comment_pkey PRIMARY KEY (id),
	CONSTRAINT fk_document_comment_document FOREIGN KEY (document_id) REFERENCES document (id),
	CONSTRAINT fk_document_comment_layer_type FOREIGN KEY (reference_layer_type_id) REFERENCES reference_layer_type (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE document_comment OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE document_comment TO ktunaxa; #--;
GRANT ALL ON TABLE document_comment TO postgres; #--;



-- ----------------------------------------------------------------------------
-- Table: TEMPLATE
-- ----------------------------------------------------------------------------
CREATE TABLE template(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	content oid NOT NULL,
	mime_type character varying NOT NULL,
	CONSTRAINT template_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE); #--;

ALTER TABLE template OWNER TO ktunaxa; #--;
GRANT ALL ON TABLE template TO ktunaxa; #--;
GRANT ALL ON TABLE template TO postgres; #--;


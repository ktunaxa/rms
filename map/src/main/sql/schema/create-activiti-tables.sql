


CREATE TABLE act_id_user (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    first_ character varying(255),
    last_ character varying(255),
    email_ character varying(255),
    pwd_ character varying(255),
    picture_id_ character varying(64)
);

ALTER TABLE ONLY act_id_user
    ADD CONSTRAINT act_id_user_pkey PRIMARY KEY (id_);

ALTER TABLE act_id_user OWNER TO referral_group;
GRANT ALL ON TABLE act_id_user TO referral_group;



CREATE TABLE act_id_group (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    type_ character varying(255)
);

ALTER TABLE ONLY act_id_group
    ADD CONSTRAINT act_id_group_pkey PRIMARY KEY (id_);

ALTER TABLE act_id_group OWNER TO referral_group;
GRANT ALL ON TABLE act_id_group TO referral_group;



CREATE TABLE act_id_membership (
    user_id_ character varying(64) NOT NULL,
    group_id_ character varying(64) NOT NULL
);

ALTER TABLE ONLY act_id_membership
    ADD CONSTRAINT act_id_membership_pkey PRIMARY KEY (user_id_, group_id_);

ALTER TABLE ONLY act_id_membership
    ADD CONSTRAINT act_fk_memb_group FOREIGN KEY (group_id_) REFERENCES act_id_group(id_);

ALTER TABLE ONLY act_id_membership
    ADD CONSTRAINT act_fk_memb_user FOREIGN KEY (user_id_) REFERENCES act_id_user(id_);

CREATE INDEX act_idx_memb_user ON act_id_membership USING btree (user_id_);
CREATE INDEX act_idx_memb_group ON act_id_membership USING btree (group_id_);

ALTER TABLE act_id_membership OWNER TO referral_group;
GRANT ALL ON TABLE act_id_membership TO referral_group;

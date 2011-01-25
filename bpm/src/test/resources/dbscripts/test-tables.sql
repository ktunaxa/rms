DROP TABLE loadrequest;

CREATE TABLE loanrequest(
	id serial NOT NULL,
	customername character varying,
	amount integer,
	approved boolean NOT NULL DEFAULT false,
	CONSTRAINT loanrequest_pkey PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
);

ALTER TABLE loanrequest OWNER TO ktunaxa;
GRANT ALL ON TABLE loanrequest TO ktunaxa;
GRANT ALL ON TABLE loanrequest TO postgres;


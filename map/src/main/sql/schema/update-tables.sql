-- KTU-161 content field in comment table is now allowed to be NULL
ALTER TABLE referral_comment ALTER COLUMN content DROP NOT NULL;

-- KTU-113 also need sender e-mail
ALTER TABLE template ADD COLUMN mail_sender character varying(254) NOT NULL;

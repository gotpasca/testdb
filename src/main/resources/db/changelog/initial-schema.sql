CREATE SCHEMA IF NOT EXISTS ONBOARDING;
SET SCHEMA ONBOARDING;

CREATE TABLE option_table (
  id UUID PRIMARY KEY,
  name VARCHAR(128) NOT NULL,
  tags VARCHAR(128) NOT NULL,
  CONSTRAINT uc_option_name_tags UNIQUE(name, tags)
);

CREATE TABLE option_entry (
  id UUID PRIMARY KEY,
  option_id UUID NOT NULL,
  data VARCHAR(1024),
  CONSTRAINT fk_option_entry_option FOREIGN KEY(option_id) REFERENCES option_table(id)
);

CREATE INDEX idx_option_entry_option_id ON option_entry(option_id);

INSERT INTO option_table (id, name, tags)
VALUES
  ('11111111-1111-1111-1111-111111111111','currency','client'),
  ('22222222-2222-2222-2222-222222222222','countries','user');

INSERT INTO option_entry (id, option_id, data)
VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa','11111111-1111-1111-1111-111111111111','{"key":"USD","value":"Dollar"}'),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb','11111111-1111-1111-1111-111111111111','{"key":"EURO","value":"Euro"}'),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc','22222222-2222-2222-2222-222222222222','{"key":"FR","value":"France"}'),
  ('dddddddd-dddd-dddd-dddd-dddddddddddd','22222222-2222-2222-2222-222222222222','{"key":"EN","value":"England"}');

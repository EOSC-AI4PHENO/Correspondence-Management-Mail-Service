CREATE SCHEMA IF NOT EXISTS queue;
-- Drop tables if they exist
DROP TABLE IF EXISTS queue."messages" CASCADE;


-- Create public.formHeader table
CREATE TABLE queue."messages" (
                                  "id" bigserial not null primary key,
                                  "sendTo" varchar(255),
                                  "subject" text,
                                  "content" text,
                                  "realm" varchar(255),
                                  "isSent" bool,
                                  "sentAt" timestamptz,
                                  "messageFromServer" text,
                                  "attempts" int8,
                                  "createdBy" varchar(255),
                                  "createdAt" timestamptz NOT NULL DEFAULT now(),
                                  "modifiedAt" timestamptz NOT NULL DEFAULT now()
);

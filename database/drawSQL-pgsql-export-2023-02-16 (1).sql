CREATE TABLE "event"(
    "id" BIGINT NOT NULL,
    "id_organisator" BIGINT NOT NULL,
    "title" TEXT NOT NULL,
    "description" TEXT NOT NULL,
    "eventImage" TEXT NOT NULL,
    "tags" SMALLINT NOT NULL,
    "date" DATE NOT NULL,
    "location" TEXT NOT NULL,
    "isOpen" BOOLEAN NOT NULL,
    "isMainOrganiser" BOOLEAN NOT NULL
);
CREATE INDEX "event_date_index" ON
    "event"("date");
CREATE INDEX "event_tags_index" ON
    "event"("tags");
CREATE INDEX "event_id_organisator_index" ON
    "event"("id_organisator");
ALTER TABLE
    "event" ADD PRIMARY KEY("id");
ALTER TABLE
    "event" ADD CONSTRAINT "event_id_organisator_unique" UNIQUE("id_organisator");
COMMENT
ON COLUMN
    "event"."id" IS 'bigserial';
COMMENT
ON COLUMN
    "event"."tags" IS 'тут массив';
CREATE TABLE "user"(
    "id" BIGINT NOT NULL,
    "id_istu" BIGINT NULL,
    "fullname" TEXT NOT NULL,
    "studentGroup" TEXT NULL,
    "id_status" SMALLINT NOT NULL
);
CREATE INDEX "user_id_status_index" ON
    "user"("id_status");
ALTER TABLE
    "user" ADD PRIMARY KEY("id");
ALTER TABLE
    "user" ADD CONSTRAINT "user_id_istu_unique" UNIQUE("id_istu");
COMMENT
ON COLUMN
    "user"."id" IS 'bigserial';
CREATE TABLE "my_event"(
    "id" BIGINT NOT NULL,
    "id_event" BIGINT NOT NULL,
    "id_user" BIGINT NOT NULL,
    "presence" BOOLEAN NOT NULL,
    "qr_image" TEXT NOT NULL
);
ALTER TABLE
    "my_event" ADD PRIMARY KEY("id");
ALTER TABLE
    "my_event" ADD CONSTRAINT "my_event_id_event_unique" UNIQUE("id_event");
ALTER TABLE
    "my_event" ADD CONSTRAINT "my_event_id_user_unique" UNIQUE("id_user");
COMMENT
ON COLUMN
    "my_event"."id" IS 'bigserial';
CREATE TABLE "user_status"(
    "id_status" SMALLINT NOT NULL,
    "status" TEXT NOT NULL
);
ALTER TABLE
    "user_status" ADD PRIMARY KEY("id_status");
ALTER TABLE
    "user_status" ADD CONSTRAINT "user_status_status_unique" UNIQUE("status");
COMMENT
ON COLUMN
    "user_status"."id_status" IS 'smallserial';
CREATE TABLE "tags"(
    "id" SMALLINT NOT NULL,
    "tag" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "tags" ADD PRIMARY KEY("id");
ALTER TABLE
    "tags" ADD CONSTRAINT "tags_tag_unique" UNIQUE("tag");
COMMENT
ON COLUMN
    "tags"."id" IS 'smallserial';
ALTER TABLE
    "my_event" ADD CONSTRAINT "my_event_id_event_foreign" FOREIGN KEY("id_event") REFERENCES "event"("id");
ALTER TABLE
    "my_event" ADD CONSTRAINT "my_event_id_user_foreign" FOREIGN KEY("id_user") REFERENCES "user"("id");
ALTER TABLE
    "event" ADD CONSTRAINT "event_id_organisator_foreign" FOREIGN KEY("id_organisator") REFERENCES "user"("id");
ALTER TABLE
    "user" ADD CONSTRAINT "user_id_status_foreign" FOREIGN KEY("id_status") REFERENCES "user_status"("id_status");
CREATE TABLE "question" (
  "id" int PRIMARY KEY,
  "timelimit" int,
  "memlimit" int
);

CREATE TABLE "submission" (
  "id" text PRIMARY KEY,
  "question" int,
  "status" text
);

ALTER TABLE "submission" ADD FOREIGN KEY ("question") REFERENCES "question" ("id");

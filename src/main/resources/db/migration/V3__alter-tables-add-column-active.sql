alter table users add  active BOOLEAN NOT NULL DEFAULT true;

alter table books add  active BOOLEAN NOT NULL DEFAULT true;

alter table book_copies add  active BOOLEAN NOT NULL DEFAULT true;

update users set active = true;
update books set active = true;
update book_copies set active = true;
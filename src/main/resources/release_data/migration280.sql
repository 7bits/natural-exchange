ALTER TABLE advertisement ADD is_new BIT(1) NOT NULL;


ALTER TABLE advertisement ADD COLUMN is_del TINYINT(1) AFTER is_deleted;
UPDATE advertisement SET is_del = is_deleted;
ALTER TABLE advertisement DROP is_deleted;
ALTER TABLE advertisement CHANGE is_del is_deleted TINYINT(1);

ALTER TABLE advertisement ADD COLUMN is_visible TINYINT(1) AFTER is_new;
UPDATE advertisement SET is_visible = is_new;
ALTER TABLE advertisement DROP is_new;

UPDATE advertisement SET is_visible=1 WHERE is_deleted=0;
UPDATE advertisement SET is_visible=0 WHERE is_deleted=1;



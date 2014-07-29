ALTER TABLE advertisement ADD is_new BIT(1) NOT NULL;


ALTER TABLE advertisement ADD COLUMN is_del TINYINT(1) AFTER is_deleted;
UPDATE advertisement SET is_del = is_deleted;
ALTER TABLE advertisement DROP is_deleted;
ALTER TABLE advertisement CHANGE is_del is_deleted TINYINT(1);

ALTER TABLE advertisement ADD COLUMN is_vis TINYINT(1) AFTER is_visible;
UPDATE advertisement SET is_vis = is_visible;
ALTER TABLE advertisement DROP is_visible;
ALTER TABLE advertisement CHANGE is_vis is_visible TINYINT(1);


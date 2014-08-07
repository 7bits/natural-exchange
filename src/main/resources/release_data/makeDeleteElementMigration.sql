UPDATE advertisement SET is_deleted=1 WHERE is_visible=0;
UPDATE advertisement SET is_deleted=0 WHERE is_visible=1;

ALTER TABLE advertisement DROP is_visible;
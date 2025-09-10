ALTER TABLE loans ADD renewals INTEGER;
UPDATE loans SET renewals = 0;
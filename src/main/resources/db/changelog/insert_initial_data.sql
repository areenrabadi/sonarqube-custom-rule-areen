-- Inserting values into the TYPES table
INSERT INTO TYPES (ID, CODE, DESCRIPTION, NAME)
VALUES (1, 'BANK_INFO', '', 'Bank Information');

-- Inserting values into the EXTENSIONS table
INSERT INTO EXTENSIONS (ID, CODE, DESCRIPTION, NAME)
VALUES (1, 'image/png', '', 'PNG Image');

INSERT INTO EXTENSIONS (ID, CODE, DESCRIPTION, NAME)
VALUES (2, 'application/pdf', '', 'PDF File');

-- Inserting values into the SETTINGS table
INSERT INTO SETTINGS (ID, CREATED_AT, CREATED_BY, DELETED_AT, DELETED_BY, LAST_MODIFIED_AT, LAST_MODIFIED_BY,
                      DIRECTORY_PATTERN, MAX_ALLOWED_SIZE, TYPE_ID)
VALUES (1, CURRENT_TIMESTAMP, null, null, null, null, null,
        '/home/ftpuser/profile/{?profileId}/applicationId/{?applicationId}/image/' , 50000, 1);

-- Inserting values into the SETTING_EXTENSIONS table
INSERT INTO SETTING_EXTENSIONS (SETTING_ID, EXTENSION_ID) VALUES (1, 1);
INSERT INTO SETTING_EXTENSIONS (SETTING_ID, EXTENSION_ID) VALUES (1, 2);
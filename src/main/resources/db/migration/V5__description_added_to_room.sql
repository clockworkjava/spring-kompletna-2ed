ALTER TABLE room ADD COLUMN description VARCHAR(255) AFTER number;

UPDATE room SET description = 'Ladny pokoj';

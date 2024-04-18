CREATE TABLE users (
	user_id SERIAL PRIMARY KEY,
	role TEXT,
	name TEXT,
	surname TEXT,
	email TEXT,
	password TEXT
);

CREATE TABLE culinary_notes (
	culinary_note_id SERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES users(user_id),
	category TEXT,
	name TEXT,
	description TEXT,
	instructions TEXT
);

CREATE TABLE user_ratings (
	user_id BIGINT REFERENCES users(user_id),
	culinary_note_id BIGINT REFERENCES culinary_notes(culinary_note_id),
	PRIMARY KEY(user_id, culinary_note_id),
	grade SMALLINT,
	comment TEXT
);

CREATE INDEX idx_user_ratings_usr_id ON user_ratings(user_id);
CREATE INDEX idx_user_ratings_clnr_nt_id ON user_ratings(culinary_note_id);

CREATE TABLE ingredients (
	ingredient_id SERIAL PRIMARY KEY,
	name TEXT,
	unit_of_measurement TEXT
);

CREATE TABLE ingredients_in_culinary_notes (
	ingredient_id BIGINT REFERENCES ingredients(ingredient_id),
	culinary_note_id BIGINT REFERENCES culinary_notes(culinary_note_id),
	PRIMARY KEY(ingredient_id, culinary_note_id),
	amount INTEGER
);

CREATE INDEX idx_ingredients_in_culinary_notes_ingrdnt_id ON ingredients_in_culinary_notes(ingredient_id);
CREATE INDEX idx_ingredients_in_culinary_notes_clnr_nt_id ON ingredients_in_culinary_notes(culinary_note_id);

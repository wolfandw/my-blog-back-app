DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS post;

CREATE TABLE IF NOT EXISTS post (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    text TEXT NOT NULL,
    likes_count INTEGER DEFAULT 0 NOT NULL,
    comments_count INTEGER DEFAULT 0 NOT NULL,
    image VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE INDEX idx_post_title ON post(title);

CREATE TABLE IF NOT EXISTS comment (
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);
CREATE INDEX idx_comment_post_id ON comment(post_id);

CREATE TABLE IF NOT EXISTS tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);
CREATE INDEX idx_tag_name ON tag(name);

CREATE TABLE IF NOT EXISTS post_tag (
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);
CREATE INDEX idx_post_tag_tag_id ON post_tag(tag_id);
CREATE INDEX idx_post_tag_post_id ON post_tag(post_id);

INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 01 title',
 'Post 01 text for 128-lenght string testtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt128qq',
  1, 1, '1.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 02 title', 'Post 02 text', 2, 2, '2.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 03 title', 'Post 03 text', 3, 3, '3.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 04 title', 'Post 04 text', 4, 1, '4.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 05 title', 'Post 05 text', 5, 2, '5.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 06 title', 'Post 06 text', 6, 3, '6.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 07 title', 'Post 07 text', 7, 1, '7.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 08 title', 'Post 08 text', 8, 2, '8.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 09 title', 'Post 09 text', 9, 3, '9.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 10 title', 'Post 10 text', 10, 1, '10.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 11 title', 'Post 11 text', 11, 2, '11.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 12 title', 'Post 12 text', 12, 3, '12.png');
INSERT INTO post(title, text, likes_count, comments_count, image) VALUES ('Post 13 title', 'Post 13 text', 13, 1, '13.png');

INSERT INTO comment(post_id, text) VALUES (1, 'Post 1 comment 1');
INSERT INTO comment(post_id, text) VALUES (1, 'Post 1 comment 2');
INSERT INTO comment(post_id, text) VALUES (1, 'Post 1 comment 3');
INSERT INTO comment(post_id, text) VALUES (2, 'Post 2 comment 1');
INSERT INTO comment(post_id, text) VALUES (2, 'Post 2 comment 2');
INSERT INTO comment(post_id, text) VALUES (2, 'Post 2 comment 3');
INSERT INTO comment(post_id, text) VALUES (3, 'Post 3 comment 1');
INSERT INTO comment(post_id, text) VALUES (3, 'Post 3 comment 2');
INSERT INTO comment(post_id, text) VALUES (3, 'Post 3 comment 3');
INSERT INTO comment(post_id, text) VALUES (4, 'Post 4 comment 1');
INSERT INTO comment(post_id, text) VALUES (4, 'Post 4 comment 2');
INSERT INTO comment(post_id, text) VALUES (4, 'Post 4 comment 3');
INSERT INTO comment(post_id, text) VALUES (5, 'Post 5 comment 1');
INSERT INTO comment(post_id, text) VALUES (5, 'Post 5 comment 2');
INSERT INTO comment(post_id, text) VALUES (5, 'Post 5 comment 3');
INSERT INTO comment(post_id, text) VALUES (6, 'Post 6 comment 1');
INSERT INTO comment(post_id, text) VALUES (6, 'Post 6 comment 2');
INSERT INTO comment(post_id, text) VALUES (6, 'Post 6 comment 3');
INSERT INTO comment(post_id, text) VALUES (7, 'Post 7 comment 1');
INSERT INTO comment(post_id, text) VALUES (7, 'Post 7 comment 2');
INSERT INTO comment(post_id, text) VALUES (7, 'Post 7 comment 3');
INSERT INTO comment(post_id, text) VALUES (8, 'Post 8 comment 1');
INSERT INTO comment(post_id, text) VALUES (8, 'Post 8 comment 2');
INSERT INTO comment(post_id, text) VALUES (8, 'Post 8 comment 3');
INSERT INTO comment(post_id, text) VALUES (9, 'Post 9 comment 1');
INSERT INTO comment(post_id, text) VALUES (9, 'Post 9 comment 2');
INSERT INTO comment(post_id, text) VALUES (9, 'Post 9 comment 3');
INSERT INTO comment(post_id, text) VALUES (10, 'Post 10 comment 1');
INSERT INTO comment(post_id, text) VALUES (10, 'Post 10 comment 2');
INSERT INTO comment(post_id, text) VALUES (10, 'Post 10 comment 3');
INSERT INTO comment(post_id, text) VALUES (11, 'Post 11 comment 1');
INSERT INTO comment(post_id, text) VALUES (11, 'Post 11 comment 2');
INSERT INTO comment(post_id, text) VALUES (11, 'Post 11 comment 3');
INSERT INTO comment(post_id, text) VALUES (12, 'Post 12 comment 1');
INSERT INTO comment(post_id, text) VALUES (12, 'Post 12 comment 2');
INSERT INTO comment(post_id, text) VALUES (12, 'Post 12 comment 3');
INSERT INTO comment(post_id, text) VALUES (13, 'Post 13 comment 1');
INSERT INTO comment(post_id, text) VALUES (13, 'Post 13 comment 2');
INSERT INTO comment(post_id, text) VALUES (13, 'Post 13 comment 3');

INSERT INTO tag(name) VALUES ('Tag 1');
INSERT INTO tag(name) VALUES ('Tag 2');
INSERT INTO tag(name) VALUES ('Tag 3');

INSERT INTO post_tag(post_id, tag_id) VALUES (1, 1);
INSERT INTO post_tag(post_id, tag_id) VALUES (2, 2);
INSERT INTO post_tag(post_id, tag_id) VALUES (3, 3);
INSERT INTO post_tag(post_id, tag_id) VALUES (4, 1);
INSERT INTO post_tag(post_id, tag_id) VALUES (4, 2);
INSERT INTO post_tag(post_id, tag_id) VALUES (5, 1);
INSERT INTO post_tag(post_id, tag_id) VALUES (5, 2);
INSERT INTO post_tag(post_id, tag_id) VALUES (6, 1);
INSERT INTO post_tag(post_id, tag_id) VALUES (6, 2);
INSERT INTO post_tag(post_id, tag_id) VALUES (7, 2);
INSERT INTO post_tag(post_id, tag_id) VALUES (7, 3);
INSERT INTO post_tag(post_id, tag_id) VALUES (8, 2);
INSERT INTO post_tag(post_id, tag_id) VALUES (8, 3);
INSERT INTO post_tag(post_id, tag_id) VALUES (9, 2);
INSERT INTO post_tag(post_id, tag_id) VALUES (9, 3);
INSERT INTO post_tag(post_id, tag_id) VALUES (10, 1);
INSERT INTO post_tag(post_id, tag_id) VALUES (10, 3);
INSERT INTO post_tag(post_id, tag_id) VALUES (11, 1);
INSERT INTO post_tag(post_id, tag_id) VALUES (11, 3);
INSERT INTO post_tag(post_id, tag_id) VALUES (12, 1);
INSERT INTO post_tag(post_id, tag_id) VALUES (12, 3);
INSERT INTO post_tag(post_id, tag_id) VALUES (13, 1);
INSERT INTO post_tag(post_id, tag_id) VALUES (13, 2);
INSERT INTO post_tag(post_id, tag_id) VALUES (13, 3);
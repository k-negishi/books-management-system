-- H2データベースのbook.idのsequenceを定義
CREATE SEQUENCE IF NOT EXISTS book_id_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
;

-- bookテーブル作成
CREATE TABLE IF NOT EXISTS book (
    id INT DEFAULT NEXT VALUE FOR book_id_sequence PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL
);

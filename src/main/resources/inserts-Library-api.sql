INSERT INTO users (name, email, password, phone, user_type, street, district, postal_code, city, state, house_number, complement) VALUES
('Maria Silva', 'maria.silva@gmail.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '61987654321', 'PROFESSOR', 'Avenida Paulista', 'Bela Vista', '01310100', 'São Paulo', 'SP', '1500', 'apto 101'),
('Carlos Eduardo Santos', 'carlos.santos@yahoo.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '21999887766', 'STUDENT', 'Rua das Palmeiras', 'Copacabana', '22011000', 'Rio de Janeiro', 'RJ', '50', 'casa'),
('Ana Clara Oliveira', 'ana.clara@hotmail.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '31988884444', 'STUDENT', 'Rua de Ouro Preto', 'Santo Agostinho', '30170050', 'Belo Horizonte', 'MG', '100', 'bloco A'),
('Rafael Costa', 'rafael.costa@email.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '85977771111', 'PROFESSOR', 'Avenida Beira Mar', 'Meireles', '60165120', 'Fortaleza', 'CE', '2020', NULL),
('Juliana Lima', 'juliana.lima@uol.com.br', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '51966663333', 'STUDENT', 'Rua dos Andradas', 'Centro Histórico', '90020000', 'Porto Alegre', 'RS', '85', 'sala 3'),
('Pedro Almeida', 'pedro.almeida@gmail.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '41955552222', 'PROFESSOR', 'Rua Marechal Deodoro', 'Centro', '80020050', 'Curitiba', 'PR', '300', 'sala 401'),
('Sofia Mendes', 'sofia.mendes@email.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '71944449999', 'STUDENT', 'Rua Chile', 'Centro', '40020020', 'Salvador', 'BA', '12', NULL),
('Lucas Pereira', 'lucas.pereira@yahoo.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '81933330000', 'STUDENT', 'Rua do Sol', 'Santo Antônio', '50010210', 'Recife', 'PE', '70', 'prédio 2'),
('Beatriz Martins', 'beatriz.martins@hotmail.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '62922225555', 'PROFESSOR', 'Avenida Goiás', 'Setor Central', '74010010', 'Goiânia', 'GO', '1000', 'sala 2'),
('Guilherme Jales', 'guilherme.jales@gmail.com', '$2a$12$Nk3pxJy2EUvaknfeYh7HAenhGt6U0yxI3ZepxRyNZXEGsCQ3rnI8e', '61999998888', 'STUDENT', 'Rua da Paz', 'Asa Norte', '95131759', 'Brasilia', 'DF', '10', 'casa'),
('Admin', 'admin@gmail.com', '$2a$12$IeODz/9VTJnOgavCHUSmf.mf6MBi8Cc8x79mWEL.4.CtGHKlxfQXy', '55993246754', 'ADMIN', 'Rua xpto', 'Bairro xpto', '35131739', 'Brasilia', 'DF', '10', 'casa'),
('Professor', 'professor@gmail.com', '$2a$12$Pfg1HgCuyML9Ze4.OvqRDOiDZ0KL4bhnqWPQp7q.j5Pypsk3d/c0u', '55942216759', 'PROFESSOR', 'Rua xpto', 'Bairro xpto', '35131766', 'Brasilia', 'DF', '11', 'ap 601'),
('Student', 'student@gmail.com', '$2a$12$UCa6rv6hjHqoczw.bvEOWuYUlbalsqqnhU2k48nWGAkE4qTvWikHG', '55991242559', 'STUDENT', 'Rua xpto', 'Bairro xpto', '35131742', 'Brasilia', 'DF', '12', 'casa');



INSERT INTO authors (name, nationality, birth_date) VALUES
('Gabriel García Márquez', 'Colombiano', '1927-03-06'),
('Jane Austen', 'Britânica', '1775-12-16'),
('Haruki Murakami', 'Japonês', '1949-01-12'),
('Stephen King', 'Americano', '1947-09-21'),
('Clarice Lispector', 'Brasileira', '1920-12-10'),
('George Orwell', 'Britânico', '1903-06-25'),
('J.K. Rowling', 'Britânica', '1965-07-31'),
('J.R.R. Tolkien', 'Britânico', '1892-01-03'),
('Agatha Christie', 'Britânica', '1890-09-15'),
('Machado de Assis', 'Brasileiro', '1839-06-21');

INSERT INTO authors (name, nationality, birth_date) VALUES
('Ernest Hemingway', 'Americano', '1899-07-21'),
('Fiódor Dostoiévski', 'Russo', '1821-11-11'),
('Liev Tolstói', 'Russo', '1828-09-09'),
('William Shakespeare', 'Britânico', '1564-04-26'),
('Jules Verne', 'Francês', '1828-02-08'),
('Fernando Pessoa', 'Português', '1888-06-13'),
('Virginia Woolf', 'Britânica', '1882-01-25'),
('Jorge Luis Borges', 'Argentino', '1899-08-24'),
('Charles Dickens', 'Britânico', '1812-02-07'),
('Franz Kafka', 'Austríaco', '1883-07-03');

INSERT INTO authors (name, nationality, birth_date) VALUES
('Robert C. Martin', 'Estadunidense', '1952-12-05'),
('Eric Evans', 'Estadunidense', '1958-12-19'),
('Martin Fowler', 'Inglês', '1963-12-18'),
('Kent Beck', 'Estadunidense', '1961-03-31'),
('Addy Osmani', 'Inglês', '1988-12-25'),
('Steve McConnell', 'Estadunidense', '1962-09-03');


INSERT INTO books (title, publication_year, publisher, genre, isbn, author_id, active) VALUES
('Cem Anos de Solidão', 1967, 'Editorial Sudamericana', 'Realismo Mágico', '9788535914846', 1, true),
('Orgulho e Preconceito', 1813, 'T. Egerton', 'Romance', '9788573265551', 2, true),
('Norwegian Wood', 1987, 'Kodansha', 'Ficção', '9780099448832', 3, true),
('O Iluminado', 1977, 'Doubleday', 'Terror', '9788581050410', 4, true),
('A Hora da Estrela', 1977, 'Editora Rocco', 'Ficção', '9788532507624', 5, true),
('1984', 1949, 'Secker & Warburg', 'Distopia', '9788521313330', 6, true),
('Harry Potter e a Pedra Filosofal', 1997, 'Bloomsbury', 'Fantasia', '9788532511010', 7, true),
('O Senhor dos Anéis', 1954, 'George Allen & Unwin', 'Fantasia', '9788533615309', 8, true),
('O Caso dos Dez Negrinhos', 1939, 'Collins Crime Club', 'Mistério', '9788520935541', 9, true),
('Dom Casmurro', 1899, 'Livraria Garnier', 'Realismo', '9788572328103', 10, true),
('O Velho e o Mar', 1952, 'Scribner', 'Ficção', '9788501062006', 11, true);

INSERT INTO books (title, publication_year, publisher, genre, isbn, author_id, active) VALUES
('Crime e Castigo', 1866, 'The Russian Messenger', 'Ficção Psicológica', '9788573268484', 12, true),
('Guerra e Paz', 1869, 'The Russian Messenger', 'Ficção Histórica', '9788532525147', 13, true),
('Hamlet', 1603, 'Global Editora', 'Tragédia', '9788526012435', 14, true),
('Vinte Mil Léguas Submarinas', 1870, 'Jules Hetzel', 'Aventura', '9788573262840', 15, true),
('O Livro do Desassossego', 1982, 'Ática', 'Filosofia', '9788508101452', 16, true),
('Mrs. Dalloway', 1925, 'Hogarth Press', 'Modernismo', '9788580550058', 17, true),
('O Aleph', 1949, 'Losada', 'Contos', '9788535904351', 18, true),
('Um Conto de Natal', 1843, 'Chapman & Hall', 'Ficção', '9788572322316', 19, true),
('A Metamorfose', 1915, 'Kurt Wolff Verlag', 'Novela', '9788572322217', 20, true);

INSERT INTO books (title, publication_year, publisher, genre, isbn, author_id, active) VALUES
('Código Limpo: Habilidades Práticas do Agile Software', 2008, 'Alta Books', 'Programação', '9788576082675', 1, TRUE),
('Código Limpo para Profissionais: O Manual de Ética e Código de Conduta do Engenheiro de Software', 2011, 'Alta Books', 'Engenharia de Software', '9788576086208', 1, TRUE),
('Domain-Driven Design: Atacando as Complexidades no Coração do Software', 2003, 'Addison-Wesley', 'Engenharia de Software', '9780321125217', 2, TRUE),
('Refactoring: Melhorando o Design de Código Existente', 1999, 'Addison-Wesley', 'Programação', '9780201485677', 3, TRUE),
('Test-Driven Development: By Example', 2003, 'Addison-Wesley', 'Programação', '9780321146533', 4, TRUE),
('Learning JavaScript Design Patterns', 2012, 'O''Reilly Media', 'Programação', '9781449334810', 5, TRUE),
('Code Complete: A Practical Handbook of Software Construction', 1993, 'Microsoft Press', 'Engenharia de Software', '9780735619678', 6, TRUE);


INSERT INTO book_copies (book_id, inventory_code, available, active) VALUES
(1, 'GGM-C-1967-001', TRUE, TRUE),
(1, 'GGM-C-1967-002', FALSE, TRUE),
(1, 'GGM-C-1967-003', TRUE, TRUE),

(2, 'JA-OP-1813-001', TRUE, TRUE),
(2, 'JA-OP-1813-002', TRUE, TRUE),
(2, 'JA-OP-1813-003', FALSE, TRUE),

(3, 'HM-NW-1987-001', TRUE, TRUE),
(3, 'HM-NW-1987-002', TRUE, TRUE),
(3, 'HM-NW-1987-003', TRUE, TRUE),

(4, 'SK-I-1977-001', TRUE, TRUE),
(4, 'SK-I-1977-002', TRUE, TRUE),
(4, 'SK-I-1977-003', TRUE, TRUE),

(5, 'CL-HE-1977-001', TRUE, TRUE),
(5, 'CL-HE-1977-002', TRUE, TRUE),
(5, 'CL-HE-1977-003', TRUE, TRUE),

(6, 'GO-1984-001', TRUE, TRUE),
(6, 'GO-1984-002', TRUE, TRUE),
(6, 'GO-1984-003', TRUE, TRUE),

(7, 'JKR-HP1-1997-001', FALSE, TRUE),
(7, 'JKR-HP1-1997-002', TRUE, TRUE),
(7, 'JKR-HP1-1997-003', TRUE, TRUE),

(8, 'JRRT-LOTR-1954-001', FALSE, TRUE),
(8, 'JRRT-LOTR-1954-002', TRUE, TRUE),
(8, 'JRRT-LOTR-1954-003', FALSE, TRUE),

(9, 'AC-TN-1939-001', TRUE, TRUE),
(9, 'AC-TN-1939-002', TRUE, TRUE),
(9, 'AC-TN-1939-003', TRUE, TRUE),

(10, 'MA-DC-1899-001', TRUE, TRUE),
(10, 'MA-DC-1899-002', TRUE, TRUE),
(10, 'MA-DC-1899-003', TRUE, TRUE);

INSERT INTO book_copies (book_id, inventory_code, available, active) VALUES
(11, 'EH-O-1952-001', FALSE, TRUE),
(11, 'EH-O-1952-002', TRUE, TRUE),
(11, 'EH-O-1952-003', TRUE, TRUE),

(12, 'FD-CC-1866-001', TRUE, TRUE),
(12, 'FD-CC-1866-002', TRUE, TRUE),
(12, 'FD-CC-1866-003', TRUE, TRUE),

(13, 'LT-WP-1869-001', TRUE, TRUE),
(13, 'LT-WP-1869-002', TRUE, TRUE),
(13, 'LT-WP-1869-003', TRUE, TRUE),

(14, 'WS-H-1603-001', TRUE, TRUE),
(14, 'WS-H-1603-002', TRUE, TRUE),
(14, 'WS-H-1603-003', TRUE, TRUE),

(15, 'JV-20K-1870-001', TRUE, TRUE),
(15, 'JV-20K-1870-002', TRUE, TRUE),
(15, 'JV-20K-1870-003', TRUE, TRUE),

(16, 'FP-LOD-1982-001', TRUE, TRUE),
(16, 'FP-LOD-1982-002', TRUE, TRUE),
(16, 'FP-LOD-1982-003', TRUE, TRUE),

(17, 'VW-MD-1925-001', TRUE, TRUE),
(17, 'VW-MD-1925-002', TRUE, TRUE),
(17, 'VW-MD-1925-003', TRUE, TRUE),

(18, 'JLB-A-1949-001', TRUE, TRUE),
(18, 'JLB-A-1949-002', TRUE, TRUE),
(18, 'JLB-A-1949-003', TRUE, TRUE),

(19, 'CD-CC-1843-001', TRUE, TRUE),
(19, 'CD-CC-1843-002', TRUE, TRUE),
(19, 'CD-CC-1843-003', TRUE, TRUE),

(20, 'FK-M-1915-001', TRUE, TRUE),
(20, 'FK-M-1915-002', TRUE, TRUE),
(20, 'FK-M-1915-003', TRUE, TRUE);

INSERT INTO book_copies (book_id, inventory_code, available, active) VALUES
(21, 'RCM-CL-2008-001', TRUE, TRUE),
(22, 'RCM-CLP-2011-001', TRUE, TRUE),
(23, 'EE-DDD-2003-001', TRUE, TRUE),
(24, 'MF-R-1999-001', TRUE, TRUE),
(25, 'KB-TDD-2003-001', TRUE, TRUE),
(26, 'AO-LJDP-2012-001', TRUE, TRUE),
(27, 'SM-CC-1993-001', TRUE, TRUE);

INSERT INTO loans (user_id, book_copy_id, loan_date, due_date, return_date, loan_status, renewals) VALUES
-- Empréstimos concluídos em Janeiro

(1, 20, '2025-01-05', '2025-01-20', '2025-01-18', 'RETURNED', 1),
(1, 25, '2025-01-05', '2025-01-20', '2025-01-18', 'RETURNED', 2),
(2, 27, '2025-01-10', '2025-01-25', '2025-01-24', 'RETURNED', 1),
(2, 12, '2025-01-10', '2025-01-25', '2025-01-24', 'RETURNED', 0),
(3, 26, '2025-01-01', '2025-01-15', '2025-01-14', 'RETURNED', 0),
(4, 33, '2025-01-05', '2025-01-20', '2025-01-18', 'RETURNED', 1),
(5, 32, '2025-01-15', '2025-01-25', '2025-01-23', 'RETURNED', 2),
(6, 2, '2025-01-15', '2025-01-30', '2025-01-28', 'RETURNED', 0),
(6, 2, '2025-01-15', '2025-01-30', '2025-01-28', 'RETURNED', 2),

--Fevereiro
(1, 26, '2025-02-01', '2025-02-15', '2025-02-14', 'RETURNED', 1),
(6, 33, '2025-02-05', '2025-02-20', '2025-02-19', 'RETURNED', 0),
(7, 32, '2025-02-15', '2025-02-25', '2025-02-23', 'RETURNED', 2),
(9, 2, '2025-02-15', '2025-02-25',  '2025-03-10', 'RETURNED', 1),
(9, 2, '2025-02-15', '2025-02-25', '2025-03-10', 'RETURNED', 0),

-- Empréstimos concluídos em Março
(7, 26, '2025-03-01', '2025-03-15', '2025-03-14', 'RETURNED', 0),
(5, 29, '2025-03-05', '2025-03-20', '2025-03-19', 'RETURNED', 2),
(1, 20, '2025-03-05', '2025-03-20', '2025-03-18', 'RETURNED', 1),
(2, 23, '2025-03-10', '2025-03-25', '2025-03-24', 'RETURNED', 2),
(9, 26, '2025-03-01', '2025-03-15', '2025-03-14', 'RETURNED', 0),
(10, 32, '2025-03-10', '2025-03-25', '2025-03-23', 'RETURNED', 1),
(10, 35, '2025-03-15', '2025-03-30', '2025-03-28', 'RETURNED', 2),
(8, 13, '2025-03-20', '2025-04-01', '2025-03-30', 'RETURNED', 1),
(8, 16, '2025-03-25', '2025-04-04', '2025-04-01', 'RETURNED', 2),
(1, 13, '2025-03-20', '2025-04-04', '2025-04-01', 'RETURNED', 0),
(3, 16, '2025-03-25', '2025-04-04', '2025-04-01', 'RETURNED', 0);

INSERT INTO loans (user_id, book_copy_id, loan_date, due_date, return_date, loan_status, renewals) VALUES
-- Abril
(2, 16, '2025-04-25', '2025-05-08', '2025-05-07', 'RETURNED', 1),
(2, 13, '2025-04-20', '2025-05-03', '2025-05-01', 'RETURNED', 0),
(10, 23, '2025-04-25', '2025-05-08', '2025-05-01', 'RETURNED', 1),
(10, 32, '2025-04-25', '2025-05-08', '2025-05-01', 'RETURNED', 0),
(10, 12, '2025-04-25', '2025-05-08', '2025-05-02', 'RETURNED', 0),


-- Empréstimos concluídos em Maio
(5, 32, '2025-05-10', '2025-05-25', '2025-05-25', 'RETURNED', 1),
(6, 35, '2025-05-15', '2025-05-30', '2025-05-28', 'RETURNED', 0),
(7, 38, '2025-05-02', '2025-05-17', '2025-05-16', 'RETURNED', 2),
(8, 41, '2025-05-12', '2025-05-27', '2025-05-26', 'RETURNED', 0);

INSERT INTO loans (user_id, book_copy_id, loan_date, due_date, return_date, loan_status, renewals) VALUES
-- Empréstimos concluídos em Julho
(7, 34, '2025-07-02', '2025-07-20', '2025-07-19', 'RETURNED', 1),
(9, 33, '2025-07-12', '2025-07-24', '2025-07-23', 'RETURNED', 1),
(9, 31, '2025-07-02', '2025-07-23', '2025-07-20', 'RETURNED', 1),
(1, 25, '2025-07-12', '2025-07-23', '2025-08-19', 'RETURNED', 1),
(1, 7, '2025-07-12', '2025-07-24', '2025-08-17', 'RETURNED', 1),


-- Empréstimos concluídos em Setembro (seu exemplo original)
(3, 13, '2025-08-20', '2025-09-30', '2025-09-23', 'RETURNED', 1),
(3, 16, '2025-08-25', '2025-09-30', '2025-09-26', 'RETURNED', 0),
(9, 23, '2025-08-20', '2025-09-20', '2025-09-18', 'RETURNED', 2),
(6, 21, '2025-08-25', '2025-09-25', '2025-09-21', 'RETURNED', 2),
(6, 27, '2025-08-20', '2025-09-24', '2025-09-20', 'RETURNED', 1),
(10, 30, '2025-08-25', '2025-09-25', '2025-09-20', 'RETURNED', 0);





-- ---------------------------------------------------------------------------


INSERT INTO loans (user_id, book_copy_id, loan_date, due_date, return_date, loan_status, renewals) VALUES
-- Empréstimos Atrasados (sem devolução)
(1, 2, '2025-08-20', '2025-09-03', NULL, 'LATE', 0),
(2, 6, '2025-08-15', '2025-08-29', NULL, 'LATE', 0),
(3, 19, '2025-08-20', '2025-09-03', NULL, 'LATE', 0),
(4, 22, '2025-08-15', '2025-08-29', NULL, 'LATE', 0),
(5, 31, '2025-08-20', '2025-09-03', NULL, 'LATE', 0),
(6, 24, '2025-08-15', '2025-08-29', NULL, 'LATE', 0);

INSERT INTO loans (user_id, book_copy_id, loan_date, due_date, return_date, loan_status, renewals) VALUES
-- Empréstimos Concluídos (com atraso)
(7, 3, '2025-07-30', '2025-08-20', '2025-08-25', 'RETURNED', 0),
(8, 7, '2025-08-05', '2025-08-15', '2025-08-22', 'RETURNED', 0),
(9, 30, '2025-07-28', '2025-08-13', '2025-08-15', 'RETURNED', 0),
(10, 8, '2025-08-10', '2025-08-20', '2025-08-26', 'RETURNED', 0);


INSERT INTO fines (loan_id, amount, issued_date, paid) VALUES
(52, 16.50, '2025-08-25', FALSE),
(53, 24.00, '2025-08-22', FALSE),
(54, 11.00, '2025-08-15', FALSE),
(55, 11.00, '2025-08-26', TRUE);
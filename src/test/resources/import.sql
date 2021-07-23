-- H2 Database (Tests) --
-- INSERT INTO `tb_usuarios`(login, senha, data_criacao) VALUES('seller@email.com', '$2a$10$mfagFF6QSXe6p/XTJA32MuVTVWXSKw0oKRGJAL3epoM/4ZlXaz8Sq', '2021-07-23 15:45:46');

-- INSERT INTO `tb_categorias`(nome) VALUES('Games');

-- INSERT INTO `tb_produtos`(nome, preco, qta_disponivel, descricao, data_criacao, categoria, vendedor) VALUES('PS5', 499.99, 5, 'The PlayStation 5 (PS5) is a home video game console developed by Sony Interactive Entertainment', '2021-07-23 15:47:49', 1, 1);

-- INSERT INTO `tb_caracteristicas`(nome, descricao, produto) VALUES('Test1', 'test1', 1);
-- INSERT INTO `tb_caracteristicas`(nome, descricao, produto) VALUES('Test2', 'test2', 1);
-- INSERT INTO `tb_caracteristicas`(nome, descricao, produto) VALUES('Test2', 'test2', 1);

-- INSERT INTO `tb_imagens`(link, id_produto) VALUES('http://bucket.io/casper.jpg', 1);

-- INSERT INTO `tb_opinioes`(descricao, nota, titulo, id_produto, id_usuario) VALUES('Best Video Game ever!', 5, 'Amazing', 1, 1);

-- INSERT INTO `tb_perguntas`(titulo, data_criacao, id_produto, id_usuario) VALUES('Is it good?', '2021-07-23 15:49:55', 1, 1);
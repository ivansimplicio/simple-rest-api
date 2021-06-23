-- DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cpf` varchar(11) NOT NULL,
  `email` varchar(30) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `role` int DEFAULT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cpf` (`cpf`),
  UNIQUE KEY `UK_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `alunos`;
CREATE TABLE `alunos` (
  `data_de_nascimento` date DEFAULT NULL,
  `matricula` varchar(8) NOT NULL,
  `nome_da_mae` varchar(30) DEFAULT NULL,
  `nome_do_pai` varchar(30) DEFAULT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_matricula` (`matricula`),
  CONSTRAINT `FK_aluno_usuario_id` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `professores`;
CREATE TABLE `professores` (
  `formacao` varchar(30) NOT NULL,
  `salario` double DEFAULT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_professor_usuario_id` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `coordenadores`;
CREATE TABLE `coordenadores` (
  `data_de_cadastro` date DEFAULT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_coordenador_usuario_id` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `turmas`;
CREATE TABLE `turmas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `disciplina` varchar(25) NOT NULL,
  `professor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_disciplina` (`disciplina`),
  KEY `FK_professor_id` (`professor_id`),
  CONSTRAINT `FK_professor_id` FOREIGN KEY (`professor_id`) REFERENCES `professores` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- DROP TABLE IF EXISTS `alunos_turmas`;
CREATE TABLE `alunos_turmas` (
  `aluno_id` int NOT NULL,
  `turma_id` int NOT NULL,
  KEY `FK_turma_id` (`turma_id`),
  KEY `FK_aluno_id` (`aluno_id`),
  CONSTRAINT `FK_turma_id` FOREIGN KEY (`turma_id`) REFERENCES `turmas` (`id`),
  CONSTRAINT `FK_aluno_id` FOREIGN KEY (`aluno_id`) REFERENCES `alunos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `usuarios` VALUES (1, '81728794072', 'coordenador@email.com', 'Coordenador', 1, '$2a$10$rfYOACoZlpQkpOJGADkonuDqfKAsLuwhfeushtAbGCI.FOoOUsRqm');
INSERT INTO `coordenadores` VALUES (curdate(), 1);
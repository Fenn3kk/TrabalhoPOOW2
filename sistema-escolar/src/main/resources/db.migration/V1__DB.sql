-- Habilitando a extensão para UUIDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Criando a tabela Administrador
CREATE TABLE Administrador (
                               id_administrador SERIAL PRIMARY KEY,
                               uuid_administrador UUID DEFAULT UNIQUE,
                               nome_administrador VARCHAR(100),
                               senha_administrador VARCHAR(100)
);

-- Criando a tabela Aluno
CREATE TABLE Aluno (
                       id_aluno SERIAL PRIMARY KEY,
                       uuid_aluno UUID DEFAULT UNIQUE,
                       nome_aluno VARCHAR(100),
                       email_aluno VARCHAR(100)
);

-- Criando a tabela Turma
CREATE TABLE Turma (
                       id_turma SERIAL PRIMARY KEY,
                       uuid_turma UUID DEFAULT UNIQUE,
                       nome_turma VARCHAR(100)
);

-- Criando a tabela Disciplina
CREATE TABLE Disciplina (
                            id_disciplina SERIAL PRIMARY KEY,
                            uuid_disciplina UUID DEFAULT UNIQUE,
                            nome_disciplina VARCHAR(100)
);

-- Criando a tabela Professor
CREATE TABLE Professor (
                           id_professor SERIAL PRIMARY KEY,
                           uuid_professor UUID DEFAULT UNIQUE,
                           nome_professor VARCHAR(100),
                           email_professor VARCHAR(100)
);

CREATE TABLE Aluno_turma
(
                            id_turma  INT,
                            id_aluno INT,
                            PRIMARY KEY (id_turma, id_aluno),
                            FOREIGN KEY (id_turma) REFERENCES Turma (id_turma) ON DELETE CASCADE,
                            FOREIGN KEY (id_aluno) REFERENCES Aluno (id_aluno) ON DELETE CASCADE
);

-- Tabela intermediária para muitos-para-muitos entre Professor e Disciplina
CREATE TABLE Professor_disciplina (
                            id_professor INT,
                            id_disciplina INT,
                            PRIMARY KEY (id_professor, id_disciplina),
                            FOREIGN KEY (id_professor) REFERENCES Professor(id_professor) ON DELETE CASCADE,
                            FOREIGN KEY (id_disciplina) REFERENCES Disciplina(id_disciplina) ON DELETE CASCADE
);

-- Criando a tabela Horario (tabela de junção com atributos adicionais)
CREATE TABLE Horario (
                         id_horario SERIAL PRIMARY KEY,
                         uuid_horario UUID DEFAULT UNIQUE,
                         id_turma INT,
                         id_professor INT,
                         id_disciplina INT,
                         dia_horario VARCHAR(100),
                         hora_horario VARCHAR(100),
                         FOREIGN KEY (id_turma) REFERENCES Turma(id_turma) ON DELETE CASCADE,
                         FOREIGN KEY (id_professor) REFERENCES Professor(id_professor) ON DELETE CASCADE,
                         FOREIGN KEY (id_disciplina) REFERENCES Disciplina(id_disciplina) ON DELETE CASCADE,
                         CONSTRAINT unique_turma_professor_disciplina UNIQUE (id_turma, id_professor, id_disciplina)
);

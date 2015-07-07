CREATE TABLE alunos (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	dataNascimento DATE NOT NULL,
	genero VARCHAR(30) NOT NULL,
	cpf VARCHAR(15) NOT NULL
);

CREATE TABLE disciplinas (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	periodo VARCHAR(6) NOT NULL,
	nomeProfessor VARCHAR(255) NOT NULL,
	vagas INT NOT NULL
);

CREATE TABLE matriculas (
        id SERIAL PRIMARY KEY,
        idAluno INT NOT NULL REFERENCES alunos(id),
        idDisciplina INT NOT NULL REFERENCES disciplinas(id)
);
INSERT INTO especialidade (nome) VALUES ('Cardiologia');
INSERT INTO especialidade (nome) VALUES ('Dermatologia');
INSERT INTO especialidade (nome) VALUES ('Ortopedista');

INSERT INTO medico (nome, crm, endereco) VALUES ('Dr. João Silva', 'CRM213412345', 'Rua A, 100');
INSERT INTO medico (nome, crm, endereco) VALUES ('Dr. José Silva', '2CR12M12345', 'Rua A, 100');
INSERT INTO medico (nome, crm, endereco) VALUES ('Dr. Tiago Silva', 'CR5Mdsa12345', 'Rua A, 100');

INSERT INTO medico_especialidade (medico_id, especialidade_id) VALUES (1,1);
INSERT INTO medico_especialidade (medico_id, especialidade_id) VALUES (2,2);
INSERT INTO medico_especialidade (medico_id, especialidade_id) VALUES (3,3);

INSERT INTO convenio (nome, cobertura, telefone_contato) VALUES ('Unimed', 'Completa', '11999999999');
INSERT INTO convenio (nome, cobertura, telefone_contato) VALUES ('Bradesco', 'Nacional', '11988888888');

INSERT INTO paciente (nome, email, telefone, data_nascimento, convenio_id)
VALUES
('João Silva','joao@example.com','11999998888','1988-03-22',1),
('Ana Oliveira','ana@example.com','21977776666','1995-07-15',1),
('Carlos Pereira','carlos@example.com','31966665555','1980-11-30',1),
('Fernanda Lima','fernanda@example.com','21955554444','1992-01-18',1),
('Bruno Santos','bruno@example.com','41944443333','1998-06-05',1),
('Patrícia Mendes','patricia@example.com','31933332222','1985-09-25',1),
('Lucas Almeida','lucas@example.com','11922221111','1993-12-12',1),
('Juliana Costa','juliana@example.com','31911110000','1997-04-08',1),
('Rodrigo Martins','rodrigo@example.com','41900009999','1989-08-21',1);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Rua das Palmeiras', '123', 'Centro', 'São Paulo', 'SP', '01000-000', 1);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Av. Brasil', '456', 'Copacabana', 'Rio de Janeiro', 'RJ', '22000-000', 2);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Rua Minas Gerais', '789', 'Savassi', 'Belo Horizonte', 'MG', '30140-000', 3);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Rua do Sol', '50', 'Boa Viagem', 'Recife', 'PE', '51020-000', 4);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Av. das Torres', '1020', 'Portão', 'Curitiba', 'PR', '81070-000', 5);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Rua das Laranjeiras', '99', 'Centro', 'Vitória', 'ES', '29010-000', 6);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Rua Goiás', '222', 'Centro', 'Campinas', 'SP', '13010-000', 7);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Rua Bahia', '888', 'Cidade Nova', 'Belo Horizonte', 'MG', '31170-000', 8);

INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep, paciente_id)
VALUES ('Av. Paulista', '1000', 'Bela Vista', 'São Paulo', 'SP', '01310-000', 9);


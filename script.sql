/*
Created: 4/6/2021
Modified: 5/6/2021
Model: TowerDefense
Database: Oracle 18c
*/


-- Create sequences section -------------------------------------------------

CREATE SEQUENCE td_usuarios_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 CACHE 20
;

CREATE SEQUENCE td_partida_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 1
 CACHE 20
;

-- Create tables section -------------------------------------------------

-- Table td_usuarios

CREATE TABLE td_usuarios(
  usr_id Number NOT NULL,
  usr_nombre Varchar2(60 ) NOT NULL,
  usr_clave Varchar2(30 ) NOT NULL,
  usr_avatar Varchar2(300 ) NOT NULL
)
;

-- Add keys for table td_usuarios

ALTER TABLE td_usuarios ADD CONSTRAINT td_usuarios_pk PRIMARY KEY (usr_id)
;

-- Table and Columns comments section

COMMENT ON TABLE td_usuarios IS 'Tabla que registra los datos principales del jugardor

-Nombre del ususario
-Contraseña del usuario
-Avatar del jugaor
'
;
COMMENT ON COLUMN td_usuarios.usr_id IS 'Id del jugador'
;
COMMENT ON COLUMN td_usuarios.usr_nombre IS 'Nombre del jugador'
;
COMMENT ON COLUMN td_usuarios.usr_clave IS 'Se guarda la clave para entrar en el sistema
'
;
COMMENT ON COLUMN td_usuarios.usr_avatar IS 'Aqui se guarda la dirrecion de la foto para el avatar del jugador'
;

-- Table td_partida

CREATE TABLE td_partida(
  prt_id_usuario Number,
  prt_id Number NOT NULL,
  prt_nivel Number NOT NULL,
  prt_nivel_ballesta Number NOT NULL,
  prt_nivel_castillo Number NOT NULL,
  prt_nivel_elixir Number NOT NULL,
  prt_nivel_poder_meteoro Number NOT NULL,
  prt_nivel_poder_hielo Number NOT NULL,
  prt_tipo_ballesta Varchar2(2 ) NOT NULL,
  prt_monedas Number NOT NULL,
  prt_tipo_uso_ballesta Char(20 ),
  CONSTRAINT td_partida_ck01 CHECK (prt_tipo_ballesta in ('A','B')),
  CONSTRAINT td_partida_ck02 CHECK (prt_tipo_uso_ballesta in ('M','T'))
)
;

-- Create indexes for table td_partida

CREATE INDEX IX_Relationship1 ON td_partida (prt_id_usuario)
;

-- Add keys for table td_partida

ALTER TABLE td_partida ADD CONSTRAINT PK_td_partida PRIMARY KEY (prt_id)
;

-- Table and Columns comments section

COMMENT ON TABLE td_partida IS 'En esta tabla se guardan los datos necesarios para poder realizar la carga de una partida que esta ligada a algun jugador en especifico'
;
COMMENT ON COLUMN td_partida.prt_id IS 'id de la partida'
;
COMMENT ON COLUMN td_partida.prt_nivel IS 'numero que indica el nivel actual de la partida
'
;
COMMENT ON COLUMN td_partida.prt_nivel_ballesta IS 'el nivel de la ballesta indica el poder de la misma, tanto en daño como en velocidad de disparo
'
;
COMMENT ON COLUMN td_partida.prt_nivel_castillo IS 'cantidad de vida del castillo'
;
COMMENT ON COLUMN td_partida.prt_nivel_elixir IS 'cantidad maxima de almacenaje de elixir'
;
COMMENT ON COLUMN td_partida.prt_nivel_poder_meteoro IS 'cantidad de daño que puede inflingir el poder del meteoro
'
;
COMMENT ON COLUMN td_partida.prt_nivel_poder_hielo IS 'cantidad de tiempo que dura el poder del hielo'
;
COMMENT ON COLUMN td_partida.prt_tipo_ballesta IS 'guarda el tipo de ballesta seleccionado por el usuario'
;
COMMENT ON COLUMN td_partida.prt_monedas IS 'cantidad de monedas recolectadas durante las partidas'
;
COMMENT ON COLUMN td_partida.prt_tipo_uso_ballesta IS 'indica la modalidad para usar la ballesta(mouse o teclado)'
;

-- Trigger for sequence td_usuarios_seq01 for column usr_id in table td_usuarios ---------
CREATE OR REPLACE TRIGGER td_usuarios_tgr01 BEFORE INSERT
ON td_usuarios FOR EACH ROW
BEGIN
  IF :new.usr_id IS NULL OR :new.usr_id <1 THEN
  :new.usr_id := td_usuarios_seq01.nextval;
  END IF;
END;
/
CREATE OR REPLACE TRIGGER td_usuarios_tgr02 AFTER UPDATE OF usr_id
ON td_usuarios FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column usr_id in table td_usuarios as it uses sequence.');
END;
/

-- Trigger for sequence td_partida_seq01 for column prt_id in table td_partida ---------
CREATE OR REPLACE TRIGGER td_partida_tgrq01 BEFORE INSERT
ON td_partida FOR EACH ROW
BEGIN
  IF :new.prt_id IS NULL OR :new.prt_id < 1 THEN
  :new.prt_id := td_partida_seq01.nextval;
  END IF;
END;
/
CREATE OR REPLACE TRIGGER td_partida_tgrq02 AFTER UPDATE OF prt_id
ON td_partida FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column prt_id in table td_partida as it uses sequence.');
END;
/


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE td_partida ADD CONSTRAINT Relationship1 FOREIGN KEY (prt_id_usuario) REFERENCES td_usuarios (usr_id)
;

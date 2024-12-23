-- Criando o banco de dados
CREATE DATABASE "recargapay";

\c "recargapay";

CREATE TABLE public.wallets (
	id bigserial NOT NULL,
	wallet_code varchar(20) NOT NULL,
	client_id uuid NOT NULL,
	balance numeric(18, 2) DEFAULT 0.00 NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	description varchar(200) NOT NULL,
	status varchar(20) NOT NULL,
	CONSTRAINT pk_wallets PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_wallet_code ON public.wallets USING btree (wallet_code);

CREATE TABLE public.wallet_commands (
	id bigserial NOT NULL,
	wallet_id int8 NOT NULL,
	wallet_code varchar(20) NOT NULL,
	client_id uuid NOT NULL,
	balance numeric(18, 2) DEFAULT 0.00 NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	description varchar(200) NOT NULL,
	status varchar(20) NOT NULL,
	command varchar(50) NOT NULL,
	CONSTRAINT fk_wallets_commands PRIMARY KEY (id),
	CONSTRAINT fk_wallet_commands FOREIGN KEY (wallet_id) REFERENCES wallets(id)
);


CREATE TABLE public.wallet_operations (
	id bigserial NOT NULL,
	wallet_id int8 NOT NULL,
	transaction_id uuid NOT NULL,
	operation varchar(50) NOT NULL,
	amount numeric(18, 2) NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT pk_wallet_operations_y PRIMARY KEY (id),
	CONSTRAINT fk_wallet_commands FOREIGN KEY (wallet_id) REFERENCES wallets(id)
);

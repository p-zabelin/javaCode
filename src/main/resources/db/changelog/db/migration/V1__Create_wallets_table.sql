-- create-table.sql
CREATE TABLE IF NOT EXISTS wallet (
                         wallet_id UUID PRIMARY KEY,
                         balance NUMERIC(19, 4) NOT NULL DEFAULT 0
);

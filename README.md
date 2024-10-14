Для запуска проекта выполнить команды:
1. mvn clean package
2. docker-compose up --build

Чтобы создать в бд кошелек, необходимо отправить запрос 
Метод POST
url: localhost:8085/api/v1/wallet/create
body: 
{
"amount": 1111
}

Внести/снять средства 
Метод POST
url: localhost:8085/api/v1/wallet
body:
{
"wallet_id": "e4aefefb-3bdd-4f68-9757-b026d958e1c3",
"operation_type": "DEPOSIT",
"amount": 50000
}

Узнать баланс
Метод GET
url: localhost:8085/api/v1/wallet/e4aefefb-3bdd-4f68-9757-b026d958e1c3

Получить все кошельки с балансом
Метод GET
url: localhost:8085/api/v1/wallet

# Merchant Database

## users

| Column | Type | Description |
|---------|------|-------------|
| id | BIGSERIAL | Primary Key |
| first_name | VARCHAR(100) | User first name |
| last_name | VARCHAR(100) | User last name |
| email | VARCHAR(150) | Unique Email |
| password | VARCHAR(255) | Encrypted Password |
| phone | VARCHAR(20) | Mobile Number |
| created_at | TIMESTAMP | Created Time |
| updated_at | TIMESTAMP | Updated Time |



## products

| Column | Type |
|---------|------|
| id | BIGSERIAL |
| name | VARCHAR(150) |
| description | TEXT |
| price | DECIMAL(10,2) |
| stock | INT |
| category | VARCHAR(100) |
| created_at | TIMESTAMP |
| updated_at | TIMESTAMP |

## cart

| Column | Type |
|---------|------|
| id | BIGSERIAL |
| user_id | BIGINT |
| created_at | TIMESTAMP |

## cart_items

| Column | Type |
|---------|------|
| id | BIGSERIAL |
| cart_id | BIGINT |
| product_id | BIGINT |
| quantity | INT |


## orders

| Column | Type |
|---------|------|
| id | BIGSERIAL |
| user_id | BIGINT |
| total_amount | DECIMAL(10,2) |
| payment_reference | VARCHAR(100) |
| order_status | VARCHAR(50) |
| created_at | TIMESTAMP |


## order_items

| Column | Type |
|---------|------|
| id | BIGSERIAL |
| order_id | BIGINT |
| product_id | BIGINT |
| quantity | INT |
| price | DECIMAL(10,2) |



# Payment Database

## payments

| Column | Type |
|---------|------|
| id | BIGSERIAL |
| payment_reference | VARCHAR(100) |
| merchant_id | VARCHAR(100) |
| order_id | VARCHAR(100) |
| amount | DECIMAL(10,2) |
| currency | VARCHAR(10) |
| status | VARCHAR(50) |
| idempotency_key | VARCHAR(255) |
| correlation_id | VARCHAR(255) |
| processing_time_ms | BIGINT |
| created_at | TIMESTAMP |
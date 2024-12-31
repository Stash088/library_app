import sqlite3
import random
import string

# Функция для генерации случайного email
def generate_email():
    domains = ['gmail.com', 'yahoo.com', 'hotmail.com', 'example.com']
    username_length = random.randint(5, 10)
    username = ''.join(random.choices(string.ascii_lowercase + string.digits, k=username_length))
    domain = random.choice(domains)
    return f"{username}@{domain}"

# Функция для генерации случайного номера телефона
def generate_phone():
    return f"+7{''.join(random.choices(string.digits, k=10))}"

# Функция для генерации случайного кода читателя
def generate_reader_code():
    return ''.join(random.choices(string.ascii_uppercase + string.digits, k=8))

# Функция для генерации случайного имени
def generate_first_name():
    first_names = ['John', 'Jane', 'Alice', 'Bob', 'Charlie', 'Diana', 'Eva', 'Frank', 'Grace', 'Henry']
    return random.choice(first_names)

# Функция для генерации случайной фамилии
def generate_last_name():
    last_names = ['Smith', 'Johnson', 'Williams', 'Brown', 'Jones', 'Garcia', 'Miller', 'Davis', 'Rodriguez', 'Martinez']
    return random.choice(last_names)

# Функция для создания соединения с базой данных
def create_connection(db_file):
    conn = None
    try:
        conn = sqlite3.connect(db_file)
    except sqlite3.Error as e:
        print(e)
    return conn

# Функция для создания таблицы (если её нет)
def create_table(conn):
    sql = '''CREATE TABLE IF NOT EXISTS Users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT NOT NULL,
                phone TEXT,
                code_reader TEXT,
                first_name TEXT,
                last_name TEXT
            );'''
    try:
        c = conn.cursor()
        c.execute(sql)
    except sqlite3.Error as e:
        print(e)

# Функция для вставки данных
def insert_user(conn, user):
    sql = ''' INSERT INTO Users(email, phone, code_reader, first_name, last_name)
              VALUES(?,?,?,?,?) '''
    try:
        cur = conn.cursor()
        cur.execute(sql, user)
        return cur.lastrowid
    except sqlite3.Error as e:
        print(f"Error inserting user: {user}. Error: {e}")
        return None

# Основная функция для генерации данных
def generate_data(db_file, num_records):
    conn = create_connection(db_file)
    
    if conn is not None:
        # Не будет создавать новую таблицу, если она уже существует
        create_table(conn)
        
        for _ in range(num_records):
            user = (
                generate_email(),
                generate_phone(),
                generate_reader_code(),
                generate_first_name(),
                generate_last_name()
            )
            insert_user(conn, user)
        
        conn.commit()
        print(f"{num_records} records inserted successfully into the existing database.")
    else:
        print("Error! Cannot create the database connection.")
    
    if conn:
        conn.close()

if __name__ == '__main__':
    db_file = "library_app/src/main/resources/db"  # Укажите имя вашей существующей базы данных
    num_records = 150  # Количество записей для генерации
    generate_data(db_file, num_records)
import sqlite3
import random
from datetime import datetime, timedelta

# Подключение к базе данных
conn = sqlite3.connect('library_app/src/main/resources/db')
cursor = conn.cursor()

# Получение только доступных книг и читателей
cursor.execute("SELECT id FROM Books WHERE status IN ('available', 'reserved')")
available_book_ids = [row[0] for row in cursor.fetchall()]

cursor.execute("SELECT id FROM Users")
reader_ids = [row[0] for row in cursor.fetchall()]

# Функция для генерации случайной даты
def random_date(start_date, end_date):
    time_between = end_date - start_date
    days_between = time_between.days
    random_number_of_days = random.randrange(days_between)
    return start_date + timedelta(days=random_number_of_days)

# Статусы выдачи
loan_statuses = ['active', 'returned', 'overdue']

# Генерация данных
loans_data = []
books_status_updates = []
start_date = datetime(2022, 1, 1)
end_date = datetime(2024, 1, 1)

num_records = 1000

for _ in range(num_records):
    # Выбор случайной книги и читателя
    book_id = random.choice(available_book_ids)
    reader_id = random.choice(reader_ids)
    
    # Генерация дат
    issue_date = random_date(start_date, end_date)
    due_date = issue_date + timedelta(days=14)
    
    # Определение статуса выдачи и даты возврата
    loan_status = random.choice(loan_statuses)
    
    if loan_status == 'returned':
        return_date = random_date(issue_date, due_date)
        book_status = 'available'
    elif loan_status == 'overdue':
        return_date = None
        book_status = 'checked out'
    else:  # active
        return_date = None
        book_status = 'checked out'
    
    # Добавление данных о выдаче
    loans_data.append((
        book_id,
        issue_date.strftime('%Y-%m-%d'),
        due_date.strftime('%Y-%m-%d'),
        return_date.strftime('%Y-%m-%d') if return_date else None,
        loan_status,
        reader_id
    ))
    
    # Обновление статуса книги
    books_status_updates.append((
        book_status,
        book_id
    ))

# Начало транзакции
cursor.execute('BEGIN TRANSACTION')

try:
    # Вставка данных о выдаче
    cursor.executemany('''
        INSERT INTO loans (book_id, issue_date, due_date, return_date, status, reader_id)
        VALUES (?, ?, ?, ?, ?, ?)
    ''', loans_data)

    # Обновление статусов книг
    cursor.executemany('''
        UPDATE books 
        SET status = ?
        WHERE id = ?
    ''', books_status_updates)

    # Подтверждение транзакции
    conn.commit()
    print(f"Успешно добавлено {num_records} записей в таблицу loans")
    print("Статусы книг обновлены")

except sqlite3.Error as e:
    # Откат транзакции в случае ошибки
    conn.rollback()
    print(f"Произошла ошибка: {e}")

finally:
    # Закрытие соединения
    conn.close()
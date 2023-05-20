**Задача №2. Разная комиссия**

Напишите алгоритм расчёта в виде функции, передавая в функцию:

тип карты/счёта (по умолчанию VK Pay);
сумму предыдущих переводов в этом месяце (по умолчанию 0 рублей);
сумму совершаемого перевода.


За переводы с карт Mastercard и Maestro - не взимается при сумме перевода до 75000 рублей в календарный месяц в рамках акции(*),
в иных случаях - 0,6% + 20 рублей.
• За переводы с карт Visa и Мир - 0,75%, минимум 35 рублей.
• За переводы на счёт VK Рау - не взимается.
Лимиты
• Максимальная сумма переводов по одной карте - 150000 рублей в сутки и 600000 рублей в месяц раздельно на отправку и на получение.
Комиссия в лимитах не учитывается.
• Максимальная сумма переводов со счёта VK Pay - 15000 рублей за один раз и 40000 рублей в месяц.
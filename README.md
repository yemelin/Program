Игра “Стройка”
=======

Программа реализует паттерн проектирования Стратегия.
Язык java.

Краткое описание игры:
На экране пишется “Поздравляем, вы выиграли тендер на копание ямы размер ямы 10 куб.м. стоимость работ 10тыс руб
Далее 2 кнопки:
- Копать экскаватором (1куб.м за 1 раз затраты 900руб)
- Копать лопатой (0,1куб.м. за 1 раз затраты 20руб)
По нажатии на каждую кнопку уменьшается кол-во оставшихся куб.м. и кол-во денег. Когда яма будет выкопана, выдается сообщение: “Поздравляем, заказ выполнен, ваша прибыль ...”

Логика работы программы:
1. старт
2. инициализация значений глобальных переменных (размер ямы, стоимость тендера)
3. отрисовка графического интерфейса (Сообщение о размере и стоимости + 2 кнопки + остаток денег и работы)
4. при нажатии каждой из кнопок запускается метод, реализованный по принципу "Стратегия", модифицирующий глобальные переменные и возвращающий результат (скорее всего в виде строки для вывода на экран)
5. результат выводится на графический интерфейс
6. ожидаем нажатие следующей кнопки
Каким образом завершать игру, решим по ходу (можно 2 кнопки заменять на одну "Сыграть еще раз", а можно оставить 2, просто при первом нажатии на любую из кнопок рестартовать игру)

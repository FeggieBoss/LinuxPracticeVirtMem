### Проект «Алгоритмы управления виртуальной памятью»

#### Входные данные для программы представляют собой 4 строки:
1) количество запросов - Q
2) размер адресного пространства процесса (в страницах) - N
3) размер оперативной памяти (в кадрах) - M
4) запросы через пробел
p.s. каждый запрос представляет собой число(от 1 до N), обозначающее номер страницы
к которой мы хотим обратиться

#### Результатом работы программы считаются 3*(Q+2) строки:
первые Q+2 являются результатом работы алгоритма замещения FIFO
вторые - LRU, третьи - OPT

#### Запуск программы:
Чтобы запустить программу, нужно для начала собрать её в Jar файл при помощи "shadow\shadowJar" в меню Gradle
(по умолчанию он будет назван "runnable-1.0-SNAPSHOT" и будет лежать
в папке "build\libs"). Потом же при помощи команды "java -jar путь_до_Jar_файла путь_до_файла_с_входными_данными путь_до_файла_куда_нужно_вывести_выходные_данные".
Прошу заметить, что не обязательно указыть файл для выходных данных! Программа сама определит наличие файла для вывода и 
в случае, если его не окажется, выведет данные в консоль. 

Можно ознакомиться с программой при помощи уже готовых входных данных "test\Examples\Example1.txt" (ответ на такие
запросы для сверки лежит в файле "test\Examples\Example1Answer.txt").

Пример команды для запуска:
java -jar D:\prog-2020-virt-mem-FeggieBoss\build\libs\runnable-1.0-SNAPSHOT.jar D:\prog-2020-virt-mem-FeggieBoss\src\test\Examples\Example1.txt

#### Тестирование
Программа протестирована на одной тысячи тестов с Q,N,M до 100. Тесты с ответами были сгенерированы 
на С++.

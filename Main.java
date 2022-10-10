package Java_Calculator;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String... args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Калькулятор принимает выражение как римские так и арабские. Вводить числа только в одной системе исчисления.");
        System.out.println("Вводить выражения через пробел пример; 5 * 5, V + IX");
        System.out.println("Введите выражение: ");
        String calcAll1 = input.nextLine();

        calc(calcAll1); // Вызываем метод calc для реализации нашей программы

    }

    // Это условие в задании пока не понятно что с ним делать
    public static String calc(String input) {
        try {
            String[] operations = {"+", "-", "/", "*"};

            String calcAll;
            calcAll = input.toUpperCase(Locale.ROOT); // Переводим из нижнего в верхний регистр

            int operationIndex = -1;

            // Проверяем есть ли плюс, минус
            for (int i = 0; i < operations.length; i++) {
                if (calcAll.contains(operations[i])) {
                    operationIndex = i;
                }
            }
            try {
                checkOneOp(calcAll); // Проверяем одно ли действие


                // Если нет операции, то выводим эту строку
                if (operationIndex == -1) {
                    System.out.println("Строка не является математической операцией ");
                    return calcAll;
                }

                // разделяем ввод значений на массив с индексами разделителем служит пробел
                String[] strSplit = calcAll.split(" ");
                // Определяем находятся ли они в одной системе исчисления
                if (check(strSplit[2]) == check(strSplit[0])) {
                    int number1, number2;
                    // Проверяем римские ли числа
                    boolean isRoman = check(strSplit[0]);
                    if (isRoman) {
                        // Если true то конвертируем их в числа
                        number1 = romanToInt(strSplit[0]);
                        number2 = romanToInt(strSplit[2]);
                    } else {
                        // Арабские то переводим из строки в число
                        number1 = Integer.parseInt(strSplit[0]);
                        number2 = Integer.parseInt(strSplit[2]);
                    }
                    if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
                        String s = "Числа больше 10. Введите числа между 1-10 ";
                        System.out.println(s);
                        return s;
                    }
                    // Присваиваем операции переменную и выполняем действие
                    var operation = strSplit[1];
                    int result;
                    switch (operation) {
                        case "+" -> result = (number1 + number2);
                        case "-" -> result = (number1 - number2);
                        case "/" -> result = (number1 / number2);
                        case "*" -> result = (number1 * number2);
                        default -> throw new IllegalStateException("Unexpected value: " + operation);
                    }
                    // Если числа были римские возвращаем римские
                    if (isRoman) {
                        System.out.println(IntegerToRomanNumeral(result));
                    } else {
                        System.out.println(result);
                    }

                } else {
                    System.out.println("Используются одновременно разные системы счисления");
                }
            }catch (Exception e) {
                System.out.println(" формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            }
        }catch (Exception e){
                System.out.println("Ведите корректное выражение ");
        }
        return input;
    }


    // Конвертируем из арабских в римские
    static String IntegerToRomanNumeral(int input) {
        if (input < 1 || input > 101)
            return "В римской системе нет отрицательных чисел";
        String s = "";
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }

    // Перевод из Римских в арабские
    public static int getArabian(char roman) {       // Сравниваем наше число и возвращаем число.
        if ('I' == roman) return 1;            // Можно этот метод осуществить через Switch Case
        else if ('V' == roman) return 5;
        else if ('X' == roman) return 10;
        else if ('L' == roman) return 50;
        else if ('C' == roman) return 100;

        return 0;

    }

    public static int romanToInt(String string) {

        int end = string.length() - 1;          // Находим последний индекс римского числа
        char[] arr = string.toCharArray();    // Переводим наше римское число в массив
        int arabian;                            // Создаем переменную которая будет хранить число до последнего числа
        int result = getArabian(arr[end]);      // Если в наше число одно как V, то находим результат в методе getArabian
        for (int i = end - 1; i >= 0; i--) {     // Проверяем из одной ли буквы состоят наше число если нет то входим в цикл
            arabian = getArabian(arr[i]);       // Находим предпоследнее число

            if (arabian < getArabian(arr[i + 1])) { // если предпоследнее число меньше последнего минусуем как IV = 4
                result -= arabian;
            } else {                                // если больше или равно прибавляем как VI = 6
                result += arabian;                  // Возвращаемся к началу цикла и повторяем цикл если у нас VIII = 8
            }
        }
        return result;                             // Возвращаем результат
    }

    public static boolean check(String s) {      // Проверяем римские ли числа или арабские

        int end = s.length() - 1;
        char[] arr = s.toCharArray();
        int result = getArabian(arr[end]);
        if (result == 0) {
            return false;
        } else {
            return true;
        }

    }



    public static void checkOneOp(String calcAll) throws Exception { // Пока что может проверить только 1 + 2 + 3
        char[] operations1 = {'+', '-', '/', '*'};        // если 1 + 2 - 3 то  не найдет и основной код выведет результат 3
        char[] checkOneOperation = calcAll.toCharArray(); // Исправил, но код получился громоздким
        int moreOne = 0;
        for (int j = 0; j < checkOneOperation.length; j++) { // Проверяем один ли оператор в строке
            if (operations1[0] == (checkOneOperation[j]) || operations1[1] == (checkOneOperation[j]) || operations1[2] == (checkOneOperation[j]) || operations1[3] == (checkOneOperation[j]))
                moreOne += 1;
        }

        if (1 < moreOne) {        // Проверяем один ли оператор в строке
            // System.out.println(" формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            throw new ArithmeticException(" формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        }


}
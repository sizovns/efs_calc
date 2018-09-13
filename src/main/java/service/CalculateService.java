package service;

import java.util.*;

public class CalculateService {

    // Создаём ассоциативный массив операций со значениями - приоритетами операций
    private static final Map<String, Integer> ConstMOper;

    static {
        ConstMOper = new HashMap<>();
        ConstMOper.put("*", 1);
        ConstMOper.put("/", 1);
        ConstMOper.put("-", 2);
        ConstMOper.put("+", 2);
    }

    // преобразование выражения в обратную польскую запись
    private static String infixToRPN(String expression, Map<String, Integer> operations) {
        // задаём скобки
        String leftBr = "(";
        String rightBr = ")";
        // список с результатом преобразования
        List<String> listRPN = new ArrayList<String>();
        // стэк для хранения операций
        Stack<String> stackOp = new Stack<String>();
        // коллекция со всеми возможными операциями
        Set<String> opers = new HashSet<String>(operations.keySet());
        // в том числе и скобками
        opers.add(leftBr);
        opers.add(rightBr);

        // удаляем пробелы из выражения
        expression = expression.replace(" ", "");

        // индекс символа выражения, на котором находится преобразование
        int index = 0;
        // флаг окончания преобразования
        boolean ok = true;
        while (ok) {
            // индекс следующей операции
            int nextOpI = expression.length(); // количество символов в выражении
            // следующая операция
            String nextOp = "";
            // Поиск следующей операции путём перебора всего списка операция и сравнения
            for (String op : opers) {
                // i - последняя операциия в выражении, начиная с точки отсчёта index
                int i = expression.indexOf(op, index);
                // если такой оператор есть в выражении и его индекс меньше последнего найденного
                if (i >= 0 && i < nextOpI) {
                    // то запоминаем этот оператор, как следующий используемый
                    nextOp = op;
                    nextOpI = i;
                }
            }
            // Если операций больше нет в выражении, выставляем флаг окончания преобразования
            if (nextOpI == expression.length()) {
                ok = false;
            } else {
                // Если операции предшествует операнд, добавляем его в результирующий список в виде подстроки от точки отсчёта до индекса операции
                if (index != nextOpI) {
                    listRPN.add(expression.substring(index, nextOpI));
                }
                // Если операци - открывающаяся скобка - помещаем её в стек операций
                if (nextOp.equals(leftBr)) {
                    stackOp.push(nextOp);
                } else if (nextOp.equals(rightBr)) {
                    // Если операци - закрывающаяся скобка
                    // перебираем стек, пока верхний элемент не будет открывающейся скобкой
                    while (!stackOp.peek().equals(leftBr)) {
                        // и добавляем операции из стека в результирующий список
                        listRPN.add(stackOp.pop());
                        // если стек опустел, значит кто-то накосячил в исходном выражении со скобками
                        if (stackOp.empty()) {
                            System.out.println("Empty Stack!");
                        }
                    }
                    // удаляем открвающуюся скобку
                    stackOp.pop();
                } else {
                    // Если операция - не скобка, перебираем стек операций, и подходящие помещаем в результирующий список
                    while (!stackOp.empty() && !stackOp.peek().equals(leftBr) && (operations.get(nextOp) >= operations.get(stackOp.peek()))) {
                        listRPN.add(stackOp.pop());
                    }
                    // помещаем операцию в стек
                    stackOp.push(nextOp);
                }
                // смещаем индекс начала отсчёта
                index = nextOpI + nextOp.length();
            }
        }

        // Добавление в результирующий список "остатков" выражения
        if (index != expression.length()) {
            listRPN.add(expression.substring(index));
        }
        // Опустошаем стек операций, перенося их в результирующий список
        while (!stackOp.empty()) {
            listRPN.add(stackOp.pop());
        }
        // результирующая строка
        StringBuilder rpn = new StringBuilder();
        // если результирующий список не пуст,
        if (!listRPN.isEmpty()) {
            // переносим в результирующую строку первый элемент
            rpn.append(listRPN.remove(0));
        }
        // И все остальные элементы через пробел
        while (!listRPN.isEmpty()) {
            rpn.append(" ").append(listRPN.remove(0));
        }

        return rpn.toString();
    }

    // вычисление выражения
    public static Double calc(String expression) {
        // получаем выражение в обратной польской нотации
        String rpn = infixToRPN(expression, ConstMOper);
        // разбиваем выражение по пробелам
        String[] elems = rpn.split(" ");
        // стек, в котором будет высчитываться результат
        Stack<Double> result = new Stack<Double>();

        // перебираем полученные элементы
        for (String e : elems) {
            // если элемент - операнд, помещаем его в стек
            if (!ConstMOper.keySet().contains(e)) {
                switch (e) {
                    case "pi":
                    case "Pi":
                    case "PI":
                        result.push(Math.PI);
                        break;
                    case "e":
                    case "E":
                        result.push(Math.E);
                        break;
                    default:
                        result.push(new Double(e));
                        break;
                }
            } else {
                // если элемент - операция, получаем операнды из результирующего стека для вычисления значения
                Double op2 = result.pop();
                Double op1 = result.empty() ? 0.0 : result.pop();
                // производим вычисления
                if ("*".equals(e)) {
                    result.push(op1 * op2);

                } else if ("/".equals(e)) {
                    if (op2 != 0) {
                        result.push(op1 / op2);
                    } else {
                        throw new ArithmeticException();
                    }

                } else if ("+".equals(e)) {
                    result.push(op1 + op2);

                } else if ("-".equals(e)) {
                    result.push(op1 - op2);

                } else {
                    System.out.println("Error!");
                }
            }
        }
        return (double) Math.round(result.pop() * 100000) / 100000;
    }

}

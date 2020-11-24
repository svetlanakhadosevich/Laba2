package ea.easy;

import java.io.*;
import java.util.*;

/**
 * @author SH
 */
public class Calculator {
    /**
     *
     * @param args - Стндартный аргумент main
     * @throws Exception - исключение
     */
    public static void main(String[] args) throws Exception {
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
        String sIn;

        try {
            System.out.println("Введте выражение для расчета. Поддерживаются цифры, операции +,-,*,/,^,% и приоритеты в виде скобок ( и ):");
            sIn = d.readLine();
            sIn = opn(sIn);
            double result = calculate(sIn);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param s - Выражение
     * @return - результат
     */
    public double result(String s){
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        String sIn;
        double result = 0;
        try {
            System.out.println("Введте выражение для расчета. Поддерживаются цифры, операции +,-,*,/,^,% и приоритеты в виде скобок ( и ):");
            sIn = c.readLine();
            sIn = opn(sIn);
             result = calculate(sIn);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     *
     * @param sIn String
     * @throws Exception - исключение
     */
    private static String opn(String sIn) throws Exception {
        StringBuilder sbStack = new StringBuilder(""), sbOut = new StringBuilder("");
        char cIn, cTmp;

        for (int i = 0; i < sIn.length(); i++) {
            cIn = sIn.charAt(i);
            if (isOp(cIn)) {
                while (sbStack.length() > 0) {
                    cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
                    if (isOp(cTmp) && (opPrior(cIn) <= opPrior(cTmp))) {
                        sbOut.append(" ").append(cTmp).append(" ");
                        sbStack.setLength(sbStack.length() - 1);
                    } else {
                        sbOut.append(" ");
                        break;
                    }
                }
                sbOut.append(" ");
                sbStack.append(cIn);
            } else if ('(' == cIn) {
                sbStack.append(cIn);
            } else if (')' == cIn) {
                cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
                while ('(' != cTmp) {
                    if (sbStack.length() < 1) {
                        throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                    }
                    sbOut.append(" ").append(cTmp);
                    sbStack.setLength(sbStack.length() - 1);
                    cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
                }
                sbStack.setLength(sbStack.length() - 1);
            } else {
                sbOut.append(cIn);
            }
        }


        while (sbStack.length() > 0) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length() - 1));
            sbStack.setLength(sbStack.length() - 1);
        }

        return sbOut.toString();
    }

    /**
     *
     * @param c - Char
     * @return - Boolean
     */
    private static boolean isOp(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
                return true;
        }
        return false;
    }

    /**
     *
     * @param op Char
     * @return Коэфецент операции
     */
    private static byte opPrior(char op) {
        switch (op) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return 1;
    }

    /**
     *
     * @param sIn String
     * @return result
     * @throws Exception - исключение
     */
    private static double calculate(String sIn) throws Exception {
        double dA = 0, dB = 0;
        String sTmp;
        Deque<Double> stack = new ArrayDeque<>();
        StringTokenizer st = new StringTokenizer(sIn);
        while (st.hasMoreTokens()) {
            try {
                sTmp = st.nextToken().trim();
                if (1 == sTmp.length() && isOp(sTmp.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("Неверное количество данных в стеке для операции " + sTmp);
                    }
                    dB = stack.pop();
                    dA = stack.pop();
                    switch (sTmp.charAt(0)) {
                        case '+':
                            dA += dB;
                            break;
                        case '-':
                            dA -= dB;
                            break;
                        case '/':
                            dA /= dB;
                            break;
                        case '*':
                            dA *= dB;
                            break;
                        case '%':
                            dA %= dB;
                            break;
                        case '^':
                            dA = Math.pow(dA, dB);
                            break;
                        default:
                            throw new Exception("Недопустимая операция " + sTmp);
                    }
                    stack.push(dA);
                } else {
                    dA = Double.parseDouble(sTmp);
                    stack.push(dA);
                }
            } catch (Exception e) {
                throw new Exception("Недопустимый символ в выражении");
            }
        }

        if (stack.size() > 1) {
            throw new Exception("Количество операторов не соответствует количеству операндов");
        }

        return stack.pop();
    }
}
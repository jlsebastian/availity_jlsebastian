package com.availity.hw;

import java.util.Stack;

public class LispCodeChecker {

    /**
     * validates the parentheses of a LISP code. It takes in a string as an input and
     * returns true if all the parentheses in the string are properly closed and nested.
     *
     * @param strCode code to be validated
     * @return True if string are properly closed and nested otherwise false.
     */
    public boolean isParenthesesMatching(String strCode) {
        if (null == strCode || strCode.isEmpty()) {
            return true;
        }
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < strCode.length(); i++) {
            char currChar = strCode.charAt(i);
            if ((currChar != '(' && currChar != ')')) {
                continue;
            }

            switch (currChar) {
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(')
                        return false;
                    break;
                default:
                    stack.push(currChar);
                    break;
            }
        }
        return stack.isEmpty();
    }

}

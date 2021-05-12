package com.availity.hw;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class LispCodeCheckerTest {

    @org.junit.Test
    public void isParenthesesMatching() {
        LispCodeChecker lispCodeChecker = new LispCodeChecker();
        assertTrue("Valid empty ", lispCodeChecker.isParenthesesMatching(""));
        assertTrue("Valid null ", lispCodeChecker.isParenthesesMatching(null));
        assertTrue("Valid simple ()", lispCodeChecker.isParenthesesMatching("()"));
        assertTrue("Valid medium a(b)c()d()e()(f)g", lispCodeChecker.isParenthesesMatching("a(b)c()d()e()(f)g"));
        assertTrue("Valid medium2 (a((b)c123())d34()e()(f)g)", lispCodeChecker.isParenthesesMatching("(a((b)c123())d34()e()(f)g)"));
        assertTrue("Valid complex ((((((a((b)c123())d34()e()(f)g))))))", lispCodeChecker.isParenthesesMatching("((((((a((b)c123())d34()e()(f)g))))))"));
        assertTrue("Valid complex2 ab()ab()(ab)(ab)ab(()()()())((((((a((b)c123())d34()e()(f)g))))))", lispCodeChecker.isParenthesesMatching("ab()ab()(ab)(ab)ab(()()()())((((((a((b)c123())d34()e()(f)g))))))"));
        assertTrue("Valid complex3 ()()()()()()()()()ab()ab()(ab)(ab)ab(()()()())((((((a((b)c123())d34()e()(f)g))))))()()()()()()()()()()()()()()()()()()()", lispCodeChecker.isParenthesesMatching("()()()()()()()()()ab()ab()(ab)(ab)ab(()()()())((((((a((b)c123())d34()e()(f)g))))))()()()()()()()()()()()()()()()()()()()"));
        assertFalse("Invalid simple (", lispCodeChecker.isParenthesesMatching("("));
        assertFalse("Invalid simple2 )(", lispCodeChecker.isParenthesesMatching(")("));
        assertFalse("Invalid medium ))((", lispCodeChecker.isParenthesesMatching("))(("));
        assertFalse("Invalid medium2 (a((b)c123())d34()e()(f)g)(", lispCodeChecker.isParenthesesMatching("(a((b)c123())d34()e()(f)g)("));
        assertFalse("Invalid complex ()()()()()()()()()ab()ab()(ab)(ab)ab(()()()())((((((a((b)c1(23())d34()e()(f)g))))))()()()()()()()()()()()()()()()()()()()", lispCodeChecker.isParenthesesMatching("()()()()()()()()()ab()ab()(ab)(ab)ab(()()()())((((((a((b)c12(3())d34()e()(f)g))))))()()()()()()()()()()()()()()()()()()()"));

    }
}

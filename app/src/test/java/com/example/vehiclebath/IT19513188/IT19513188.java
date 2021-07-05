package com.example.vehiclebath.IT19513188;

import com.example.vehiclebath.addSubscripUser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*******IT19513188*******/

/*******Unit Test Case for Calculating Subscription Total Amount*******/

public class IT19513188 {
    private static addSubscripUser addSubscripUserOb;
    public static double result;

    @BeforeClass
    public static void addSubscripUser() {
        addSubscripUserOb = new addSubscripUser();
    }

    @Before
    public void setup() {
        result = addSubscripUserOb.calculateSubAmount(2000,2);
    }

    @Test
    public void calTotSubAmount() {
        assertEquals(4000, result, 0.01);
    }

    @After
    public void clearResultValue() {
        result = 0;
    }

    @AfterClass
    public static void clearObject() {
        addSubscripUserOb = null;
    }
}

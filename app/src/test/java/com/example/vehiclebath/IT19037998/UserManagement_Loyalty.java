package com.example.vehiclebath.IT19037998;

import com.example.vehiclebath.ViewUserLoyalty;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserManagement_Loyalty {

    /******************************IT19037998***********************/

    private static ViewUserLoyalty viewUserLoyalty;
    public static float result;



//    @BeforeClass
//    public static void createUserLoyalty() {
//        viewUserLoyalty = new ViewUserLoyalty();
//    }
//
//    @Before
//    public void setup(){
//        result =  viewUserLoyalty.calLoyaltyPoints("300");
//    }
//
//    @Test
//    public void calLoyalty() {
//        assertEquals(30.0, result, 0.001);
//    }
//
//    @After
//    public void clearResultValue() {
//        result = 0;
//    }
//    @AfterClass
//    public static void clearObject() {
//        viewUserLoyalty = null;
//    }

    @BeforeClass
    public static void createUserLoyalty() {
        viewUserLoyalty = new ViewUserLoyalty();
    }

    @Before
    public void setup(){
        result =  viewUserLoyalty.calLoyaltyPoints(300);
    }

    @Test
    public void calLoyalty() {
        assertEquals(30, result, 0.001);
    }

    @After
    public void clearResultValue() {
        result = 0;
    }
    @AfterClass
    public static void clearObject() {
        viewUserLoyalty = null;
    }


}

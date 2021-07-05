package com.example.vehiclebath.IT19021430;

import com.example.vehiclebath.AdminRating;
import com.example.vehiclebath.Model.DecimalUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.example.vehiclebath.Model.DecimalUtils.round;
import static org.junit.Assert.*;

public class IT19021430 {

    private static double result1,result2;



    private static DecimalUtils decimalUtils;

    /********************IT19021430************************************/
    /****test cases for calculating the average rating which is to be displayed
     * for the orgnization *****/
    private static AdminRating adminRating1;

    @BeforeClass
    public static void createAdminRatingObject(){
        adminRating1 = new AdminRating(); }

    @Before
    public void assignValuesRatingAvg(){
        result1 = adminRating1.calculateAvereage(20,5);
    }

    @Test
    public void testRatingAvg(){
        assertEquals(4,result1,0.1); }

    @After
    public void cleartestRatingAvg(){
        result1 = 0; }

    @AfterClass
    public static void  clearAdminRatingObject(){
        adminRating1 = null; }


    /*************************IT19021430**************************/
    /*****Test case to calculate the rounded value to of decimal place*********/
    @BeforeClass
    public static void createtestDecimalValueObject()
    {
        decimalUtils = new DecimalUtils();
    }

    @Before
    public void assignValuesDecimaltest()
    { result2 = decimalUtils.round(22.123,1);
    }

    @Test
    public void testDeciamlUtil(){
        assertEquals(22.1,result2,0.1);
    }

    @After
    public void clearDecimalUtilValue(){
        result2 = 0;
    }

    @AfterClass
    public static void  clearDEcimalUtilObject(){
        decimalUtils = null;
    }


}

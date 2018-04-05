package com.example.chris.androidcodingchallenge;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class RemoteDataSourceUnitTest {
    private List<User> items;
    @Before public void initialize() {
        RemoteDataSourceImpl rds = new RemoteDataSourceImpl();
        try{
            items = rds.createUserList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void size() {
        assertEquals(30, items.size());
    }
}
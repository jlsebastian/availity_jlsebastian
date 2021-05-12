package com.availity.hw;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EnrollmentCsvParserTest {
    @Test
    public void removeUserIdDuped() {
        //test empty
        List<Enrollee> enrollees = null;
        assertNotNull(EnrollmentCsvParser.removeUserIdDuped(enrollees));
        assertTrue(EnrollmentCsvParser.removeUserIdDuped(enrollees).isEmpty());

        //test 1 enrollee
        enrollees = new ArrayList<>();
        enrollees.add(new Enrollee("id1", "f_name", "l_name", 1, "insurer"));
        enrollees = new ArrayList<Enrollee>(EnrollmentCsvParser.removeUserIdDuped(enrollees));
        assertEquals(enrollees.get(0).getVersion(), 1);
        assertEquals(enrollees.size(), 1);

        //test 2 unique enrollee
        enrollees.add(new Enrollee("id2", "af_name", "bl_name", 1, "insurer"));
        assertEquals(enrollees.size(), 2);

        //test duplicate userID; 2 unique enrollee and 1 duplicate userId; versionId should be 2
        enrollees.add(new Enrollee("id2", "af_name", "bl_name", 2, "insurer"));
        enrollees = new ArrayList<Enrollee>(EnrollmentCsvParser.removeUserIdDuped(enrollees));
        assertEquals(enrollees.size(), 2);
        for (Enrollee enrollee : enrollees) {
            if ("id2".equals(enrollee.getUserId())) {
                assertEquals(enrollee.getVersion(), 2);
                break;
            }
        }

        //test sorting; same insurer with different last name
        enrollees = new ArrayList<>();
        enrollees.add(new Enrollee("id1", "af_name", "zl_name", 2, "insurer"));
        enrollees.add(new Enrollee("id2", "af_name", "bl_name", 2, "insurer"));
        enrollees = new ArrayList<Enrollee>(EnrollmentCsvParser.removeUserIdDuped(enrollees));
        EnrollmentCsvParser.sortEnrollees(enrollees);
        assertEquals(enrollees.get(0).getLastName(), "bl_name");
        assertEquals(enrollees.get(1).getLastName(), "zl_name");
        assertEquals(enrollees.size(), 2);

        //test sorting; same insurer with same last name, different firstname
        enrollees = new ArrayList<>();
        enrollees.add(new Enrollee("id1", "bf_name", "bl_name", 2, "insurer"));
        enrollees.add(new Enrollee("id3", "cf_name", "bl_name", 2, "insurer"));
        enrollees.add(new Enrollee("id2", "af_name", "bl_name", 2, "insurer"));
        enrollees = new ArrayList<Enrollee>(EnrollmentCsvParser.removeUserIdDuped(enrollees));
        EnrollmentCsvParser.sortEnrollees(enrollees);
        assertEquals(enrollees.size(), 3);
        assertEquals(enrollees.get(0).getFirstName(), "af_name");
        assertEquals(enrollees.get(1).getFirstName(), "bf_name");
        assertEquals(enrollees.get(2).getFirstName(), "cf_name");

        //test different insurer with same userId
        enrollees = new ArrayList<>();
        enrollees.add(new Enrollee("id1", "bf_name", "bl_name", 2, "insurer"));
        enrollees.add(new Enrollee("id3", "cf_name", "bl_name", 2, "insurer"));
        enrollees.add(new Enrollee("id1", "af_name", "bl_name", 2, "XXinsurer"));
        enrollees = new ArrayList<Enrollee>(EnrollmentCsvParser.removeUserIdDuped(enrollees));
        EnrollmentCsvParser.sortEnrollees(enrollees);
        assertEquals(enrollees.size(), 3);
        assertEquals(enrollees.get(0).getFirstName(), "af_name");
        assertEquals(enrollees.get(1).getFirstName(), "bf_name");
        assertEquals(enrollees.get(2).getFirstName(), "cf_name");

    }

}
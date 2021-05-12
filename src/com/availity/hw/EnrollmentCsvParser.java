package com.availity.hw;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This program accepts a csv input file containing enrollees.
 * The system will read the content of the file and separate enrollees by
 * insurance company in its own file. Additionally, sort the contents of each file by last and
 * first name (ascending).  Lastly, if there are duplicate User Ids for the same Insurance
 * Company, then only the record with the highest version should be included.
 * <p>
 * To run:
 * 1. Modify the PROJ_DIR variable to your desired directory
 * 2. PROJ_DIR shoudl contain 2 additional directories, input and output
 * 3. The input directory should contain the input csv file
 * 4. The output directory is where the progrm will write the output files.
 * <p>
 * The following articles/resources were used to accomplish
 * > https://www.baeldung.com/opencsv
 */
public class EnrollmentCsvParser {

    private static final String PROJ_DIR = "C:\\Work\\IdeaProjects\\availity\\resources\\";
    private static final String INPUT_FILE = "input\\enrollees.csv";
    private static final String OUTPUT_DIR = PROJ_DIR + "output\\";

    /**
     * This is first method to be called when running the class.
     *
     * @param args n/a
     * @throws IOException when error in accessing input and output files.
     */
    public static void main(String[] args) {
        try {
            List<Enrollee> enrollees = new CsvToBeanBuilder(new FileReader(PROJ_DIR + INPUT_FILE))
                    .withType(Enrollee.class)
                    .withFilter(new CsvToBeanFilter() {
                        /*
                         * This filter ignores empty lines from the input
                         */
                        @Override
                        public boolean allowLine(String[] strings) {
                            for (String one : strings) {
                                if (one != null && one.length() > 0) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    })
                    .build()
                    .parse();

            enrollees.forEach(System.out::println);
            EnrollmentCsvParser parser = new EnrollmentCsvParser();
            Collection<Enrollee> enrolleeVersionedList = parser.removeUserIdDuped(enrollees);

            Map<String, List<String[]>> enrolleesByInsurer = parser.enrolleeListToStringArray(enrolleeVersionedList);
            for (String insurer : enrolleesByInsurer.keySet()) {
                parser.csvWriterAll(enrolleesByInsurer.get(insurer), insurer, OUTPUT_DIR);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * This runs in O(n) to check the userId for any duplicates if any duplicate is found then
     * keep only the largest version of the object.
     *
     * @param enrollees list of Enrollee objects
     * @return map containing
     */
    static Collection<Enrollee> removeUserIdDuped(List<Enrollee> enrollees) {
        if (enrollees == null || enrollees.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, Enrollee> enrolleeVersionMap = new HashMap<>();
        Enrollee cachedEnrollee = null;

        //if userId is same then only keep larger version
        for (Enrollee enrollee : enrollees) {
            cachedEnrollee = enrolleeVersionMap.getOrDefault(enrollee.getInsuranceCompany() + enrollee.getUserId(), null);
            if (cachedEnrollee != null && cachedEnrollee.getVersion() > enrollee.getVersion()) {
                continue;
            } else {
                enrolleeVersionMap.put(enrollee.getInsuranceCompany() + enrollee.getUserId(), enrollee);
            }
        }
        return enrolleeVersionMap.values();
    }

    /**
     * sort list of Enrollee object using Comparator by lastname then firstname
     *
     * @param enrollees unsorted enrolleees list
     */
    static void sortEnrollees(List<Enrollee> enrollees) {
        //Compare by first name and then last name
        Comparator<Enrollee> compareByName = Comparator
                .comparing(Enrollee::getLastName)
                .thenComparing(Enrollee::getFirstName);

        Collections.sort(enrollees, compareByName);
    }

    /**
     * This runs in O(n) to iterate over enrollees list and transform the Enrollee object into String array.
     *
     * @param enrolleesInput list of enrollees sorted by insurer, lastname, then firstname. Also duplicate userId are removed
     * @return map grouping all enrollees by insurer
     */
    static Map<String, List<String[]>> enrolleeListToStringArray(Collection<Enrollee> enrolleesInput) {
        List<Enrollee> enrollees = new ArrayList<>(enrolleesInput);
        sortEnrollees(enrollees);

        Map<String, List<String[]>> map = new HashMap<>();
        String[] enrollColumns = null;
        //convert Enrollee object to string array
        for (Enrollee enrollee : enrollees) {
            List<String[]> stringArray = map.getOrDefault(enrollee.getInsuranceCompany(), new ArrayList<>());
            enrollColumns = new String[5];
            enrollColumns[0] = enrollee.getUserId();
            enrollColumns[1] = enrollee.getFirstName();
            enrollColumns[2] = enrollee.getLastName();
            enrollColumns[3] = String.valueOf(enrollee.getVersion());
            enrollColumns[4] = enrollee.getInsuranceCompany();
            stringArray.add(enrollColumns);
            map.put(enrollee.getInsuranceCompany(), stringArray);
        }
        return map;
    }

    /**
     * Writes the string array into a CSV file using OpenCVS library
     *
     * @param stringArray string array of enrollees sorted by insurer, lastname, then firstname.
     *                    Also duplicate userId are removed
     * @param insurer     used as filename to group the enrollees by insurer
     * @param outputDir   where the files will be written
     * @throws IOException when error in accessing output file.
     */
    static void csvWriterAll(List<String[]> stringArray, String insurer, String outputDir) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(outputDir + "\\" + insurer + ".csv"));
        writer.writeAll(stringArray);
        writer.close();
    }
}

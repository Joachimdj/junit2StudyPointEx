/*
 * 2017 Joachim Dittman 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class RecognitionITFIX {

    private File plate;
    private String expected;
    private CarSnapshot carSnapshot;

    public RecognitionITFIX(File plate, String expected) {
        this.plate = plate;
        this.expected = expected;
    }

    @Before
    public void init() throws IOException {
        carSnapshot = new CarSnapshot(new FileInputStream(plate));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> plateDataCreator() throws FileNotFoundException, IOException {

        String snapshotDirPath = "src/test/resources/snapshots";
        String resultsPath = "src/test/resources/results.properties";
        Properties properties;

        try (InputStream resultsStream = new FileInputStream(new File(resultsPath))) {
            properties = new Properties();
            properties.load(resultsStream);
        }

        assertThat(properties.size(), greaterThan(0));

        File snapshotDir = new File(snapshotDirPath);
        File[] snapshots = snapshotDir.listFiles();

        assertThat(snapshots, notNullValue());
        assertThat(snapshots.length, greaterThan(0));

        Collection<Object[]> data = new ArrayList();
        for (File file : snapshots) {
            String name = file.getName();
            String plateExpected = properties.getProperty(name);
            data.add(new Object[]{file, plateExpected});
        }

        return data;

    }

    @Test
    public void testAllPlates() throws ParserConfigurationException, IOException, SAXException {
        Intelligence intel = new Intelligence();
        assertThat(carSnap, notNullValue());
        assertThat(carSnap, notNullValue());
        assertThat(carSnap.getImage(), notNullValue());
        assertThat(plateExpected, notNullValue());
        String numberPlate = intel.recognize(carSnap, false);
        assertThat(plateExpected, equalTo(numberPlate));
    }


}
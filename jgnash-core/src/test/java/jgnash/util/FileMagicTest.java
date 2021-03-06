/*
 * jGnash, a personal finance application
 * Copyright (C) 2001-2016 Craig Cavanaugh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jgnash.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

/**
 * Test FileMagic.
 * 
 * @author Craig Cavanaugh
 */
public class FileMagicTest {

    /**
     * Test for jGnash 1.x file identification
     */
    @Test
    public void db4ojGnash1xTest() {

        URL url = Object.class.getResource("/test1.jgnash.xml");

        try {
            assertTrue(FileMagic.isValidVersion1File(new File(url.toURI())));
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileMagicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test for Ofx version 1 file identification.
     */
    @Test
    public void OfxV1Test() {

        URL url = Object.class.getResource("/bank1.ofx");

        try {
            assertTrue(FileMagic.isOfxV1(new File(url.toURI())));
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileMagicTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        url = Object.class.getResource("/ofx_spec201_stmtrs_example.xml");

        try {
            assertFalse(FileMagic.isOfxV1(new File(url.toURI())));
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileMagicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test for Ofx version 1 file encoding.
     */
    @Test
    public void OfxV1EncodingTest1() {

        URL url = Object.class.getResource("/bank1.ofx");

        try {
            assertTrue(FileMagic.getOfxV1Encoding(new File(url.toURI())).equals("windows-1252"));
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileMagicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test for Ofx version 1 file encoding.
     */
    @Test
    public void OfxV1EncodingTest2() {

        URL url = Object.class.getResource("/File_with_Accents.ofx");

        try {
            assertTrue(FileMagic.getOfxV1Encoding(new File(url.toURI())).equals("ISO-8859-1"));
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileMagicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test for Ofx version 2 file identification.
     */
    @Test
    public void OfxV2Test() {

        URL url = Object.class.getResource("/ofx_spec201_stmtrs_example.xml");

        try {
            assertTrue(FileMagic.isOfxV2(new File(url.toURI())));
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileMagicTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        url = Object.class.getResource("/bank1.ofx");

        try {
            assertFalse(FileMagic.isOfxV2(new File(url.toURI())));
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileMagicTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

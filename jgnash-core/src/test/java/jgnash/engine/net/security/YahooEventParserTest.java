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
package jgnash.engine.net.security;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import jgnash.engine.AbstractEngineTest;
import jgnash.engine.DataStoreType;
import jgnash.engine.Engine;
import jgnash.engine.EngineFactory;
import jgnash.engine.SecurityHistoryEvent;
import jgnash.engine.SecurityHistoryNode;
import jgnash.engine.SecurityNode;
import jgnash.net.security.YahooEventParser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JUnit test for the Yahoo security downloader.
 *
 * @author Craig Cavanaugh
 */
public class YahooEventParserTest extends AbstractEngineTest {

    @Override
    protected Engine createEngine() throws IOException {
        database = testFolder.newFile("yahoo-test.bxds").getAbsolutePath();
        EngineFactory.deleteDatabase(database);

        return EngineFactory.bootLocalEngine(database, EngineFactory.DEFAULT, EngineFactory.EMPTY_PASSWORD,
                DataStoreType.BINARY_XSTREAM);
    }

    @Test
    public void testParser() {

        final SecurityNode ibm = new SecurityNode(e.getDefaultCurrency());
        ibm.setSymbol("IBM");
        ibm.setScale((byte) 2);

        final SecurityHistoryNode historyNode = new SecurityHistoryNode(LocalDate.of(1962, Month.JANUARY, 1),
                BigDecimal.TEN, 1000, BigDecimal.TEN, BigDecimal.TEN);

        e.addSecurity(ibm);
        e.addSecurityHistory(ibm, historyNode);

        final Set<SecurityHistoryEvent> events = YahooEventParser.retrieveNew(ibm, LocalDate.of(2015, Month.AUGUST, 22));

        assertNotNull(events);

        assertEquals(219, events.size());
    }
}

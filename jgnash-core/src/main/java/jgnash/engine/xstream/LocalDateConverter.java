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
package jgnash.engine.xstream;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import jgnash.time.DateUtils;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;

/**
 * XStream converter for {@code LocalDateTime} objects.
 *
 * This uses the same default format as {@code com.thoughtworks.xstream.converters.basic.DateConverter}
 *
 * @author Craig Cavanaugh
 *
 * @see com.thoughtworks.xstream.converters.basic.DateConverter
 */
public class LocalDateConverter extends AbstractSingleValueConverter {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateUtils.DEFAULT_XSTREAM_LOCAL_DATE_PATTERN);

    // Delegate to the xstream converter if an old file is being read
    private final DateConverter dateConverter = new DateConverter();

    @SuppressWarnings("rawtypes") 
    @Override
    public boolean canConvert(final Class type) {
        return type.equals(LocalDate.class);
    }

    @Override
    public Object fromString(final String str) {
        if (!str.isEmpty()) {
            if (str.length() == 10) {   // newer file
                return LocalDate.from(dateTimeFormatter.parse(str));
            } else {    // older file, delegate to the default XStream converter
                return DateUtils.asLocalDate((Date)dateConverter.fromString(str));
            }
        }
        return null;
    }

    @Override
    public String toString(final Object obj) {
        return dateTimeFormatter.format((LocalDate)obj);
    }
}

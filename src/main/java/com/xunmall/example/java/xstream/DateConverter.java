package com.xunmall.example.java.xstream;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by wangyanjing on 2018/11/28.
 */
public class DateConverter implements Converter {

    private Locale locale;

    public DateConverter(Locale locale) {
        super();
        this.locale = locale;
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, this.locale);
        hierarchicalStreamWriter.setValue(dateFormat.format(o));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        GregorianCalendar calendar = new GregorianCalendar();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, this.locale);

        try {
            calendar.setTime(dateFormat.parse(hierarchicalStreamReader.getValue()));
        } catch (ParseException e) {
            throw new ConversionException(e.getMessage(), e);
        }
        return calendar.getGregorianChange();
    }

    @Override
    public boolean canConvert(Class aClass) {
        return Date.class.isAssignableFrom(aClass);
    }
}

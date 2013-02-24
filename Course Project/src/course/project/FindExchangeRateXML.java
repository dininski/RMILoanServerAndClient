/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course.project;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author support
 */
public class FindExchangeRateXML {

    private static double exchangeRate;
    private static String currency;

    public static double FindExchangeRatesXML(String curr) {
        currency = curr;
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean foundCurrency = false;
                boolean exchangeRateFound = false;
                boolean currencyMatched = false;
                String currentCurrency;

                public void startElement(String uri, String localName, String qName,
                        Attributes attributes) throws SAXException {

                    if (qName.equalsIgnoreCase("currency")) {
                        foundCurrency = true;
                    }
                    if (qName.equalsIgnoreCase("inBGN")) {
                        exchangeRateFound = true;
                    }
                    
                }

                @Override
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    String currentContent = new String(ch, start, length);

                    if (exchangeRateFound) {
                        if (currentCurrency.equalsIgnoreCase(currency)) {
                            exchangeRate = Double.parseDouble(currentContent);
                        }
                        exchangeRateFound = false;
                    }

                    if (foundCurrency) {
                        currentCurrency = currentContent;

                        foundCurrency = false;
                    }
                }
            };

            saxParser.parse("exchangeRates.xml", handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exchangeRate;
    }
}

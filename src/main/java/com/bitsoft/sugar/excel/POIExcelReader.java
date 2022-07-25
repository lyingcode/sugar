package com.bitsoft.sugar.excel;

import com.bitsoft.sugar.excel.listener.StudentHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.xmlbeans.XmlOptionsBean;
import org.apache.xmlbeans.impl.common.SAXHelper;
import org.springframework.util.StopWatch;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
@Slf4j
public class POIExcelReader {
    public static void main(String[] args) {
        String excelFile = "/Users/jameszhang/demo.xlsx";
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("POI解析文件");
            importExcel(excelFile);
            stopWatch.stop();
            log.info(stopWatch.prettyPrint());
        } catch (OpenXML4JException|IOException|ParserConfigurationException|SAXException e) {
            log.error("文件解析异常",e);
        }
    }

    public static void importExcel(String path) throws OpenXML4JException, IOException, ParserConfigurationException, SAXException {
        OPCPackage opcPackage = OPCPackage.open(path, PackageAccess.READ);
        XSSFReader xssfReader = new XSSFReader(opcPackage);
        ReadOnlySharedStringsTable sharedStringsTable = new ReadOnlySharedStringsTable(opcPackage);
        StylesTable stylesTable = xssfReader.getStylesTable();
        XMLReader xmlReader = SAXHelper.newXMLReader(new XmlOptionsBean());
        XSSFSheetXMLHandler xssfSheetXMLHandler = new XSSFSheetXMLHandler(stylesTable, sharedStringsTable, new StudentHandler(), false);
        xmlReader.setContentHandler(xssfSheetXMLHandler);
        Iterator<InputStream> iterator = xssfReader.getSheetsData();
        while (iterator.hasNext()) {
            InputStream inputStream = iterator.next();
            InputSource inputSource = new InputSource(inputStream);
            xmlReader.parse(inputSource);
        }
    }
}

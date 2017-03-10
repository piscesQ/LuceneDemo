import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;

/**
 * @author KoreQ
 * @version V1.0
 * @Copyright (c) 2016 
 * @Description extract file content
 * @date 2016/1/6
 */
public class ReadContextUtil {

    /**
     * 处理word2003
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static String readWord(String path) throws Exception {
        String bodyText = null;
        InputStream inputStream = new FileInputStream(path);
        WordExtractor extractor = new WordExtractor(inputStream);
        bodyText = extractor.getText();
        return bodyText;
    }

//    /**
//     * 处理word2007
//     *
//     * @param path
//     * @return
//     * @throws IOException
//     * @throws OpenXML4JException
//     * @throws XmlException
//     */
//    public static String readWord2007(String path) throws IOException, OpenXML4JException, XmlException {
//        OPCPackage opcPackage = POIXMLDocument.openPackage(path);
//        POIXMLTextExtractor ex = new XWPFWordExtractor(opcPackage);
//        return ex.getText();
//    }

    /**
     * 处理excel2003
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String ReadExcel(String path) throws IOException {
        InputStream inputStream = null;
        String content = null;
        try {
            inputStream = new FileInputStream(path);
            HSSFWorkbook wb = new HSSFWorkbook(inputStream);
            ExcelExtractor extractor = new ExcelExtractor(wb);
            extractor.setFormulasNotResults(true);
            extractor.setIncludeSheetNames(false);
            content = extractor.getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 处理excel2007
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readExcel2007(String path) throws IOException {
        StringBuffer content = new StringBuffer();
        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        XSSFWorkbook xwb = new XSSFWorkbook(path);
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
            XSSFSheet xSheet = xwb.getSheetAt(numSheet);
            if (xSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
                XSSFRow xRow = xSheet.getRow(rowNum);
                if (xRow == null) {
                    continue;
                }
                // 循环列Cell
                for (int cellNum = 0; cellNum <= xRow.getLastCellNum(); cellNum++) {
                    XSSFCell xCell = xRow.getCell(cellNum);
                    if (xCell == null) {
                        continue;
                    }
                    if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
                        content.append(xCell.getBooleanCellValue());
                    } else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                        content.append(xCell.getNumericCellValue());
                    } else {
                        content.append(xCell.getStringCellValue());
                    }
                }
            }
        }

        return content.toString();
    }

    /**
     * 处理ppt
     *
     * @param path
     * @return
     */
    public static String readPowerPoint(String path) {
        StringBuffer content = new StringBuffer("");
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            SlideShow ss = new SlideShow(new HSLFSlideShow(new FileInputStream(path)));// is
            // 为文件的InputStream，建立SlideShow
            Slide[] slides = ss.getSlides();// 获得每一张幻灯片
            for (int i = 0; i < slides.length; i++) {
                TextRun[] t = slides[i].getTextRuns();// 为了取得幻灯片的文字内容，建立TextRun
                for (int j = 0; j < t.length; j++) {
                    content.append(t[j].getText());// 这里会将文字内容加到content中去
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return content.toString();
    }

    /**
     * 处理pdf
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readPdf(String path) throws IOException {
        StringBuffer content = new StringBuffer("");// 文档内容
        PDDocument pdfDocument = null;
        try {
            FileInputStream fis = new FileInputStream(path);
//            PDFParser pdfParser = new PDFParser(fis);
//            pdfParser.parse();
//            COSDocument document = pdfParser.getDocument();
//            document.print();

            PDFTextStripper stripper = new PDFTextStripper();
            pdfDocument = PDDocument.load(fis);
            StringWriter writer = new StringWriter();
            stripper.writeText(pdfDocument, writer);
            content.append(writer.getBuffer().toString());
            fis.close();
        } catch (java.io.IOException e) {
            System.err.println("IOException=" + e);
            e.printStackTrace();
//            System.exit(1);
        } finally {
            if (pdfDocument != null) {
                org.apache.pdfbox.cos.COSDocument cos = pdfDocument.getDocument();
                cos.close();
                pdfDocument.close();
            }
        }

        return content.toString();
    }

    /**
     * 处理txt
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String readTxt(String path) throws IOException {
        StringBuffer sb = new StringBuffer("");
        InputStream is = new FileInputStream(path);
        // 动态获得系统编码，windows是GBK，Ubuntu是UTF-8，否则将出现乱码
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, System.getProperty("file.encoding")));
        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\r");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString().trim();
    }

    /**
     * read Html
     *
     * @param path
     * @return
     */
    public static String readHtml(String path) {

        StringBuffer content = new StringBuffer("");
        File file = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // 读取页面
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    fis, "utf-8"));//这里的字符编码要注意，要对上html头文件的一致，否则会出乱码

            String line = null;

            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String contentString = content.toString();
        return contentString;
    }

//    public static String getXMLContent(String filename) {
//        SAXBuilder sb = new SAXBuilder();
//        try {
//            org.jdom.Document doc = sb.build(filename); // 生成文档对象
//            Element root = doc.getRootElement();// 获取根节点
//            //     获取根节点的comment 属性
//            String str1 = root.getAttributeValue("comment");
//            System.out.println("根节点说明 : " + str1);
//            //     获取Listner节点的className属性
//            String str2 = root.getChild("Listener").getAttributeValue("className");
//            System.out.println("Listener 节点 className 属性 : " + str2);
//            //     获取Service 节点的name 属性
//            String str3 = root.getChild("Service").getAttributeValue("name");
//            System.out.println("Service 节点 Name 属性 : " + str3);
//            //     生成XML 的输出对象
//            XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
//            String outStr = xmlOut.outputString(root); // 输出根节点内容字符串
//            System.out.println("");
//            System.out.println(outStr); // 输出根节点内容
//            return outStr;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
}



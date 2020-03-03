package com.Servlet;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Servlet2", urlPatterns = "/Servlet2")
public class Servlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    public Cell getCell(String text, TextAlignment alignment) {
        Cell cell = new Cell().add(new Paragraph(text));
        cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/pdf");
        response.setHeader("Expires","0");
        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma","public");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(response.getOutputStream()));
        Document doc = new Document(pdfDoc);
        //图片的添加要放在添加table之前，不然图片会覆盖table里的文字
        String IMG = "E:/IdeaProjects/com.Sharka.javaWeb/ItextDemo/src/mountain.jpg";
        ImageData data = ImageDataFactory.create(IMG);
        Image img = new Image(data);
        img.setFixedPosition(40,700);
        img.setWidth(200);
        doc.add(img);
        //创建一个table 有1列
        Table table = new Table(1);
        table.addCell(getCell("登高", TextAlignment.CENTER));
        table.addCell(getCell("电商11802班陈思琴", TextAlignment.CENTER));
        table.addCell(getCell("风急天高猿啸哀，渚清沙白鸟飞回。", TextAlignment.CENTER));
        table.addCell(getCell("无边落木萧萧下，不尽长江滚滚来。",TextAlignment.CENTER));
        table.addCell(getCell("万里悲秋常作客，百年多病独登台。",TextAlignment.CENTER));
        table.addCell(getCell("艰难苦恨繁霜鬓，潦倒新停浊酒杯。",TextAlignment.CENTER));
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",true);
        table.setFont(font);
        doc.add(table);


        doc.close();
        System.out.println("成功显示pdf");
    }
}

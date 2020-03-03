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
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet", urlPatterns = "/Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    private static class ImageBackgroundCellRenderer extends CellRenderer {
        protected Image img;

        public ImageBackgroundCellRenderer(Cell modelElement, Image img) {
            super(modelElement);
            this.img = img;
        }

        // If renderer overflows on the next area, iText uses getNextRender() method to create a renderer for the overflow part.
        // If getNextRenderer isn't overriden, the default method will be used and thus a default rather than custom
        // renderer will be created
        @Override
        public IRenderer getNextRenderer() {
            return new ImageBackgroundCellRenderer((Cell) modelElement, img);
        }

        @Override
        public void draw(DrawContext drawContext) {
            img.scaleToFit(getOccupiedAreaBBox().getWidth(), getOccupiedAreaBBox().getHeight()*9/10);
            drawContext.getCanvas().addXObject(img.getXObject(), getOccupiedAreaBBox());
            super.draw(drawContext);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/pdf");
        response.setHeader("Expires","0");
        response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma","public");

        String IMG = "E:/IdeaProjects/com.Sharka.javaWeb/ItextDemo/src/mountain.jpg";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(response.getOutputStream()));
        Document doc = new Document(pdfDoc);
        //创建一个table 有1列
        Table table = new Table(1);
        //table居中显示
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        //cell中添加内容
        Cell cell = new Cell();
        cell.add(new Paragraph("登高"));
        cell.add(new Paragraph("电商11802班陈思琴"));
        cell.add(new Paragraph("风急天高猿啸哀，渚清沙白鸟飞回。"));
        cell.add(new Paragraph("无边落木萧萧下，不尽长江滚滚来。"));
        cell.add(new Paragraph("万里悲秋常作客，百年多病独登台。"));
        cell.add(new Paragraph("艰难苦恨繁霜鬓，潦倒新停浊酒杯。"));
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setBorder(Border.NO_BORDER);
        //table中文字的字体设置
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",true);
        table.setFont(font);
        //图片的导入
        ImageData data = ImageDataFactory.create(IMG);
        Image img = new Image(data);
        //一个cell添加图片背景
        cell.setNextRenderer(new ImageBackgroundCellRenderer(cell, img));
        table.addCell(cell);
        doc.add(table);
        doc.close();
        System.out.println("成功显示pdf");



    }
}

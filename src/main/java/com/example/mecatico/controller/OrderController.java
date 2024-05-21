package com.example.mecatico.controller;

import com.example.mecatico.model.Order;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Order order) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("MECATICO COMPANY"));
            document.add(new Paragraph("Orden de Compra"));
            document.add(new Paragraph("Cédula/NIT: " + order.getId()));
            document.add(new Paragraph("Teléfono: " + order.getPhone()));
            document.add(new Paragraph("Producto 1: " + order.getProduct1()));
            document.add(new Paragraph("Producto 2: " + order.getProduct2()));
            document.add(new Paragraph("Producto 3: " + order.getProduct3()));
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=orden_de_compra.pdf");

        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(out.toByteArray());
    }
}

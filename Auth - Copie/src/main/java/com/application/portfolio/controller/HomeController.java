/**
 * 
 */
package com.application.portfolio.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;

/**
 * 
 */
@Controller
public class HomeController {

	@GetMapping({"/","","/home"})
	public String showHomePage(){
		return "home";
		
	}
	
	@GetMapping({"/cv"})
	public ResponseEntity<ByteArrayResource> getCv(){
	
		try {
			
			//String htmlSource = Files.readString(Paths.get("home.html"));
	        // 🔹 Chemins d'entrée/sortie
	        String htmlSource = "src/main/resources/templates/home.html"; // ton fichier HTML complet
	        String pdfDest = "CV_Professionnel_Arafet.pdf";
	
	        // 🔹 Charger le contenu HTML (UTF-8)
	        String html = readHtmlFile(htmlSource);
	
	        // 🔹 Configuration du convertisseur
	        ConverterProperties props = new ConverterProperties();
	        props.setCharset("UTF-8");
	        ByteArrayOutputStream  pdfStream  = new ByteArrayOutputStream();
	        // 🔹 Conversion HTML → PDF
	        HtmlConverter.convertToPdf(new ByteArrayInputStream(html.getBytes("UTF-8")),
	        		pdfStream , props);
	        
	        // 3️⃣ Retourner le PDF en réponse HTTP
            byte[] pdfBytes = pdfStream.toByteArray();
            ByteArrayResource resource = new ByteArrayResource(pdfBytes); 
            
	        return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CV_Arafet_Ferdjani.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(resource);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().build();
	    }
		
		
	}

// Fonction utilitaire pour lire le HTML complet
private static String readHtmlFile(String filePath) throws IOException, UnsupportedEncodingException, FileNotFoundException, java.io.IOException {
    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
        String line;
        while ((line = br.readLine()) != null) {
            contentBuilder.append(line).append("\n");
        }
    }
    return contentBuilder.toString();
}
}

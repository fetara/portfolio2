/**
 * 
 */
package com.application.portfolio.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	@GetMapping({ "/", "", "/home" })
	public String showHomePage() {
		return "home";

	}
	@GetMapping({ "/backoffice" })
	public String getBackoffice() {
		
		return "back";
	}

	@GetMapping({ "/cv" })
	public ResponseEntity<ByteArrayResource> getCv() {

		try {
			// Générer le HTML du CV
			String cvHtml = generateCvHtml();

			// Configuration du convertisseur
			ConverterProperties props = new ConverterProperties();
			props.setCharset("UTF-8");
			ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

			// Conversion HTML → PDF
			HtmlConverter.convertToPdf(new ByteArrayInputStream(cvHtml.getBytes("UTF-8")), pdfStream, props);

			// Retourner le PDF en réponse HTTP
			byte[] pdfBytes = pdfStream.toByteArray();
			ByteArrayResource resource = new ByteArrayResource(pdfBytes);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=CV_Arafet_Ferdjani.pdf")
					.contentType(MediaType.APPLICATION_PDF).contentLength(pdfBytes.length).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	/**
	 * Génère le HTML du CV à partir du contenu de home.html
	 */
	private String generateCvHtml() {
		StringBuilder html = new StringBuilder();

		html.append("<!DOCTYPE html>");
		html.append("<html lang='fr'>");
		html.append("<head>");
		html.append("<meta charset='UTF-8'>");
		html.append("<title>CV - Arafet Ferdjani</title>");
		html.append("<style>");
		html.append(getCvStyles());
		html.append("</style>");
		html.append("</head>");
		html.append("<body>");

		// En-tête du CV
		html.append("<div class='header'>");
		html.append("<h1>Arafet Ferdjani</h1>");
		html.append("<h2>Ingénieur Solutions Cloud</h2>");
		html.append("<div class='contact-info'>");
		html.append("<p>📧 arafet.ferdjani@gmail.com</p>");
		html.append("<p>🔗 linkedin.com/in/arafet-ferdjani-a20629a1</p>");
		html.append("</div>");
		html.append("</div>");

		// Section À Propos
		html.append("<div class='section'>");
		html.append("<h3 class='section-title'>À PROPOS</h3>");
		html.append("<p class='highlight-text'>");
		html.append(
				"Transformer les défis techniques en solutions cloud évolutives grâce à un développement moderne et performant.");
		html.append("</p>");
		html.append("<p>");
		html.append(
				"En tant qu'Ingénieur en Développement et Solutions Cloud, je me spécialise dans la migration et la modernisation des applications vers des plateformes cloud privées. ");
		html.append(
				"Mon rôle consiste à étudier les exigences techniques et fonctionnelles, à concevoir des architectures adaptées et à accompagner les équipes de développement dans la mise en œuvre de solutions performantes et sécurisées.");
		html.append("</p>");
		html.append("</div>");

		// Section Compétences
		html.append("<div class='section'>");
		html.append("<h3 class='section-title'>COMPÉTENCES & EXPERTISE</h3>");

		// DevOps & Automatisation
		html.append("<div class='skill-category'>");
		html.append("<h4>🔧 DevOps & Automatisation</h4>");
		html.append("<p class='skill-description'>Pipelines CI/CD et automatisation d'infrastructure</p>");
		html.append("<div class='tags'>");
		html.append("<span class='tag'>CloudBees CI</span>");
		html.append("<span class='tag'>CloudBees CD/RO</span>");
		html.append("<span class='tag'>Docker</span>");
		html.append("<span class='tag'>Kubernetes</span>");
		html.append("<span class='tag'>Openshift</span>");
		html.append("</div>");
		html.append("</div>");

		// Programmation
		html.append("<div class='skill-category'>");
		html.append("<h4>💻 Programmation</h4>");
		html.append("<p class='skill-description'>Langages et frameworks</p>");
		html.append("<div class='tags'>");
		html.append("<span class='tag'>Java</span>");
		html.append("<span class='tag'>Spring</span>");
		html.append("<span class='tag'>C#.net</span>");
		html.append("<span class='tag'>JavaScript</span>");
		html.append("<span class='tag'>Go</span>");
		html.append("</div>");
		html.append("</div>");

		// Monitoring
		html.append("<div class='skill-category'>");
		html.append("<h4>📊 Monitoring & Observabilité</h4>");
		html.append("<p class='skill-description'>Surveillance full-stack et optimisation des performances</p>");
		html.append("<div class='tags'>");
		html.append("<span class='tag'>Prometheus</span>");
		html.append("<span class='tag'>Grafana</span>");
		html.append("<span class='tag'>Dynatrace</span>");
		html.append("</div>");
		html.append("</div>");
		html.append("</div>");

		// Section Expérience Professionnelle
		html.append("<div class='section'>");
		html.append("<h3 class='section-title'>EXPÉRIENCE PROFESSIONNELLE</h3>");

		// Projet 1 - CM-CIC
		html.append("<div class='experience'>");
		html.append("<div class='experience-header'>");
		html.append("<h4>Ingénieur Solutions Cloud Privé</h4>");
		html.append("<span class='period'>2020 - 2026</span>");
		html.append("</div>");
		html.append("<p class='company'>CM-CIC</p>");
		html.append(
				"<p class='project-desc'>Migration des applications existantes vers une plateforme cloud privée.</p>");
		html.append("<ul class='responsibilities'>");
		html.append("<li>Étudier les exigences techniques et fonctionnelles</li>");
		html.append(
				"<li>Fournir des conseils techniques et un support aux équipes de développeurs, ainsi que le développement et la maintenance des chaînes d'intégration et déploiement continue</li>");
		html.append(
				"<li>Définir la meilleure architecture pour chaque application, participer activement aux travaux d'étude de faisabilité, de prototypage et d'architecture</li>");
		html.append("<li>Gestion de projet, planification des tâches et suivi de l'avancement</li>");
		html.append("<li>Assurer le lien entre les équipes plateforme et les équipes de développement</li>");
		html.append("</ul>");
		html.append("<div class='tags'>");
		html.append("<span class='tag'>Helm</span>");
		html.append("<span class='tag'>Istio</span>");
		html.append("<span class='tag'>Vault</span>");
		html.append("<span class='tag'>Artifactory</span>");
		html.append("<span class='tag'>Dynatrace</span>");
		html.append("<span class='tag'>Jaeger</span>");
		html.append("<span class='tag'>Kiali</span>");
		html.append("<span class='tag'>C#.Net / API-Rest</span>");
		html.append("<span class='tag'>Git-GitLab</span>");
		html.append("<span class='tag'>Cloudbees CI & CD/RO</span>");
		html.append("</div>");
		html.append("</div>");

		// Projet 2 - Crédit Agricole
		html.append("<div class='experience'>");
		html.append("<div class='experience-header'>");
		html.append("<h4>Ingénieur en Technologie de l'Information</h4>");
		html.append("<span class='period'>2015 - 2020</span>");
		html.append("</div>");
		html.append("<p class='company'>Crédit Agricole (CGI) - Projet CATS</p>");
		html.append(
				"<p class='project-desc'>Interventions sur les applications web, Web API et applications mobiles du Crédit Agricole.</p>");

		html.append(
				"<p class='project-subtitle'><strong>Portail financier</strong> - Application poste de travail agent</p>");
		html.append("<ul class='responsibilities'>");
		html.append("<li>Conception et développement des nouvelles fonctionnalités</li>");
		html.append("<li>Migration des APIs REST sur des dockers engine</li>");
		html.append("<li>Supervision des dockers engine et des serveurs présentation</li>");
		html.append("<li>Maintenance du portail, analyse et correction des incidents de production</li>");
		html.append("<li>Leader technique</li>");
		html.append("</ul>");

		html.append("<p class='project-subtitle'><strong>Application Mobile</strong></p>");
		html.append("<ul class='responsibilities'>");
		html.append("<li>Développement et maintenance du console d'administration de l'application mobile</li>");
		html.append("<li>Conception et développement des API</li>");
		html.append("<li>Intégration de l'API du côté serveur Applicatif</li>");
		html.append("<li>Livraison de la matière mobile Android/Apple sur les stores interne</li>");
		html.append("</ul>");

		html.append("<p class='project-subtitle'><strong>Vente et Rachat Parts Immobiliers Amundi</strong></p>");
		html.append("<ul class='responsibilities'>");
		html.append(
				"<li>Mise en œuvre des Unités Applicatives permettant de souscrire, vendre des parts immobiliers et afficher la synthèse des parts immobiliers</li>");
		html.append("</ul>");

		html.append("<div class='tags'>");
		html.append("<span class='tag'>Java/J2EE</span>");
		html.append("<span class='tag'>Spring</span>");
		html.append("<span class='tag'>Tomcat</span>");
		html.append("<span class='tag'>JSF</span>");
		html.append("<span class='tag'>REST</span>");
		html.append("<span class='tag'>Docker Engine</span>");
		html.append("<span class='tag'>MySQL</span>");
		html.append("<span class='tag'>SQL Server</span>");
		html.append("<span class='tag'>WebSphere</span>");
		html.append("<span class='tag'>Git</span>");
		html.append("<span class='tag'>SVN</span>");
		html.append("</div>");
		html.append("</div>");

		html.append("</div>");

		// Footer
		html.append("<div class='footer'>");
		html.append("<p>CV généré le "
				+ java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				+ "</p>");
		html.append("</div>");

		html.append("</body>");
		html.append("</html>");

		return html.toString();
	}

	/**
	 * Styles CSS pour le CV
	 */
	private String getCvStyles() {
		return """
				* {
					margin: 0;
					padding: 0;
					box-sizing: border-box;
				}

				body {
					font-family: 'Arial', 'Helvetica', sans-serif;
					font-size: 11pt;
					line-height: 1.6;
					color: #333;
					padding: 20px;
					background: #fff;
				}

				.header {
					text-align: center;
					margin-bottom: 30px;
					padding-bottom: 20px;
					border-bottom: 3px solid #2563eb;
				}

				h1 {
					font-size: 28pt;
					color: #1e40af;
					margin-bottom: 5px;
					font-weight: bold;
				}

				h2 {
					font-size: 16pt;
					color: #64748b;
					margin-bottom: 15px;
					font-weight: normal;
				}

				.contact-info {
					display: flex;
					justify-content: center;
					gap: 30px;
					flex-wrap: wrap;
				}

				.contact-info p {
					font-size: 10pt;
					color: #475569;
				}

				.section {
					margin-bottom: 25px;
					page-break-inside: avoid;
				}

				.section-title {
					font-size: 14pt;
					color: #1e40af;
					font-weight: bold;
					text-transform: uppercase;
					margin-bottom: 15px;
					padding-bottom: 5px;
					border-bottom: 2px solid #e2e8f0;
					letter-spacing: 1px;
				}

				.highlight-text {
					font-style: italic;
					color: #475569;
					margin-bottom: 10px;
					padding: 10px;
					background: #f1f5f9;
					border-left: 4px solid #2563eb;
				}

				.skill-category {
					margin-bottom: 15px;
				}

				.skill-category h4 {
					font-size: 12pt;
					color: #1e293b;
					margin-bottom: 5px;
				}

				.skill-description {
					font-size: 10pt;
					color: #64748b;
					margin-bottom: 8px;
				}

				.tags {
					display: flex;
					flex-wrap: wrap;
					gap: 6px;
					margin-top: 8px;
				}

				.tag {
					display: inline-block;
					padding: 4px 10px;
					background: #e0e7ff;
					color: #3730a3;
					border-radius: 4px;
					font-size: 9pt;
					font-weight: 500;
				}

				.experience {
					margin-bottom: 20px;
					page-break-inside: avoid;
				}

				.experience-header {
					display: flex;
					justify-content: space-between;
					align-items: baseline;
					margin-bottom: 5px;
				}

				.experience-header h4 {
					font-size: 13pt;
					color: #1e293b;
					font-weight: bold;
				}

				.period {
					font-size: 10pt;
					color: #2563eb;
					font-weight: bold;
				}

				.company {
					font-size: 11pt;
					color: #64748b;
					font-weight: 600;
					margin-bottom: 5px;
				}

				.project-desc {
					font-size: 10pt;
					color: #475569;
					margin-bottom: 10px;
				}

				.project-subtitle {
					font-size: 10pt;
					color: #1e293b;
					margin-top: 10px;
					margin-bottom: 5px;
				}

				.responsibilities {
					margin-left: 20px;
					margin-bottom: 10px;
				}

				.responsibilities li {
					font-size: 10pt;
					color: #475569;
					margin-bottom: 4px;
					line-height: 1.5;
				}

				.footer {
					margin-top: 30px;
					padding-top: 15px;
					border-top: 1px solid #e2e8f0;
					text-align: center;
					font-size: 9pt;
					color: #94a3b8;
				}

				p {
					margin-bottom: 8px;
				}

				ul {
					margin-bottom: 10px;
				}

				@page {
					size: A4;
					margin: 15mm;
				}
				""";
	}
}

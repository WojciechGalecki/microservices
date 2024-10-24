package wg.microservices.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class ApiConfiguration {

    @Value("${api.version}")         String apiVersion;
    @Value("${api.title}")           String apiTitle;
    @Value("${api.description}")     String apiDescription;
    @Value("${api.termsOfService}")  String apiTermsOfService;
    @Value("${api.license}")         String apiLicense;
    @Value("${api.licenseUrl}")      String apiLicenseUrl;
    @Value("${api.externalDocDesc}") String apiExternalDocDesc;
    @Value("${api.externalDocUrl}")  String apiExternalDocUrl;
    @Value("${api.contact.name}")    String apiContactName;
    @Value("${api.contact.url}")     String apiContactUrl;
    @Value("${api.contact.email}")   String apiContactEmail;

    @Bean
    public OpenAPI getOpenApiDocumentation() {
        return new OpenAPI()
            .info(new Info().title(apiTitle)
                .description(apiDescription)
                .version(apiVersion)
                .contact(new Contact()
                    .name(apiContactName)
                    .url(apiContactUrl)
                    .email(apiContactEmail))
                .termsOfService(apiTermsOfService)
                .license(new License()
                    .name(apiLicense)
                    .url(apiLicenseUrl)))
            .externalDocs(new ExternalDocumentation()
                .description(apiExternalDocDesc)
                .url(apiExternalDocUrl));
    }
}

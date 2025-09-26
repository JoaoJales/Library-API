package br.com.Library_api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/populate")
@Tag(name = "0 - Populate", description = "Popular Banco de Dados (Opcional)")
public class PopulateTestController {
    private final DataSource dataSource;

    public PopulateTestController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostMapping
    public String populate() {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("inserts-library-api.sql"));
            return "Database successfully populated!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error to the popular database: " + e.getMessage();
        }
    }
}

package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MyController {
    DataSource dataSource;

    MyController(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/")
    String index(Model model) {
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery("show tables");
            List<String> tableNames = new ArrayList<>();
            while(resultSet.next()){
                tableNames.add(resultSet.getString(1));
                System.out.println(resultSet.getString(1));
            }
            model.addAttribute("tableNames", tableNames);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "index";
    }
}

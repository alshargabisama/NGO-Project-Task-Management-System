package com.mycompany.tasktrakingsystem.dao;



import com.mycompany.tasktrakingsystem.model.Project;

import com.mycompany.tasktrakingsystem.util.DBConnection;

import java.sql.*;

import java.util.ArrayList;

import java.util.List;



public class ProjectDAO {



    public List<Project> getProjectsByUserId(int userId) {

        List<Project> projects = new ArrayList<>();

        



        String sql = "SELECT p.PROJ_ID, p.PROJ_NAME, p.PROJ_DESCRIPTION, p.START_DATE, p.END_DATE, p.STATUS " +

                     "FROM Project p " +

                     "JOIN WORKS_ON w ON p.PROJ_ID = w.PROJ_ID " +

                     "WHERE w.USER_ID = ?";

        

        try (Connection conn = DBConnection.getConnection();

             PreparedStatement pst = conn.prepareStatement(sql)) {

            

            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();

            

            while (rs.next()) {


                Project p = new Project(

                    rs.getInt(1),

                    rs.getString(2),

                    rs.getString(3),

                    rs.getDate(4) != null ? rs.getDate(4).toLocalDate() : null,

                    rs.getDate(5) != null ? rs.getDate(5).toLocalDate() : null,

                    rs.getString(6)

                );

                projects.add(p);

            }

        } catch (SQLException e) {

            System.err.println("Error in ProjectDAO: " + e.getMessage());

            e.printStackTrace();

        }

        return projects;

    }}
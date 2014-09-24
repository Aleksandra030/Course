/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.fon.is.lrmi.services;

import java.util.ArrayList;
import java.util.Collection;
import rs.fon.is.lrmi.domen.CreativeWork;
import rs.fon.is.lrmi.domen.Search;
import rs.fon.is.lrmi.persistence.QueryExecutor;
import rs.fon.is.lrmi.persistence.RDFModel;
import rs.fon.is.lrmi.util.Constants;

/**
 *
 * @author ANA
 */
public class QueryService {

    private QueryExecutor queryExecutor = new QueryExecutor();

    public Collection<CreativeWork> getCoursesCousera(String name, String inLanguage,
            String organizationName, String typicalAgeRange, String duration) {

        Collection<CreativeWork> listakurseva = new ArrayList<CreativeWork>();

        String where = " ?courses a schema:CreativeWork. ";
        String filter = "";

        if (!name.isEmpty()) {
            where += "?courses schema:name ?name. ";
            filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
        }
        if (inLanguage != null) {
            where += "?courses schema:inLanguage ?inLanguage. ";
            filter += "FILTER regex( ?inLanguage, \"" + inLanguage + "\", \"i\" ) ";
        }

        if (!organizationName.isEmpty()) {
            String[] publishersArray = organizationName.split(",");
            for (int i = 0; i < publishersArray.length; i++) {

                where += "?courses schema:publishers ?publishers" + i + ". ?publishers" + i
                        + " schema:name ?name" + i + " FILTER regex(?name" + i
                        + ", \"" + publishersArray[i] + "\") ";

            }
        }

        if (!duration.isEmpty()) {
            where += "?courses schema:duration ?duration. "
                    + "?duration schema:description ?description.";
            filter += "FILTER regex( ?description, \"" + duration + "\", \"i\" ) ";
        }
        if (!typicalAgeRange.isEmpty()) {
            where += "?courses schema:typicalAgeRange ?typicalAgeRange. ";
            filter += "FILTER regex( ?typicalAgeRange, \"" + typicalAgeRange + "\", \"i\" ) ";
        }
       
        

        String query = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT ?courses " + "WHERE { " + where + filter + " } ";
        Collection<String> lista = queryExecutor
                .executeOneVariableSelectSparqlQuery(query, "courses",
                        RDFModel.getInstance().getModel());

        for (String string : lista) {
            CreativeWork c = getCousera(string);
            listakurseva.add(c);
        }

        return listakurseva;
    }

    public Collection<CreativeWork> getCourses(Search ss) {

        Collection<CreativeWork> products = new ArrayList<CreativeWork>();

        String name = ss.getName();
        String inLanguage = ss.getInLanguage();
        String organizationName = ss.getPublishers();
        String typicalAgeRange = ss.getTypicalAgeRange();
        String duration = ss.getDuration();

        String where = " ?courses a schema:CreativeWork. ";
        
        String filter = "";
        System.out.print(ss.getName());
        if (!name.isEmpty()) {
            where += "?courses schema:name ?name. ";
            filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
        }
        if (!inLanguage.isEmpty()) {
            where += "?courses schema:inLanguage ?inLanguage. ";
            filter += "FILTER regex( ?inLanguage, \"" + inLanguage + "\", \"i\" ) ";
        }

        if (!organizationName.isEmpty()) {
            String[] publishersArray = organizationName.split(",");
            for (int i = 0; i < publishersArray.length; i++) {

                where += "?courses schema:publishers ?publishers" + i + ". ?publishers" + i
                        + " schema:name ?name" + i + " FILTER regex(?name" + i
                        + ", \"" + publishersArray[i] + "\") ";

            }
        }
        if (!duration.isEmpty()) {

                where += "?courses schema:duration ?duration. "
                        + "?duration schema:description ?description. ";
                filter += "FILTER regex( ?description, \"" + duration + "\", \"i\" ) ";


       }

        if (!typicalAgeRange.isEmpty()) {
            where += "?courses schema:typicalAgeRange ?typicalAgeRange. ";
            filter += "FILTER regex( ?typicalAgeRange, \"" + typicalAgeRange + "\", \"i\" ) ";
        }
         if (ss.getImeKursa().equals("Udacity")||ss.getImeKursa().equals("Coursera")) {
                               where += "?courses schema:provider ?provider. "
                        + "?provider schema:name ?name. ";
                filter += "FILTER regex( ?name, \"" + ss.getImeKursa() + "\", \"i\" ) ";
         }

        String query = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT ?courses " + "WHERE { " + where + filter + " } ";

        Collection<String> coursestUris = queryExecutor
                .executeOneVariableSelectSparqlQuery(query, "courses",
                        RDFModel.getInstance().getModel());

        for (String string : coursestUris) {
            CreativeWork c = getCousera(string);
            products.add(c);
        }

        return products;
    }

    public Collection<CreativeWork> getCoursesUdacity(String name, String duration,
            String organizationName, String typicalAgeRange) {

        Collection<CreativeWork> products = new ArrayList<CreativeWork>();

        String where = " ?courses a schema:CreativeWork. ";
        String filter = "";

        if (!name.isEmpty()) {
            where += "?courses schema:name ?name. ";
            filter += "FILTER regex( ?name, \"" + name + "\", \"i\" ) ";
        }
        if (!duration.isEmpty()) {
            where += "?courses schema:duration ?duration. "
                    + "?duration schema:description ?description.";
            filter += "FILTER regex( ?description, \"" + duration + "\", \"i\" ) ";
        }

        if (!organizationName.isEmpty()) {
            String[] publishersArray = organizationName.split(",");
            for (int i = 0; i < publishersArray.length; i++) {

                where += "?courses schema:publishers ?publishers" + i + ". ?publishers" + i
                        + " schema:name ?name" + i + " FILTER regex(?name" + i
                        + ", \"" + publishersArray[i] + "\") ";

            }
        }
        if (!typicalAgeRange.isEmpty()) {
            where += "?courses schema:typicalAgeRange ?typicalAgeRange. ";
            filter += "FILTER regex( ?typicalAgeRange, \"" + typicalAgeRange + "\", \"i\" ) ";
        }

        String query = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT ?courses " + "WHERE { " + where + filter + " } ";
        Collection<String> coursesUris = queryExecutor
                .executeOneVariableSelectSparqlQuery(query, "courses",
                        RDFModel.getInstance().getModel());

        for (String string : coursesUris) {
            CreativeWork c = getCousera(string);
            products.add(c);
        }

        return products;
    }

    public CreativeWork getCousera(String uri) {
        CreativeWork courses = queryExecutor.getCousera(uri);
        return courses;
    }

    public Collection<String> getLanguages() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:inLanguage ?l }"
                + "ORDER BY ?l";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getTypicalAgeRange() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:typicalAgeRange ?l }"
                + "ORDER BY ?l";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getPublishers() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:publishers ?publishers. "
                + "?publishers schema:name ?l }"
                + "ORDER BY ?l";

        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

    public Collection<String> getDuration() {
        String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:duration ?duration. "
                + "?duration schema:description ?l }"
                + "ORDER BY ?l";


        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

    Collection<String> getLinks() {
 String queryString
                = "PREFIX courses: <" + Constants.NS + "> "
                + "PREFIX schema: <" + Constants.SCHEMA + "> "
                + "PREFIX xsd: <" + Constants.XSD + "> "
                + "SELECT DISTINCT ?l \n"
                + "WHERE { ?x schema:provider ?provider. "
                + "?provider schema:name ?l }"
                + "ORDER BY ?l";


        return queryExecutor.executeOneVariableSelectSparqlQuery(queryString, "l",
                RDFModel.getInstance().getModel());
    }

}

package com.mlefevre.samples.hibernate3.repository;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BaseDataSet {

    private static final String DATASET_FOLDER = "/dataset/";
    private static final String PROPERTIES_FILE= "/datasource-test.properties";

    private IDatabaseConnection connection;
    private List<IDataSet> dataSets = new ArrayList<IDataSet>();


    private InputStream getResourceFile(String name) {
        Class<?> clazz = BaseDataSet.class;
        InputStream stream = clazz.getResourceAsStream(name);

        return stream;
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        try {
            InputStream stream = this.getResourceFile(PROPERTIES_FILE);
            properties.load(stream);

        } catch(FileNotFoundException e) { } catch (IOException e) { }

        return properties;
    }

    private void setUpDbConnection() {
        Properties properties = this.getProperties();

        try {
            Class driverClass = Class.forName(properties.getProperty("db.driver"));
            Connection connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );
            IDatabaseConnection dbConnection = new DatabaseConnection(connection);
            DatabaseConfig config = dbConnection.getConfig();
            config.setProperty("http://www.dbunit.org/features/caseSensitiveTableNames", true);
            this.connection = dbConnection;

        } catch (SQLException e) { } catch (DatabaseUnitException e) { } catch (ClassNotFoundException e) { }
    }



    protected void loadDataSets() throws DatabaseUnitException, SQLException {
        this.setUpDbConnection();
        if(dataSets == null || this.connection == null) {
            return;
        }

        CompositeDataSet composite = new CompositeDataSet(this.dataSets.toArray(new IDataSet[this.dataSets.size()]));
        DatabaseOperation.CLEAN_INSERT.execute(this.connection, composite);
    }

    protected void addDataSet(String dataSetFileName) throws DataSetException {
        InputStream stream = this.getResourceFile(DATASET_FOLDER + dataSetFileName);

        InputSource inputSource = new InputSource(stream);
        FlatXmlProducer xmlProducer = new FlatXmlProducer(inputSource);
        IDataSet dataSet = new FlatXmlDataSet(xmlProducer);

        if(dataSet != null) {
            this.dataSets.add(dataSet);
        }
    }


    protected void cleanDataBase() throws DatabaseUnitException, SQLException {
        CompositeDataSet composite = new CompositeDataSet(this.dataSets.toArray(new IDataSet[this.dataSets.size()]));
        DatabaseOperation.DELETE_ALL.execute(this.connection, composite);
    }

}

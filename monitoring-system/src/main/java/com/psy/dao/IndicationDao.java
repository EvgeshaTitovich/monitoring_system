package com.psy.dao;

import com.psy.DaoException;
import com.psy.entity.Indication;
import com.psy.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class IndicationDao {

    private static final IndicationDao INSTANCE = new IndicationDao();
    private static final String SAVE_SQL = "INSERT INTO indication (p,q,u,i,phi,v,date) VALUES(?,?,?,?,?,?,?)";
    private static final String FIND_ALL_SQL = "SELECT id,p,q,u,i,phi,v,date FROM indication";

    private IndicationDao() {
    }

    public List<Indication> list() {
        try (var connection = ConnectionManager.get()) {
            try (var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
                var resultSet = preparedStatement.executeQuery();
                List<Indication> indications = new ArrayList<>();
                Indication indication;
                while (resultSet.next()) {
                    indication = new Indication(
                            resultSet.getInt("id"),
                            resultSet.getDouble("p"),
                            resultSet.getDouble("q"),
                            resultSet.getDouble("u"),
                            resultSet.getDouble("i"),
                            resultSet.getDouble("phi"),
                            resultSet.getDouble("v"),
                            resultSet.getTimestamp("date")
                    );
                    indications.add(indication);
                }
                return indications;
            }
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Indication save(Indication indication) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, indication.getP());
            preparedStatement.setDouble(2, indication.getQ());
            preparedStatement.setDouble(3, indication.getU());
            preparedStatement.setDouble(4, indication.getI());
            preparedStatement.setDouble(5, indication.getPhi());
            preparedStatement.setDouble(6, indication.getV());
            preparedStatement.setTimestamp(7, indication.getTimestamp());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                indication.setId(generatedKeys.getInt("id"));
            }
            return indication;

        } catch (Exception throwables) {
            throw new DaoException(throwables);
        }
    }

    public static IndicationDao getInstance() {
        return INSTANCE;
    }

}

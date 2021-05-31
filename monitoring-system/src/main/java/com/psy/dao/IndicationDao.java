package com.psy.dao;

import com.psy.DaoException;
import com.psy.entity.Indication;
import com.psy.util.ConnectionManager;

import java.sql.*;

public class IndicationDao {

    private static final IndicationDao INSTANCE = new IndicationDao();
    private static final String SAVE_SQL = "INSERT INTO indication (p,q,u,i,phi,v) VALUES(?,?,?,?,?,?)";

    private IndicationDao() {
    }

    public Indication save(Indication indication) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1,indication.getP());
            preparedStatement.setDouble(2,indication.getQ());
            preparedStatement.setDouble(3,indication.getU());
            preparedStatement.setDouble(4,indication.getI());
            preparedStatement.setDouble(5,indication.getPhi());
            preparedStatement.setDouble(6,indication.getV());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
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

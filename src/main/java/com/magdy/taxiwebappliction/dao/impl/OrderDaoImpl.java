package com.magdy.taxiwebappliction.dao.impl;


import com.magdy.taxiwebappliction.dao.DaoException;
import com.magdy.taxiwebappliction.dao.OrderDao;
import com.magdy.taxiwebappliction.entity.Client;
import com.magdy.taxiwebappliction.entity.Driver;
import com.magdy.taxiwebappliction.entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {

    private static final Logger log = (Logger) LogManager.getLogger();
    private static final String INSERT_ORDER = "INSERT  INTO taxi_order (data,client_id,driver_id) VALUES (?,?,?)";
    private static final String SELECT_ORDER = "select data,client_id,driver_id from taxi_order where id=?";
    private static final String SELECT_ALL_ORDER = "select * from taxi_order";
    private static final String UPDATE_ORDER = "update taxi_order set data=?,client_id=?,driver_id=? where id=?";
    private static final String DELETE_ORDER = "delete from taxi_order where id=?";


    @Override
    public Order save(Order order) throws DaoException {
        Connection connection = pool.getConnection();
        log.info("SAVED_ORDER_SQL");
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, order.getData());
            preparedStatement.setLong(2, order.getClient().getId());
            if (order.getDriver() != null) {
                preparedStatement.setLong(3, order.getDriver().getId());
            } else {
                preparedStatement.setNull(3, java.sql.Types.NULL);
            }

            int rewSaved = preparedStatement.executeUpdate();
            if (rewSaved > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getLong(1));
                        return order;
                    }

                }
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            pool.returnConnection(connection);
        }
        return order;
    }

    @Override
    public List<Order> saveAll(List<Order> list) throws DaoException {
        log.info("SAVED_ALL_ORDER");

        for (Order order : list) {
            save(order);
        }
        return list;
    }

    @Override
    public Order selectById(long id) throws DaoException {
        Connection connection = pool.getConnection();
        log.info("GET_ORDER_BY_ID");
        Order order = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String data = resultSet.getString("data");
                long driverId = resultSet.getLong("driver_id");
                long clintId = resultSet.getLong("client_id");

                Driver driver = driverId != 0 ? new Driver(driverId) : null;
                order = new Order(id, data, new Client(clintId), driver);
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            pool.returnConnection(connection);
        }
        return order;
    }

    @Override
    public List<Order> selectAll() throws DaoException {
        Connection connection = pool.getConnection();
        log.info("GET_ALL_ORDER_LIST");
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER)) {

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String data = resultSet.getString("data");
                long driverId = resultSet.getLong("driver_id");
                long clintId = resultSet.getLong("client_id");

                orderList.add(new Order(id, data, new Client(clintId), new Driver(driverId)));
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            pool.returnConnection(connection);
        }
        return orderList;
    }

    @Override
    public Order update(Order order) throws DaoException {
        Connection connection = pool.getConnection();
        log.info("UPDATE_ORDER");
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, order.getData());
            preparedStatement.setLong(2, order.getClient().getId());
            preparedStatement.setLong(3, order.getDriver().getId());
            preparedStatement.setLong(4, order.getId());
            preparedStatement.executeUpdate();

            return order;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        Connection connection = pool.getConnection();
        log.info("DELETE_ORDER_BY_ID");
        boolean rewDelete = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER)) {

            preparedStatement.setLong(1, id);
            rewDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            pool.returnConnection(connection);
        }
        return rewDelete;
    }

    public void accept(long driverId, Long orderId) throws DaoException {
        Connection connection = pool.getConnection();
        log.info("UPDATE_ORDER");
        try (PreparedStatement preparedStatement = connection.prepareStatement("update taxi_order set driver_id=? where id=?")) {
            preparedStatement.setLong(1, driverId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            pool.returnConnection(connection);
        }
    }
}

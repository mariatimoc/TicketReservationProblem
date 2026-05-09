package ro.maria.timoc.dao;

import ro.maria.timoc.connection.ConnectionFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class AbstractDao<T> {

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String tableName() {

        return type.getSimpleName().toLowerCase();
    }

    public void insert(T object) {

        Field[] fields = type.getDeclaredFields();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Field field : fields) {
            if (field.getName().equals("id")) {
                continue;
            }

            columns.append(field.getName()).append(", ");
            values.append("?, ");
        }
        columns.setLength(columns.length() - 2);
        values.setLength(values.length() - 2);

        String query ="INSERT INTO " + tableName() + " (" + columns + ") VALUES (" + values + ")";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)

        ) {

            int index = 1;
            for (Field field : fields) {
                if (field.getName().equals("id")) {
                    continue;
                }
                PropertyDescriptor property = new PropertyDescriptor(field.getName(), type);
                Object value = property.getReadMethod().invoke(object);
                statement.setObject(index++, value);
            }

            statement.executeUpdate();
            System.out.println(type.getSimpleName() + " inserted successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public T findById(int id) {

        String query = "SELECT * FROM " + tableName() + " WHERE id = ?";
        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)

        ) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();

                for (Field field :type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor property = new PropertyDescriptor(field.getName(), type);
                    property.getWriteMethod().invoke(instance, value);
                }
                return instance;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    public void update(T object) {

        Field[] fields =type.getDeclaredFields();

        StringBuilder query = new StringBuilder("UPDATE " + tableName() + " SET ");

        boolean first = true;

        for (Field field : fields) {

            if (field.getName().equals("id")) {
                continue;
            }

            if (!first) {
                query.append(", ");
            }

            query.append(field.getName()).append(" = ?");
            first = false;
        }

        query.append(" WHERE id = ?");

        try (
                Connection connection =ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query.toString())
        ) {

            int index = 1;
            for (Field field : fields) {
                if (field.getName().equals("id")) {
                    continue;
                }

                PropertyDescriptor property = new PropertyDescriptor(field.getName(), type);

                Object value = property.getReadMethod().invoke(object);

                statement.setObject(index++, value);
            }

            PropertyDescriptor idProperty = new PropertyDescriptor("id", type);

            Object idValue = idProperty.getReadMethod().invoke(object);

            statement.setObject(index, idValue);
            statement.executeUpdate();
            System.out.println(type.getSimpleName() + " updated successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteById(int id) {

        String query = "DELETE FROM " + tableName() + " WHERE id = ?";

        try (
                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println(type.getSimpleName() + " deleted successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
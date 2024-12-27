package mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
    public List<GuestbookVo> findAll() {
        List<GuestbookVo> result = new ArrayList<>();
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT
                        id, name, DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s') AS reg_date, contents
                    FROM guestbook
                    ORDER BY reg_date DESC;
                    """
            );
            ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                GuestbookVo vo = new GuestbookVo();
                vo.setId(resultSet.getLong("id"));
                vo.setName(resultSet.getString("name"));
                vo.setRegDate(resultSet.getString("reg_date"));
                vo.setContents(resultSet.getString("contents"));

                result.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }

        return result;
    }

    public void insert(GuestbookVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    INSERT INTO guestbook (name, password, contents, reg_date) VALUES (?, ?, ?, current_time());
                    """
            );
        ) {
            preparedStatement.setString(1, vo.getName());
            preparedStatement.setString(2, vo.getPassword());
            preparedStatement.setString(3, vo.getContents());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }
    }

    public void deleteByIdAndPassword(Long id, String password) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    DELETE FROM guestbook WHERE id=? AND password=?;
                    """
            );
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }
    }
}

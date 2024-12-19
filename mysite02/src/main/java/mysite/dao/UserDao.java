package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mysite.vo.UserVo;
public class UserDao {
    public void insert(UserVo vo) {
        try (
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    INSERT INTO user (name, password, email, gender, join_date) VALUES (?, ?, ?, ?, current_time());
                    """
            );
        ) {
            preparedStatement.setString(1, vo.getName());
            preparedStatement.setString(2, vo.getPassword());
            preparedStatement.setString(3, vo.getEmail());
            preparedStatement.setString(4, vo.getGender());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }
    }

    public UserVo findByEmailAndPassword(String email, String password) {
        UserVo userVo = null;

        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
            """
                    SELECT id, name, gender, join_date
                    FROM user
                    WHERE email = ?
                        AND password=?
                """
            );
        ) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userVo = new UserVo();
                userVo.setId(rs.getLong("id"));
                userVo.setName(rs.getString("name"));
                userVo.setEmail(email);
                userVo.setGender(rs.getString("gender"));
                userVo.setJoinDate(rs.getString("join_date"));
            }

            rs.close();
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }

        return userVo;
    }

    private Connection getConnection() throws SQLException {
        Connection conn = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            String url = "jdbc:mariadb://192.168.64.30:3306/webdb";
            conn = DriverManager.getConnection(url, "webdb", "webdb");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        }

        return conn;
    }

    public void update(UserVo vo) {
        try (
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    UPDATE user
                        SET
                        name = COALESCE(?, name),
                        password = COALESCE(?, password),
                        gender = COALESCE(?, gender)
                    WHERE id = ?;
                    """
            );
        ) {
            preparedStatement.setString(1, vo.getName());
            preparedStatement.setString(2, vo.getPassword());
            preparedStatement.setString(3, vo.getGender());
            preparedStatement.setLong(4, vo.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }
    }
}

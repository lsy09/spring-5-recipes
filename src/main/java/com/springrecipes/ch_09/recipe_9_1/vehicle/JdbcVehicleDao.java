package com.springrecipes.ch_09.recipe_9_1.vehicle;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JdbcVehicleDao implements VehicleDao{

    private static final String INSERT_SQL = "INSERT INTO VEHICLE (COLOR, WHEEL, SEAT, VEHICLE_NO) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE VEHICLE SET COLOR=?,WHEEL=?,SEAT=? WHERE VEHICLE_NO=?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM VEHICLE";
    private static final String SELECT_ONE_SQL = "SELECT * FROM VEHICLE WHERE VEHICLE_NO = ?";
    private static final String DELETE_SQL = "DELETE FROM VEHICLE WHERE VEHICLE_NO=?";

    /**
     * 외부 클래스의 지역 변수는 반드시 final을 사용
     */
    private final DataSource dataSource;

    public JdbcVehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Vehicle vehicle) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);

        /**
         * 9_1_i 문 생성기로 DB 수정하기
         */
//        jdbcTemplate.update(new InsertVehicleStatementCreator(vehicle));

        /**
         * 9_1_ii
         * 람다 표현식(lambda expression)
         * 람다 표현식을 사용하면 내부 클래스 InsertVehicleStatementCreator는 더 이상 필요 없으니 삭제
         */
//        jdbcTemplate.update(con -> {
//           PreparedStatement ps = con.prepareStatement(INSERT_SQL);
//           prepareStatement(ps, vehicle);
//           return ps;
//        });

        /**
         * 9_1_iii 문 세터로 DB 수정하기
         * PreparedStatementSetter 매개변수를 바인딩 하는 일에만 관여
         * JdbcTemplate의 update() 템플릿 메서드 중에는 SQL문과 PreparedStatementSetter 객체를 인수로 받는것들이 있다.
         * 주어진 SQL문을 수행하는 PreparedStatement 객체를 생성 이 객체에 매개변수를 바인딩하는 일이 전부이다.
         * (안에서 다시 prepareStatement() 메서드에 다시 위임)
         */
//         jdbcTemplate.update(INSERT_SQL, new PreparedStatementSetter() {
//             @Override
//             public void setValues(PreparedStatement ps) throws SQLException {
//                prepareStatement(ps, vehicle);
//             }
//         });

        /**
         * 9_1_iii
         * 람다 표현식(lambda expression)
         */
//        jdbcTemplate.update(INSERT_SQL, ps -> prepareStatement(ps, vehicle));

        /**
         * 9_1_iv SQL문과 매개변수값으로 DB 수정하기
         * PreparedStatement 객체를 만들고 매개변수를 바인딩 하므로 수정 작업에 관해서는 아무것도 오버라이드할 게 없다.
         * 콜백 인터페이스를 구현할 필요가 없다.
         */
//        jdbcTemplate.update(INSERT_SQL, vehicle.getColor(), vehicle.getWheel(), vehicle.getSeat(), vehicle.getVehicleNo());

    }

    /**
     * 9_1_v DB 배치 수정하기
     * 여러번 반복적으로 컴파일 될 경우 실행 속도가 느려지므로, 큰 덩어리로 묶어 배치 처리 하는것이 좋다
     * jdbcTemplate의 batchUpdate() 템플릿 메서드 를 호출 하여 수정 작업을 배치 처리할 수 있다.
     * SQL문, 아이템 컬렉션, 배치크기, ParameterizedPreparedStatementSett를 설정 하면 된다.
     */
    @Override
    public void insert(Collection<Vehicle> vehicles) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);

        jdbcTemplate.batchUpdate(INSERT_SQL, vehicles, vehicles.size(),
                new ParameterizedPreparedStatementSetter<Vehicle>() {

            @Override
            public void setValues(PreparedStatement ps, Vehicle argument) throws SQLException {
                prepareStatement(ps, argument);
            }
        });
    }

    @Override
    public void update(Vehicle vehicle) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)){
            prepareStatement(ps, vehicle);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Vehicle vehicle) {
        try(Connection conn =  dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(DELETE_SQL)){
            ps.setString(1, vehicle.getVehicleNo());
            ps.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vehicle findByVehicleNo(String vehicleNo) {
        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ONE_SQL)){
            ps.setString(1, vehicleNo);

            Vehicle vehicle = null;
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    vehicle = toVehicle(rs);
                }
            }
            return vehicle;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> findAll() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            List<Vehicle> vehicles = new ArrayList<>();
            while (rs.next()) {
                vehicles.add(toVehicle(rs));
            }
            return vehicles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Vehicle toVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(rs.getString("VEHICLE_NO"),
                rs.getString("COLOR"), rs.getInt("WHEEL"),
                rs.getInt("SEAT"));
    }

    private void prepareStatement(PreparedStatement ps, Vehicle vehicle) throws SQLException {
        ps.setString(1, vehicle.getColor());
        ps.setInt(2, vehicle.getWheel());
        ps.setInt(3, vehicle.getSeat());
        ps.setString(4, vehicle.getVehicleNo());
    }

    /**
     * 9_1_i 문 생성기로 DB 수정하기
     * InsertVehicleStatementCreator는 JdbcVehicleDao의 내부 클래스(inner class) 이므로 JdbcVehicleDao 클래스에서 문 생성 기능을 호출
     */
    private class InsertVehicleStatementCreator implements PreparedStatementCreator{
        private final Vehicle vehicle;

        public InsertVehicleStatementCreator(Vehicle vehicle) {
            this.vehicle = vehicle;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(INSERT_SQL);
            prepareStatement(ps, vehicle);
            return ps;
        }
    }


}

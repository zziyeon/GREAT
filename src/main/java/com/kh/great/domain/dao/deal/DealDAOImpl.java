package com.kh.great.domain.dao.deal;

import com.kh.great.domain.dao.member.Member;
import com.kh.great.domain.dao.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DealDAOImpl implements DealDAO{

    private final JdbcTemplate jt;


    //구매 등록
    @Override
    public Deal add(Deal deal) {
        StringBuffer sql = new StringBuffer();

        sql.append("insert into deal(order_number,buyer_number,seller_number,p_number, ");
        sql.append(" p_count,price,visittime, buy_type) ");
        sql.append(" values(deal_order_number_seq.nextval,1,?,?,?,?,TO_DATE(?,'YYYY-MM-DD\"T\"HH24:MI:SS'),?) ");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jt.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"order_number"});
//                pstmt.setLong(1,deal.getBuyerNumber());
                pstmt.setLong(1,deal.getSellerNumber());
                pstmt.setLong(2,deal.getPNumber());
                pstmt.setLong(3,deal.getPCount());
                pstmt.setLong(4,deal.getPrice());
                pstmt.setString(5,deal.getVisittime());
                pstmt.setLong(6,deal.getBuyType());
                return pstmt;
            }
        },keyHolder);

        Long order_number = Long.valueOf(keyHolder.getKeys().get("order_number").toString());

        deal.setOrderNumber(order_number);
        return deal;
    }

    //회원 번호로 조회
   @Override
    public List<Deal> findByMemberNumber(Long memNumber) {
       StringBuffer sql = new StringBuffer();



       sql.append("select *");
       sql.append("  from ( select * ");
       sql.append("       from member m, product_info p ");
       sql.append("      where m.mem_number = p.owner_number) t1, deal d ");
       sql.append(" where d.p_number = t1.p_number ");
       sql.append("   and d.buyer_number = ? ");


       List<Deal> deals = null;
       try {
           deals = jt.query(sql.toString(), new RowMapper<Deal>() {
               @Override
               public Deal mapRow(ResultSet rs, int rowNum) throws SQLException {
                   Deal deal = (new BeanPropertyRowMapper<>(Deal.class)).mapRow(rs, rowNum);
                   Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs, rowNum);
                   Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs, rowNum);

                   deal.setMember(member);
                   deal.setProduct(product);
                   return deal;
               }
           }, memNumber);

       } catch (DataAccessException e) {
           log.info("회원번호가 없습니다");
       }

       return deals;
   }

   //주문번호로 조회
    @Override
    public Optional<Deal> findByOrderNumber(Long orderNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append(" select d.order_number, m.mem_store_location,d.p_count, p.p_name, d.price, d.visittime,d.orderdate,m.mem_nickname, p.p_number, d.buyer_number ");
        sql.append(" from member m, deal d, product_info p ");
        sql.append(" where m.mem_number = p.owner_number ");
        sql.append("  and p.p_number = d.p_number ");
        sql.append("  and d.order_number = ? ");


        try {
            Deal deal = jt.queryForObject(sql.toString(), new RowMapper<Deal>() {
                @Override
                public Deal mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Deal deal = (new BeanPropertyRowMapper<>(Deal.class)).mapRow(rs,rowNum);
                    Member member = (new BeanPropertyRowMapper<>(Member.class)).mapRow(rs,rowNum);
                    Product product = (new BeanPropertyRowMapper<>(Product.class)).mapRow(rs,rowNum);
                    deal.setMember(member);
                    deal.setProduct(product);
                    return deal;
                }
            },orderNumber);
                    return Optional.of(deal);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    //판매 테이블 수정
    @Override
    public int update(Long pNumber,Deal deal) {
        StringBuffer sql = new StringBuffer();
        sql.append("update product_info ");
        sql.append("  set remain_count = remain_count - ? ");
        sql.append(" where p_number = ? ");

        //Deal deal = jt.queryForObject(sql.toString(),new BeanPropertyRowMapper<>(Deal.class));
        //log.info("deal={}",deal);
        int affectedRow = jt.update(sql.toString(),deal.getPCount(),pNumber);

        return affectedRow;
    }

    //취소시 수량 변경
    @Override
    public int delUpdate(Long pNumber, Deal deal) {
        StringBuffer sql = new StringBuffer();
        sql.append("update product_info ");
        sql.append("  set remain_count = remain_count + ? ");
        sql.append(" where p_number = ? ");

        int affectedRow = jt.update(sql.toString(),deal.getPCount(),pNumber);
        return affectedRow;
    }

    //주문 취소
    @Override
    public int deleteByOrderNumber(Long orderNumber) {
        String sql =" delete from deal where order_number = ? ";

        int affectedRow = jt.update(sql.toString(), orderNumber);

        return affectedRow;
    }

    //남은수량 0개 일시

    @Override
    public int updatePstatus(Long pNumber) {
        StringBuffer sql = new StringBuffer();

        sql.append(" update product_info ");
        sql.append(" set p_status = 1 ");
        sql.append(" where p_number = ? ");

        int affectedRow = jt.update(sql.toString(), pNumber);
        return affectedRow;
    }
}
